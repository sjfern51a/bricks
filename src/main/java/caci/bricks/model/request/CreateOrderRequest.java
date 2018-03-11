package caci.bricks.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrderRequest {
    private final int quantity;

    public CreateOrderRequest(@JsonProperty("quantity") int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
