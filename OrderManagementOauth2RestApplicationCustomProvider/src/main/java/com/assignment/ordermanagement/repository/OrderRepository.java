package com.assignment.ordermanagement.repository;

import com.assignment.ordermanagement.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Repository for OrderDetails extending the CRUD Repository to get the CRUD operations
 *
 * @author chiragjain
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDetails, Long> {

}
