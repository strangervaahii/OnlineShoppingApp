package com.shoppingapp.OnlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order not found in the database")
public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String s){
      super(s);
    }
}
