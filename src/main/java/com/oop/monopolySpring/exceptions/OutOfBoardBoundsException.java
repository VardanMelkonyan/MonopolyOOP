package com.oop.monopolySpring.exceptions;

public class OutOfBoardBoundsException extends Exception{
    public OutOfBoardBoundsException() {
        super("Out of board position!");
    }

    public OutOfBoardBoundsException(String message) {
        super(message);
    }
}
