package com.oop.monopolySpring.exceptions;

public class GameInitializationException extends Exception{
    public GameInitializationException() {
        super("Game initialization failed!");
    }

    public GameInitializationException(String message) {
        super(message);
    }
}
