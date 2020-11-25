package com.assignment.ordermanagement.service;

import com.assignment.ordermanagement.error.ConstraintsViolationException;
import com.assignment.ordermanagement.error.ConstraintsViolationExceptionMessage;
import com.assignment.ordermanagement.model.OrderDetails;
import com.assignment.ordermanagement.model.OrderStatus;
import com.assignment.ordermanagement.repository.OrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Class responsible to expose the CRUD Repository
 *
 * @author chiragjain
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Method Responsible to get all the Orders from memory
     *
     * @return List orders
     */
    public List getAllOrders() {
        List orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    /**
     * Method Responsible to get the OrderDetails from memory based on provided Order id
     *
     * @param id - Unique Identification of Order
     * @return orderDetails - OrderDetails
     */
    public OrderDetails getOrder(Long id) {
        //Here a check also should be done if indeed the user is authorized to view the order.
        //Since we do not want User A to be able to view the orders of User B
        return orderRepository.findById(id).orElseThrow(() -> new ConstraintsViolationException(ConstraintsViolationExceptionMessage.NOT_FOUND));
    }

    /**
     * Method Responsible to add a new Order provided the product itself does exist.
     * In Real scenario a check should be made if product for which order is placed is in Stock
     * that check is kept out of scope
     *
     * @param orderDetails - OrderDetails that needs to be added in system
     */
    public void addOrder(OrderDetails orderDetails) {
        validateOrderCanBeAdded(orderDetails);
            orderRepository.save(orderDetails);
    }

    /**
     * Method Responsible to update status of an existing Order.
     * If the Order does not exist it results in constraints violation exception
     *
     * @param id     - Unique Identification of Order whose status needs to be updated
     * @param status - Order Status that needs to be updated for the given order
     */
    public OrderDetails updateOrder(Long id, OrderStatus status) {
        OrderDetails orderDetails = getOrder(id);
        orderDetails.setStatus(status);
        return orderRepository.save(orderDetails);
    }

    /**
     * Method Responsible to cancel a existing Order.
     * If the Order does not exist it results in constraints violation exception
     *
     * @param id - Unique Identification of Order which is updated
     */
    public OrderDetails cancelOrder(Long id) {
        OrderDetails orderDetails = getOrder(id);
        if (isCancelAllowed(orderDetails.getStatus())) {
            orderDetails.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(orderDetails);
        } else {
            throw new ConstraintsViolationException(ConstraintsViolationExceptionMessage.CAN_NOT_CANCEL + orderDetails.getStatus());
        }
    }


    /**
     * This Method is to check if use can place an order.
     *  Once can not place and order which already exist and while adding order it can oly be done with status as PLACED
     * @param orderDetails
     */
    private void validateOrderCanBeAdded(OrderDetails orderDetails) {
        if (null != orderDetails.getId() && orderRepository.existsById(orderDetails.getId())){
            throw new ConstraintsViolationException(ConstraintsViolationExceptionMessage.ALREADY_EXIST);
        }else if(!orderDetails.getStatus().equals(OrderStatus.PLACED)){
            throw new ConstraintsViolationException(ConstraintsViolationExceptionMessage.ORDER_WITH_WRONG_STATUS);
        }
    }

    /**
     * This Method is to check if cancellation of Satus is possible or not
     *
     * @param status - Status of Order
     * @return boolean - True or False
     */
    private boolean isCancelAllowed(OrderStatus status) {
        Boolean isCancelAllowed = Boolean.FALSE;
        switch (status) {
            case PLACED:
            case DELAYED:
                isCancelAllowed = Boolean.TRUE;
                break;
            case IN_TRANSIT:
            case DELIVERED:
            case REQUEST_RETURN:
                isCancelAllowed = Boolean.FALSE;
                break;
            default:
                break;
        }
        return isCancelAllowed;
    }
}
