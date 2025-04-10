package com.namy.udac.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.namy.udac.backend.repository.UserRepo_JDBC;
import com.namy.udac.backend.repository.UserRepository;

@Configuration
public class DatabaseConfig {
    // Configure the DataSource (JDBC connection)
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/udacdb");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }

     // Configure JdbcTemplate with the DataSource
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

     @Bean
    UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserRepo_JDBC(jdbcTemplate);
    }

}
