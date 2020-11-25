package com.assignment.ordermanagement.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Constraints Violation Exception.
 *
 * @author chiragjain
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ConstraintsViolationException extends RuntimeException {
    public ConstraintsViolationException(String message) {

        super(message);
    }

}
