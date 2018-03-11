package caci.bricks.model.response;

public class OrderNumberResponse {
    private final int orderNumber;

    public OrderNumberResponse(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
}
