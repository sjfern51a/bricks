package caci.bricks.model.order;

public class Order {
    private final int orderNumber;
    private final int quantity;

    public Order(
            int orderNumber,
            int quantity) {
        this.orderNumber = orderNumber;
        this.quantity = quantity;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getQuantity() {
        return quantity;
    }
}
