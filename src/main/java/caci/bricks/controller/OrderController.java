package caci.bricks.controller;

import caci.bricks.model.order.Order;
import caci.bricks.model.request.CreateOrderRequest;
import caci.bricks.storage.OrderStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/order/{orderNumber}")
    public Order getOrder(@PathVariable int orderNumber) {
        return orderStorage.fetch(orderNumber);
    }
}
