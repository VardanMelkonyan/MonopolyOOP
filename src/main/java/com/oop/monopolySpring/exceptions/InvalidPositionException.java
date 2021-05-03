package com.oop.monopolySpring.exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException() {
        super("Error: User position is invalid!");
    }

    public InvalidPositionException(String message) {
        super(message);
    }
}
