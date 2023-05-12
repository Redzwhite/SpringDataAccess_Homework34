package org.example.dao;

import org.example.mapper.CartMapper;
import org.example.mapper.ProductMapper;
import org.example.model.Cart;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;

    public CartDao(JdbcTemplate jdbcTemplate, ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = productDao;
    }

    public int create(Cart cart) {
        String sqlInsertCart = "INSERT INTO carts DEFAULT VALUES";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsertCart, Statement.RETURN_GENERATED_KEYS);
            return ps;
        }, keyHolder);

        int cartId = (int) keyHolder.getKeys().get("id");
        cart.setId(cartId);

        String sqlInsertCartProduct = "INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?)";
        for (Product product : cart.getProducts()) {
            if (productDao.getProductById(product.getId()) != null) {
                jdbcTemplate.update(sqlInsertCartProduct, cartId, product.getId());
            }
        }

        return cartId;
    }

    public void update(int id, Cart cart) {
        Cart existingCart = findById(id);
        List<Product> existingProducts = existingCart.getProducts();

        for (Product product : existingProducts) {
            if (!cart.getProducts().contains(product)) {
                String sqlDelete = "DELETE FROM carts_products WHERE cart_id = ? AND product_id = ?";
                jdbcTemplate.update(sqlDelete, id, product.getId());
            }
        }

        for (Product product : cart.getProducts()) {
            if (!existingProducts.contains(product) && productDao.getProductById(product.getId()) != null) {
                String sqlInsert = "INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?)";
                jdbcTemplate.update(sqlInsert, id, product.getId());
            }
        }
    }

    public void delete(int id) {
        String sqlDeleteCartProducts = "DELETE FROM carts_products WHERE cart_id = ?";
        jdbcTemplate.update(sqlDeleteCartProducts, id);

        String sqlDeleteCart = "DELETE FROM carts WHERE id = ?";
        jdbcTemplate.update(sqlDeleteCart, id);
    }

    public Cart findById(int id) {
        String sqlCart = "SELECT * FROM carts WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlCart, new CartMapper(), id);
    }

    public List<Cart> findAll() {
        String sql = "SELECT * FROM carts";
        List<Cart> carts = jdbcTemplate.query(sql, new CartMapper());

        for (Cart cart : carts) {
            String sqlProducts = "SELECT * FROM products p JOIN carts_products cp ON p.id = cp.product_id WHERE cp.cart_id = ?";
            List<Product> products = jdbcTemplate.query(sqlProducts, new ProductMapper(), cart.getId());

            cart.setProducts(products);
        }

        return carts;
    }
}



