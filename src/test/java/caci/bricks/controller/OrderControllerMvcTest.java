package caci.bricks.controller;

import caci.bricks.model.order.Order;
import caci.bricks.model.request.CreateOrderRequest;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String orderJson = objectMapper.writeValueAsString(order);
        when(mockOrderStorage.create(orderedQuantity)).thenReturn(order);
        String requestJson = objectMapper.writeValueAsString(new CreateOrderRequest(orderedQuantity));

        mockMvc.perform(
                post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(orderJson));

        verify(mockOrderStorage).create(orderedQuantity);
        verifyNoMoreInteractions(mockOrderStorage);
    }
}