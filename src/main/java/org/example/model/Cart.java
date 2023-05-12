package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private List<Product> products = new ArrayList<>();

    public Cart() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
