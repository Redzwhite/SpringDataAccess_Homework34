package org.example.mapper;

import org.example.model.Cart;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<Cart> {
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        return cart;
    }
}

