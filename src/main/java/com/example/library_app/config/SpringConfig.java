package com.example.library_app.config;

import com.example.library_app.DAO.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.library_app")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    private final Environment environment;


    @Autowired
    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("postgres.driver"));
        dataSource.setUrl(environment.getProperty("postgres.url"));
        dataSource.setUsername(environment.getProperty("postgres.username"));
        dataSource.setPassword(environment.getProperty("postgres.password"));

        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}