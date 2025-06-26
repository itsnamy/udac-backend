package com.namy.udac.backend.service.tutorialServices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SQLExecutorService {

    private final JdbcTemplate h2JdbcTemplate;
    private final DataSource dataSource;

    public SQLExecutorService(@Qualifier("h2JdbcTemplate") JdbcTemplate h2JdbcTemplate) {
        this.h2JdbcTemplate = h2JdbcTemplate;
        this.dataSource = h2JdbcTemplate.getDataSource();
    }

    public void resetAndLoadDataset(String createTableSQL, List<String> insertStatements) {
        h2JdbcTemplate.execute("DROP ALL OBJECTS");
        h2JdbcTemplate.execute(createTableSQL);
        for (String insert : insertStatements) {
            h2JdbcTemplate.execute(insert);
        }
    }

    public void resetDataset() {
        h2JdbcTemplate.execute("DROP ALL OBJECTS");
    }

    public Object executeSQL(String sql) {
        try (Connection conn = DataSourceUtils.getConnection(dataSource);
             Statement stmt = conn.createStatement()) {

            String trimmed = sql.trim();
            String type = trimmed.split("\\s+")[0].toUpperCase();
            String tableName = extractTableName(trimmed);
    
            stmt.execute(sql);

            switch (type) {
                case "SELECT":
                    return Map.of(
                        "status", "success",
                        "type", "SELECT",
                        "tableName", tableName,
                        "table", resultSetToList(stmt.getResultSet()),
                        "availableTables", getAvailableTablesWithData(conn)
                    );

                case "CREATE":
                    if (tableName != null) {
                        return Map.of(
                            "status", "success",
                            "type", "CREATE",
                            "tableName", tableName,
                            "structure", getTableStructure(conn, tableName),
                            "message", "Jadual berjaya dicipta.",
                            "availableTables", getAvailableTablesWithData(conn)
                        );
                    } else {
                        return Map.of("status", "success","type", "CREATE", "message", "Jadual berjaya dicipta, tetapi nama jadual tidak dapat dikenalpasti.");
                    }

                case "INSERT":
                case "UPDATE":
                case "DELETE":
                case "ALTER":
                    int affected = stmt.getUpdateCount();
                    if (tableName != null && tableExists(conn, tableName)) {
                        return Map.of(
                            "status", "success",
                            "type", type,
                            "rowsAffected", affected,
                            "tableName", tableName,
                            "table", getTableData(conn, tableName),
                            "availableTables", getAvailableTablesWithData(conn)
                        );
                    } else {
                        return Map.of(
                            "type", type,
                            "rowsAffected", affected,
                            "message", "SQL berjaya dilaksanakan. Jadual tidak dapat dipaparkan."
                        );
                    }

                case "DROP":
                    return Map.of(
                        "status", "success",
                        "type", "DROP",
                        "message", "Jadual berjaya digugurkan.",
                        "availableTables", getAvailableTablesWithData(conn)
                    );

                default:
                    int genericResult = stmt.getUpdateCount();
                    return Map.of(
                        "status", "success",
                        "type", "OTHER",
                        "rowsAffected", genericResult,
                        "message", "Arahan SQL berjaya dilaksanakan.",
                        "availableTables", getAvailableTablesWithData(conn)
                    );
            }

        } catch (SQLException e) {
            return Map.of(
                "status", "error",
                "type", "ERROR",
                "message", e.getMessage()
            );
        }
    }

    private static final Set<String> EXCLUDED_TABLES = Set.of(
    "CONSTANTS", "ENUM_VALUES", "INDEXES", "INDEX_COLUMNS",
    "INFORMATION_SCHEMA_CATALOG_NAME", "IN_DOUBT", "LOCKS",
    "QUERY_STATISTICS", "RIGHTS", "ROLES", "SESSIONS",
    "SESSION_STATE", "SETTINGS", "SYNONYMS"
    );

    private List<Map<String, Object>> getAvailableTablesWithData(Connection conn) {
        List<Map<String, Object>> tablesWithData = new ArrayList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String schemaName = rs.getString("TABLE_SCHEM");
    
                if (!"INFORMATION_SCHEMA".equalsIgnoreCase(schemaName)
                        && !EXCLUDED_TABLES.contains(tableName.toUpperCase())) {
                    List<Map<String, Object>> tableData = getTableData(conn, tableName);
                    Map<String, Object> tableInfo = new HashMap<>();
                    tableInfo.put("tableName", tableName);
                    tableInfo.put("data", tableData);
                    tablesWithData.add(tableInfo);
                }
            }
        } catch (SQLException e) {
            // Optional: log or handle error
        }
        return tablesWithData;
    }
    
    
    private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            result.add(row);
        }
        return result;
    }

    private String extractTableName(String sql) {
        Pattern pattern = Pattern.compile("(?i)(INTO|TABLE|UPDATE|FROM|ALTER TABLE|DROP TABLE)\\s+([a-zA-Z_][a-zA-Z0-9_]*)");
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) return matcher.group(2);
        return null;
    }

    private List<Map<String, Object>> getTableData(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            return resultSetToList(rs);
        }
    }

    private List<Map<String, Object>> getTableStructure(Connection conn, String tableName) throws SQLException {
        List<Map<String, Object>> structure = new ArrayList<>();
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName.toUpperCase(), null)) {
            while (rs.next()) {
                Map<String, Object> column = new LinkedHashMap<>();
                column.put("columnName", rs.getString("COLUMN_NAME"));
                column.put("type", rs.getString("TYPE_NAME"));
                column.put("nullable", rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable ? "YES" : "NO");
                structure.add(column);
            }
        }
        return structure;
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
            return rs.next();
        }
    }
}