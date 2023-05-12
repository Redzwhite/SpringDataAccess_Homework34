package org.example.config;

import org.example.dao.CartDao;
import org.example.dao.ProductDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringJdbcConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ProductDao productDao(JdbcTemplate jdbcTemplate) {
        return new ProductDao(jdbcTemplate);
    }

    @Bean
    public CartDao cartDao(JdbcTemplate jdbcTemplate, ProductDao productDao) {
        return new CartDao(jdbcTemplate, productDao);
    }
}


