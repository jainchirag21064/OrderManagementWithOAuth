package com.assignment.ordermanagement.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * This is the Pojo Class representing the OrderDetails Resource
 *
 * @author chiragjain
 */
@Data
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Reference Number must not be empty or null.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Reference Number can be AlphaNumeric.")
    private String referenceNumber;

    @NotNull(message = "Total Price can not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total Price must be greater than 0.0.")
    @Digits(integer = 3, fraction = 2, message = "Total Price numeric value out of bounds (<3 digits>.<2 digits> expected).")
    private BigDecimal totalPrice;

    @NotNull(message = "Order Status can not be null.")
    private OrderStatus status;

    @UpdateTimestamp
    private Timestamp lastUpdate;

    public OrderDetails() {
    }

    public OrderDetails(String referenceNumber, BigDecimal totalPrice, OrderStatus status) {
        this.referenceNumber = referenceNumber;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
