package com.oop.monopolySpring.exceptions;

public class NotYourTurnException extends Exception{

    public NotYourTurnException(){
        super("Not your turn buddy!!");
    }

    public NotYourTurnException(String message){
        super(message);
    }
}
