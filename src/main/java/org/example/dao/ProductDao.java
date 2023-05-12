package org.example.dao;

import org.example.mapper.ProductMapper;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM Products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductMapper());
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Products";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public void update(int id, Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        jdbcTemplate.update(sql, product.getName(), product.getPrice(), id);
    }

}



