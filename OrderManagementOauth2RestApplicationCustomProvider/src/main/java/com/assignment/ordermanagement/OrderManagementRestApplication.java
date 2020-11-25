package com.assignment.ordermanagement;

import com.assignment.ordermanagement.model.OrderDetails;
import com.assignment.ordermanagement.model.OrderStatus;
import com.assignment.ordermanagement.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/**
 * Main Application for OrderManagement Rest API where everything begins.
 * We have a command line runner to intialize in memory database
 *
 * @author chiragjain
 */
@SpringBootApplication
public class OrderManagementRestApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderManagementRestApplication.class);

    /**
     * Main method for spring application startup.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderManagementRestApplication.class, args);
    }

    /**
     * Added Dummy records of Orders.
     *
     * @param repository
     * @return
     */
    @Bean
    public CommandLineRunner addingDummyOrders(OrderRepository repository) {
        return (args) -> {
            // save a few Orders
            logger.info("\n----START of Adding Few Orders on startup----");

            repository.save(new OrderDetails("OrderIDReference1", new BigDecimal(100), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference2", new BigDecimal(50), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference3", new BigDecimal(90), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference4", new BigDecimal(450), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference5", new BigDecimal(75), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference6", new BigDecimal(105), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference7", new BigDecimal(95), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference8", new BigDecimal("062.56"), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference9", new BigDecimal("085.45"), OrderStatus.PLACED));
            repository.save(new OrderDetails("OrderIDReference10", new BigDecimal("457.45"), OrderStatus.PLACED));
            logger.info("\n----END of Adding Few Orders on startup----");
        };

    }

}
