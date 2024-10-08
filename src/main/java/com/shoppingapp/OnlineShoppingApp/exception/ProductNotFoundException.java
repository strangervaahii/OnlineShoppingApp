package com.shoppingapp.OnlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found in the database")
public class ProductNotFoundException extends Throwable {
}
