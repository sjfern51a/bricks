package caci.bricks.controller;

import caci.bricks.model.order.Order;
import caci.bricks.model.request.CreateOrderRequest;
import caci.bricks.model.request.UpdateOrderRequest;
import caci.bricks.model.response.OrderNumberResponse;
import caci.bricks.storage.OrderStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderStorage mockOrderStorage;

    @Test
    public void post_orderIsCreated() throws Exception {
        int orderedQuantity = 2;
        Order order = new Order(1, orderedQuantity);
        when(mockOrderStorage.create(orderedQuantity)).thenReturn(order);
        String orderResponseJson = objectMapper.writeValueAsString(new OrderNumberResponse(order.getOrderNumber()));
        String requestJson = objectMapper.writeValueAsString(new CreateOrderRequest(orderedQuantity));

        mockMvc.perform(
                post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(orderResponseJson, true));

        verify(mockOrderStorage).create(orderedQuantity);
        verifyNoMoreInteractions(mockOrderStorage);
    }

    @Test
    public void get_existingOrderNumber_orderIsReturned() throws Exception {
        Order order = new Order(1, 10);
        when(mockOrderStorage.fetch(1)).thenReturn(order);
        String orderJson = objectMapper.writeValueAsString(order);

        mockMvc.perform(
                get("/order/{orderNumber}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(orderJson, true));

        verify(mockOrderStorage).fetch(1);
        verifyNoMoreInteractions(mockOrderStorage);
    }

    @Test
    public void get_invalidOrderNumber_noOrderReturned() throws Exception {
        when(mockOrderStorage.fetch(1)).thenReturn(null);

        mockMvc.perform(
                get("/order/{orderNumber}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(mockOrderStorage).fetch(1);
        verifyNoMoreInteractions(mockOrderStorage);
    }

    @Test
    public void list_existingOrdersReturned() throws Exception {
        List<Order> orders = newArrayList(
            new Order(1, 10),
            new Order(2, 20),
            new Order(3, 30)
        );
        when(mockOrderStorage.list()).thenReturn(orders);
        String ordersJson = objectMapper.writeValueAsString(orders);

        mockMvc.perform(
                get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(ordersJson, true));

        verify(mockOrderStorage).list();
        verifyNoMoreInteractions(mockOrderStorage);
    }

    @Test
    public void put_existingOrderNumber_updatedOrderIsReturned() throws Exception {
        Order order = new Order(1, 20);
        when(mockOrderStorage.update(1, 20)).thenReturn(order);
        String orderResponseJson = objectMapper.writeValueAsString(new OrderNumberResponse(order.getOrderNumber()));
        String requestJson = objectMapper.writeValueAsString(new UpdateOrderRequest(1, 20));

        mockMvc.perform(
                put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(orderResponseJson, true));

        verify(mockOrderStorage).update(1, 20);
        verifyNoMoreInteractions(mockOrderStorage);
    }

    @Test
    public void put_invalidOrderNumber_noOrderIsReturned() throws Exception {
        when(mockOrderStorage.update(1, 20)).thenReturn(null);
        String requestJson = objectMapper.writeValueAsString(new UpdateOrderRequest(1, 20));

        mockMvc.perform(
                put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(mockOrderStorage).update(1, 20);
        verifyNoMoreInteractions(mockOrderStorage);
    }
}