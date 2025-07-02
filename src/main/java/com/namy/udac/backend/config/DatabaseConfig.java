package com.namy.udac.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.namy.udac.backend.repository.userRepo.UserRepo_JDBC;
import com.namy.udac.backend.repository.userRepo.UserRepository;

@Configuration
public class DatabaseConfig {
    // Configure the DataSource (JDBC connection)
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://centerbeam.proxy.rlwy.net:37466/railway?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("wvrwHCGasNgfeWwURIdljwbNlliILAtJ");
        return dataSource;
    }

     // Configure JdbcTemplate with the DataSource
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // H2 for SQL tutorial execution
    @Bean(name = "h2DataSource")
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserRepo_JDBC(jdbcTemplate);
    }
}
