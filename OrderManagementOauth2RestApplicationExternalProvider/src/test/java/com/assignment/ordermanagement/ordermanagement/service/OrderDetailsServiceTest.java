package com.assignment.ordermanagement.service;

import com.assignment.ordermanagement.error.ConstraintsViolationException;
import com.assignment.ordermanagement.error.ConstraintsViolationExceptionMessage;
import com.assignment.ordermanagement.model.OrderDetails;
import com.assignment.ordermanagement.model.OrderStatus;
import com.assignment.ordermanagement.repository.OrderRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OrderDetailsServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderService underTest;

    @Before
    public void setUp() {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);

        OrderDetails orderDetailsInTransit = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.IN_TRANSIT);
        OrderDetails orderDetailsDelivered = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.DELIVERED);
        OrderDetails orderDetailsReturn = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.REQUEST_RETURN);
        OrderDetails orderDetailsDelayed = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.DELAYED);

        List expectedOrders = new ArrayList();
        expectedOrders.add(orderDetails);

        Mockito.when(orderRepository.findAll())
                .thenReturn(expectedOrders);

        Mockito.when(orderRepository.findById(1L))
                .thenReturn(java.util.Optional.of(orderDetails));

        Mockito.when(orderRepository.existsById(1L))
                .thenReturn(Boolean.TRUE);

        Mockito.when(orderRepository.existsById(2L))
                .thenReturn(Boolean.FALSE);

        Mockito.when(orderRepository.save(orderDetails))
                .thenReturn(orderDetails);

        Mockito.when(orderRepository.findById(3L))
                .thenReturn(java.util.Optional.of(orderDetailsInTransit));
        Mockito.when(orderRepository.findById(4L))
                .thenReturn(java.util.Optional.of(orderDetailsDelivered));
        Mockito.when(orderRepository.findById(5L))
                .thenReturn(java.util.Optional.of(orderDetailsReturn));
        Mockito.when(orderRepository.findById(6L))
                .thenReturn(java.util.Optional.of(orderDetailsDelayed));
        Mockito.when(orderRepository.save(orderDetailsDelayed))
                .thenReturn(orderDetailsDelayed);

    }

    @Test
    public void getAllOrders() {
        List actualOrders = new ArrayList();
        actualOrders = underTest.getAllOrders();

        assertThat(actualOrders.size()).isEqualTo(1);
        assertThat(actualOrders.get(0)).isNotNull();
    }

    @Test
    public void getOrder_Success() {
        OrderDetails actualOrders = underTest.getOrder(1L);
        assertThat(actualOrders.getReferenceNumber()).isNotNull();
        assertThat(actualOrders.getTotalPrice()).isNotNull();
        assertThat(actualOrders.getReferenceNumber()).isEqualTo("UnitTest");
        assertThat(actualOrders.getTotalPrice()).isEqualTo(new BigDecimal(120));
    }

    @Test
    public void whenOrderDetailsNotFoundWhileGetOrderThenShouldGiveConstraintViolation() {
        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.NOT_FOUND);

        OrderDetails actualOrders = underTest.getOrder(2L);
    }

    @Test
    public void whenOrderDetailsExistWhileGetOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.ALREADY_EXIST);

        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        orderDetails.setId(1L);

        underTest.addOrder(orderDetails);

    }

    @Test
    public void addOrder_Success() {
        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.PLACED);
        orderDetails.setId(2L);
        underTest.addOrder(orderDetails);
    }

    @Test
    public void addOrderWhenOrderStatusIsOtherThenPlacedThenShouldGiveConstrainntViolations() {
        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.ORDER_WITH_WRONG_STATUS);

        OrderDetails orderDetails = new OrderDetails("UnitTest", new BigDecimal(120), OrderStatus.DELIVERED);
        orderDetails.setId(2L);
        underTest.addOrder(orderDetails);
    }

    @Test
    public void whenOrderDetailsNotFoundWhileUpdateOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.NOT_FOUND);
        underTest.updateOrder(2L, OrderStatus.DELAYED);

    }

    @Test
    public void updateOrder_Success() {

        OrderDetails actualOrders = underTest.updateOrder(1L, OrderStatus.DELAYED);
        assertThat(actualOrders.getReferenceNumber()).isNotNull();
        assertThat(actualOrders.getTotalPrice()).isNotNull();
        assertThat(actualOrders.getReferenceNumber()).isEqualTo("UnitTest");
        assertThat(actualOrders.getTotalPrice()).isEqualTo(new BigDecimal(120));
        assertThat(actualOrders.getStatus()).isEqualTo(OrderStatus.DELAYED);
    }

    @Test
    public void whenOrderDetailsNotFoundWhileCancelOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.NOT_FOUND);
        underTest.cancelOrder(2L);

    }

    @Test
    public void whenOrderDetailsInTransitWhileCancelOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.CAN_NOT_CANCEL + OrderStatus.IN_TRANSIT);
        underTest.cancelOrder(3L);

    }

    @Test
    public void whenOrderDetailsDeleteWhileCancelOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.CAN_NOT_CANCEL + OrderStatus.DELIVERED);
        underTest.cancelOrder(4L);

    }

    @Test
    public void whenOrderDetailsRequestReturnWhileCancelOrderThenShouldGiveConstraintViolation() {

        exceptionRule.expect(ConstraintsViolationException.class);
        exceptionRule.expectMessage(ConstraintsViolationExceptionMessage.CAN_NOT_CANCEL + OrderStatus.REQUEST_RETURN);
        underTest.cancelOrder(5L);

    }

    @Test
    public void cancelOrderWhenStatusIsPlaced_Success() {

        OrderDetails actualOrders = underTest.cancelOrder(1L);
        assertThat(actualOrders.getReferenceNumber()).isNotNull();
        assertThat(actualOrders.getTotalPrice()).isNotNull();
        assertThat(actualOrders.getReferenceNumber()).isEqualTo("UnitTest");
        assertThat(actualOrders.getTotalPrice()).isEqualTo(new BigDecimal(120));
        assertThat(actualOrders.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    public void cancelOrderWhenStatusIsDelayed_Success() {

        OrderDetails actualOrders = underTest.cancelOrder(6L);
        assertThat(actualOrders.getReferenceNumber()).isNotNull();
        assertThat(actualOrders.getTotalPrice()).isNotNull();
        assertThat(actualOrders.getReferenceNumber()).isEqualTo("UnitTest");
        assertThat(actualOrders.getTotalPrice()).isEqualTo(new BigDecimal(120));
        assertThat(actualOrders.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @TestConfiguration
    static class OrderServiceTestContextConfiguration {

        @Bean
        public OrderService orderService() {
            return new OrderService();
        }
    }

}