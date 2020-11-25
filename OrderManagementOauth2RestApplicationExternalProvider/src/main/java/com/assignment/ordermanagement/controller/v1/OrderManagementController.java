package com.assignment.ordermanagement.controller.v1;

import com.assignment.ordermanagement.model.OrderDetails;
import com.assignment.ordermanagement.model.OrderStatus;
import com.assignment.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Rest Controller responsible to expose the OrderManagement RestApi and relevant crud operations
 *
 * @author chiragjain
 */
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/api/v1")
@Validated
public class OrderManagementController {

    @Autowired
    private OrderService orderService;

    /**
     * Rest operation used to get all order details in a list.
     * Only user with ROLE_ADMIN role can call this service.
     *
     * @return orderDetails - List of Orders Details.
     */
    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Rest operation used to get individual order based on provided order id
     * User with ROLE_USER or ROLE_ADMIN role can call this operation.
     * In real scenario only user who placed the order can retrieve the order since
     * it will require an additional relational database which is kept out of scope.
     *
     * @return OrderDetails - Individual Order
     */
    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public OrderDetails getOrder(@PathVariable @NotNull @Digits(integer = 6, fraction = 0) Long orderId) {
        return orderService.getOrder(orderId);
    }

    /**
     * Rest operation used to add an individual order based on provided Order resource
     * Only User with Role ROLE_USER can trigger this operation.
     * In real scenario before placing the order we should also check if the product is in stock or not
     * It will require an additional relational database which is kept out of scope.
     *
     * @return OrderDetails - ResponseEntity indicating order is created or ran in constraint exception.
     */
    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity addOrder(@RequestBody @Valid OrderDetails orderDetails) {
        orderService.addOrder(orderDetails);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Rest operation used to update an individual order status based on provided orderId and status.
     * Possible values of status can be CANCELLED,IN_TRANSIT,DELIVERED or DELAYED
     * This operation can be executed from internal system with Role as ROLE_ADMIN (for simplicity)
     * Assuming that at different status change internal system can update the status
     * so customer can track the order status.
     *
     * @param orderId - The orderId for which the status is updated
     * @param status  - OrderStatus which is updated by internal system for customer to track
     * @return ResponseEntity indicating operation http status code or ran in constraint exception.
     */
    @PutMapping("/orders/{orderId}/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity updateOrderStatus(@PathVariable @NotNull @Digits(integer = 6, fraction = 0) Long orderId, @PathVariable @NotNull OrderStatus status) {
        OrderDetails orderDetails = orderService.updateOrder(orderId, status);
        return new ResponseEntity(orderDetails, HttpStatus.OK);

    }

    /**
     * Rest operation used to Cancel/Delete the individual order based on provided orderId.
     * This will change the status of order to CANCEL.
     * It can only be performed if the order is not IN_TRANSIT or DELIVERED or REQUEST_RETURN state
     *
     * @return OrderDetails - ResponseEntity indicating order is updated or ran in constraint exception.
     */
    @DeleteMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity cancelOrder(@PathVariable @NotNull @Digits(integer = 6, fraction = 0) Long orderId) {
        OrderDetails orderDetails = orderService.cancelOrder(orderId);
        return new ResponseEntity(orderDetails, HttpStatus.OK);

    }
}
