package com.shoppingapp.OnlineShoppingApp.controller;

import com.shoppingapp.OnlineShoppingApp.exception.ProductAlreadyPresentException;
import com.shoppingapp.OnlineShoppingApp.exception.ProductNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Product;
import com.shoppingapp.OnlineShoppingApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/shopping")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws ProductAlreadyPresentException {
        if (productService.addProduct(product) != null) {
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        if (productList != null) {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("product list is empty", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete/{pid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable("pid") int pid) {
        if (productService.deleteProduct(pid)) {
            return new ResponseEntity<>("Product deleted from the table", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product is not deleted from the table", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{pid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable("pid") int pid) {
        if (productService.updateProduct(product, pid)) {
            return new ResponseEntity<>("Product record is updated from the table", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product record is not updated from the table", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{productName}/update/{pid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> productStatusUpdate(@PathVariable String productName, @PathVariable("pid") int pid) throws ProductNotFoundException {
        Product product = productService.productStatusUpdate(productName, pid);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
