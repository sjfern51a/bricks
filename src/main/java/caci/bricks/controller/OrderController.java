package caci.bricks.controller;

import caci.bricks.model.order.Order;
import caci.bricks.model.request.CreateOrderRequest;
import caci.bricks.model.request.UpdateOrderRequest;
import caci.bricks.model.response.OrderNumberResponse;
import caci.bricks.storage.OrderStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private final OrderStorage orderStorage;

    @Autowired
    public OrderController(OrderStorage orderStorage) {
        this.orderStorage = orderStorage;
    }

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderNumberResponse createOrder(@RequestBody CreateOrderRequest request) {
        return fromOrder(orderStorage.create(request.getQuantity()));
    }

    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderNumber) {
        return buildResponse(orderStorage.fetch(orderNumber));
    }

    @GetMapping("/orders")
    public List<Order> listOrders() {
        return orderStorage.list();
    }

    @PutMapping("/order")
    public ResponseEntity<OrderNumberResponse> updateOrder(@RequestBody UpdateOrderRequest request) {
        return buildResponse(
                fromOrder(orderStorage.update(request.getOrderNumber(), request.getQuantity()))
        );
    }

    private static <T> ResponseEntity<T> buildResponse(T body) {
        return Optional.ofNullable(body)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    private static OrderNumberResponse fromOrder(Order order) {
        return Optional.ofNullable(order)
                .map(Order::getOrderNumber)
                .map(OrderNumberResponse::new)
                .orElse(null);
    }
}
