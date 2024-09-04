package com.shoppingapp.OnlineShoppingApp.controller;

import com.shoppingapp.OnlineShoppingApp.exception.OrderNotFoundException;
import com.shoppingapp.OnlineShoppingApp.model.Order;
import com.shoppingapp.OnlineShoppingApp.service.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/shopping")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.placeOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{loginId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllOrders(@PathVariable String loginId) throws OrderNotFoundException {
        List<Order> orderList = orderService.getAllOrdersByUser(loginId);
        if (orderList != null) {
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("order list is empty", HttpStatus.NO_CONTENT);
        }
    }
}
