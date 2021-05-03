package com.oop.monopolySpring.exceptions;

public class CardNotFoundException extends Exception{

        public CardNotFoundException() {
            super("Error: THe specified card does not exist!");
        }

        public CardNotFoundException(String message) {
            super(message);
        }
}
