package com.shoppingapp.OnlineShoppingApp.repository;

import com.shoppingapp.OnlineShoppingApp.model.Order;
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
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<List<Order>> findAllByLoginId(String Order);

}
