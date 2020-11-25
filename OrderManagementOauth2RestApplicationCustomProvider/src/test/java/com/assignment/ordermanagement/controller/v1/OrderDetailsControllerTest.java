package com.assignment.ordermanagement.controller.v1;

import com.assignment.ordermanagement.model.OrderDetails;
import com.assignment.ordermanagement.model.OrderStatus;
import com.assignment.ordermanagement.repository.OrderRepository;
import com.assignment.ordermanagement.service.OrderService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderManagementController.class)
public class OrderDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @MockBean
    private OrderRepository repository;
    @Autowired
    private WebApplicationContext context;

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Before
    public void setUp() {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        List exprctedOrders = new ArrayList();
        exprctedOrders.add(orderDetails);

        Mockito.when(service.getAllOrders())
                .thenReturn(exprctedOrders);

        Mockito.when(service.getOrder(1L))
                .thenReturn(orderDetails);


    }

    @Test
    public void getAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }

    @Test
    public void getOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }

    @Test
    public void addOrder() throws Exception {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(orderDetails)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void updateOrderStatus() throws Exception {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders/1/status/" + OrderStatus.DELIVERED)
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(orderDetails)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void cancelOrder() throws Exception {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/1")
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(orderDetails)))
                .andExpect(status().is4xxClientError());

    }
}