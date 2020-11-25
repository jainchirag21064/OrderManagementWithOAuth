package com.assignment.ordermanagement.model;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OrderDetailsValidation {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenReferenceNumberIsNotAlphaNumericThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderID_1_Reference", new BigDecimal(100), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isNotNull();
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Reference Number can be AlphaNumeric."));

    }

    @Test
    public void whenReferenceNumberIsNullThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails(null, new BigDecimal(100), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isNotNull();
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Reference Number must not be empty or null."));

    }

    @Test
    public void whenReferenceNumberIsEmptyThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("", new BigDecimal(100), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isNotNull();
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Reference Number must not be empty or null."));

    }

    @Test
    public void whentoTalPriceIsNullThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", null, OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isNotNull();
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Total Price can not be null."));

    }

    @Test
    public void whentoTalPriceIsZeroThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", new BigDecimal(0.0), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Total Price must be greater than 0.0."));

    }

    @Test
    public void whentoTalPriceIsMoreThan3DigitThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", new BigDecimal(6244.56), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Total Price numeric value out of bounds (<3 digits>.<2 digits> expected)."));

    }

    @Test
    public void whentoTalPriceIsLessThan3DigitThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", new BigDecimal(62.56), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Total Price numeric value out of bounds (<3 digits>.<2 digits> expected)."));

    }

    @Test
    public void whentoTalPriceIsMoreThan3DigitFractionThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", new BigDecimal(062.568), OrderStatus.PLACED);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Total Price numeric value out of bounds (<3 digits>.<2 digits> expected)."));

    }

    @Test
    public void whentoOrderStatusIsNullThenShouldGiveConstraintViolations() {
        OrderDetails orderDetails = new OrderDetails("OrderIDReference1", new BigDecimal(122.0), null);

        Set<ConstraintViolation<OrderDetails>> violations = validator.validate(orderDetails);

        assertThat(violations.size()).isNotNull();
        violations.forEach(action -> assertThat(action.getMessage())
                .isEqualTo("Order Status can not be null."));

    }
}
