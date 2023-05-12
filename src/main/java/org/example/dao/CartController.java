package org.example.dao;
import org.example.model.Cart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartDao cartDao;

    public CartController(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @PostMapping
    public int createCart(@RequestBody Cart cart) {
        return cartDao.create(cart);
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable int id) {
        return cartDao.findById(id);
    }

    @PutMapping("/{id}")
    public void updateCart(@PathVariable int id, @RequestBody Cart cart) {
        cartDao.update(id, cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable int id) {
        cartDao.delete(id);
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartDao.findAll();
    }
}

