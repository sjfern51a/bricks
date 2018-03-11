package caci.bricks.storage;

import caci.bricks.model.order.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MapBackedOrderStorage implements OrderStorage {

    private final AtomicInteger orderNumber;
    private final Map<Integer, Order> orderMap;

    public MapBackedOrderStorage() {
        this.orderNumber = new AtomicInteger();
        this.orderMap = new HashMap<>();
    }

    @Override
    public Order create(int quantity) {
        Order order = new Order(nextOrderReference(), quantity);
        orderMap.put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order fetch(int orderNumber) {
        return orderMap.get(orderNumber);
    }

    @Override
    public List<Order> list() {
        return Collections.unmodifiableList(new ArrayList<>(orderMap.values()));
    }

    private int nextOrderReference() {
        return orderNumber.incrementAndGet();
    }
}
