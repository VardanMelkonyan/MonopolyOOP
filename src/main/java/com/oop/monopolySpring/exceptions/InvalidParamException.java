package com.oop.monopolySpring.exceptions;

public class InvalidParamException extends Exception {
    public InvalidParamException() {
        super("Invalid parameter was passed!");
    }

    public InvalidParamException(String message) {
        super(message);
    }
}
