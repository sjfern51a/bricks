package caci.bricks.storage;

import caci.bricks.model.order.Order;

import java.util.List;

public interface OrderStorage {
    Order create(int quantity);

    Order fetch(int orderNumber);

    List<Order> list();

    Order update(int orderNumber, int quantity);
}
