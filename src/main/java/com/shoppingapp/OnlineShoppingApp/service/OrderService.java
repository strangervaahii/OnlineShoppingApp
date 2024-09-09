package com.shoppingapp.OnlineShoppingApp.service;

import com.shoppingapp.OnlineShoppingApp.exception.OrderNotFoundException;
import com.shoppingapp.OnlineShoppingApp.exception.ProductAlreadyPresentException;
import com.shoppingapp.OnlineShoppingApp.exception.ProductNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Order;
import com.shoppingapp.OnlineShoppingApp.model.Product;

import java.util.List;

public interface OrderService {
    public List<Order> getAllOrdersByUser(String loginId) throws OrderNotFoundException;

    public Order placeOrder(Order order) throws OrderNotFoundException;

    public void cartOrders(List<Order> orderList) throws OrderNotFoundException;

    public List<Order> getAllOrders() throws OrderNotFoundException;
}
