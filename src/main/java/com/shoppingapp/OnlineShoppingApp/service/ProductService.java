package com.shoppingapp.OnlineShoppingApp.service;

import com.shoppingapp.OnlineShoppingApp.exception.ProductAlreadyPresentException;
import com.shoppingapp.OnlineShoppingApp.exception.ProductNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product addProduct(Product product) throws ProductAlreadyPresentException;

    public Product getProductById(int pid);

    public boolean updateProduct(Product product, int pid);

    public boolean deleteProduct(int pid);

    List<Product> searchProduct(String productName) throws ProductNotFoundException;

    Product productStatusUpdate(String product, int loginId) throws ProductNotFoundException;
}
