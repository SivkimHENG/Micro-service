package com.example.Order.exception;

public class IllegalOrderStateException extends RuntimeException {

    private final String orderState;
    private final String attemptedOperation;

    public IllegalOrderStateException(String message) {
        super(message);
        this.orderState = null;
        this.attemptedOperation = null;
    }

    public IllegalOrderStateException(String message,Throwable cause){
      super(message,cause);
      this.orderState = null;
      this.attemptedOperation = null;
    }

    public IllegalOrderStateException(String message, String orderState, String attemptedOperation){
      super(message);
      this.orderState = orderState;
      this.attemptedOperation = attemptedOperation;
    }
  public IllegalOrderStateException(String message, String orderState, String attemptedOperation, Throwable cause) {
    super(message, cause);
    this.orderState = orderState;
    this.attemptedOperation = attemptedOperation;
  }

  public String getOrderState() {
    return orderState;
  }

  public String getAttemptedOperation() {
    return attemptedOperation;
  }



}
