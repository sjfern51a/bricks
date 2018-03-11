package caci.bricks.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateOrderRequest {
    private final int orderNumber;
    private final int quantity;

    public UpdateOrderRequest(@JsonProperty("orderNumber") int orderNumber,
                              @JsonProperty("quantity") int quantity) {
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
