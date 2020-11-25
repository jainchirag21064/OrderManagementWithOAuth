package com.assignment.ordermanagement.error;

/**
 * Added to store Message text used for different Violation exception
 *
 * @author chiragjain
 */
public class ConstraintsViolationExceptionMessage {

    public static final String NOT_FOUND = "Order Not Found.";
    public static final String ALREADY_EXIST = "Order Already Exist.";
    public static final String CAN_NOT_CANCEL = "Order Can not be Cancelled as it is already in ";
    public static final String ORDER_WITH_WRONG_STATUS = "Order Can be added with status as PLACED onnly.";
}
