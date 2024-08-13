package com.shoppingapp.OnlineShoppingApp.service.Impl;

import com.shoppingapp.OnlineShoppingApp.exception.ProductAlreadyPresentException;
import com.shoppingapp.OnlineShoppingApp.exception.ProductNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Product;
import com.shoppingapp.OnlineShoppingApp.repository.ProductRepository;
import com.shoppingapp.OnlineShoppingApp.repository.UserInfoRepository;
import com.shoppingapp.OnlineShoppingApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList != null && productList.size() > 0) {
            return productList;
        } else {
            return null;
        }
    }

    @Override
    public Product addProduct(Product product) throws ProductAlreadyPresentException {
        Optional<Product> optional = productRepository.findById(product.getProductId());
        if (optional.isPresent()) {
            throw new ProductAlreadyPresentException();
        } else {
            return productRepository.saveAndFlush(product);
        }
    }

    @Override
    public Product getProductById(int pid) {
        Optional<Product> product = productRepository.findById(pid);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Override
    public boolean updateProduct(Product product, int pid) {

        Product existingProduct = productRepository.getById(pid);
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductName(product.getProductDesc());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setFeatures(product.getFeatures());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setProductStatus(product.getProductStatus());
            productRepository.saveAndFlush(existingProduct);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int pid) {
        productRepository.deleteById(pid);
        return true;
    }

    @Override
    public List<Product> searchProduct(String productName) throws ProductNotFoundException {
        Optional<List<Product>> search = productRepository.findAllByProductName(productName.toLowerCase());
        if (!search.isPresent()) {
            throw new ProductNotFoundException();
        }
        return search.get();
    }
}
