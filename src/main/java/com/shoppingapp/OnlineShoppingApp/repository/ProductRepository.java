package com.shoppingapp.OnlineShoppingApp.repository;

import com.shoppingapp.OnlineShoppingApp.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product WHERE LOWER(product_name) LIKE LOWER(:product)%", nativeQuery = true)
    public Optional<List<Product>> findAllByProductName(String product);

}
