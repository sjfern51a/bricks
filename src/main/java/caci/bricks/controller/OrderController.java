package caci.bricks.controller;

import caci.bricks.model.order.Order;
import caci.bricks.model.request.CreateOrderRequest;
import caci.bricks.storage.OrderStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderStorage orderStorage;

    @Autowired
    public OrderController(OrderStorage orderStorage) {
        this.orderStorage = orderStorage;
    }

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderStorage.create(request.getQuantity());
    }
}
