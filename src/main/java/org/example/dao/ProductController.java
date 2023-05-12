package org.example.dao;
import org.example.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productDao.addProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return productDao.getProductById(id);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {
        productDao.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productDao.deleteProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
}


