package com.oop.monopolySpring.exceptions;

public class IsNegativeException extends Exception{
    public IsNegativeException(){
        super("Error: the value is negative!!!");
    }

    public IsNegativeException(String message){
        super(message);
    }
}
