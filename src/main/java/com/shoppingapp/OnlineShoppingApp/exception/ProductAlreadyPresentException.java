package com.shoppingapp.OnlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product ID already present in database")
public class ProductAlreadyPresentException extends Throwable {
}
