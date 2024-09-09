package com.shoppingapp.OnlineShoppingApp.service.Impl;

import com.shoppingapp.OnlineShoppingApp.exception.OrderNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Order;
import com.shoppingapp.OnlineShoppingApp.model.Product;
import com.shoppingapp.OnlineShoppingApp.repository.OrderRepository;
import com.shoppingapp.OnlineShoppingApp.repository.ProductRepository;
import com.shoppingapp.OnlineShoppingApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> getAllOrdersByUser(String loginId) throws OrderNotFoundException {
        return orderRepository.findAllByLoginId(loginId).orElseThrow(() -> new OrderNotFoundException("No orders found"));
    }

    @Override
    public Order placeOrder(Order order) throws OrderNotFoundException {
        Product product = productRepository.findById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Invalid product"));
        if (order.getQuantity() <= product.getQuantity()) {
            product.setQuantity(product.getQuantity() - order.getQuantity());
            productRepository.save(product);
            order.setPrice(product.getPrice() * order.getQuantity());
            return orderRepository.save(order);
        }
        throw new OrderNotFoundException("Order not placed");
    }

    @Override
    public void cartOrders(List<Order> orderList) throws OrderNotFoundException {
        for (Order order:
             orderList) {
            Product product = productRepository.findById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Invalid product"));
            if (order.getQuantity() <= product.getQuantity()) {
                product.setQuantity(product.getQuantity() - order.getQuantity());
                productRepository.save(product);
                order.setPrice(product.getPrice() * order.getQuantity());
                orderRepository.save(order);
            }
        }
    }

    @Override
    public List<Order> getAllOrders() throws OrderNotFoundException {
        return orderRepository.findAll();
    }
}
