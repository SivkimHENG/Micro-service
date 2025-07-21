package com.example.Order.exception;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {

    private final String field;
    private final Object rejectedValue;
    private final List<String> errors;
    private final Map<String,String> fieldErrors;


    public ValidationException(String message) {
        super(message);
        this.field = null;
        this.rejectedValue = null;
        this.errors = null;
        this.fieldErrors = null;
    }

    public ValidationException(String message, String field) {
        super(message);
        this.field = field;
        this.rejectedValue = null;
        this.errors = null;
        this.fieldErrors = null;

    }
    public ValidationException(String message, String field, Object rejectedValue) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.errors = null;
        this.fieldErrors = null;
    }

    public ValidationException(String message, List<String> errors) {
        super(message);
        this.field = null;
        this.rejectedValue = null;
        this.errors = errors;
        this.fieldErrors = null;
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.field = null;
        this.rejectedValue = null;
        this.errors = null;
        this.fieldErrors = fieldErrors;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.field = null;
        this.rejectedValue = null;
        this.errors = null;
        this.fieldErrors = null;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

}
