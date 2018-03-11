package caci.bricks.storage;

import caci.bricks.model.order.Order;

public interface OrderStorage {
    Order create(int quantity);

    Order fetch(int orderNumber);
}
