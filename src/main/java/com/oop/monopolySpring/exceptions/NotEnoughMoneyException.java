package com.oop.monopolySpring.exceptions;

public class NotEnoughMoneyException extends Exception{
    public final int amount;
    public final boolean required;

    public NotEnoughMoneyException(){
        super("Error: With current amount of money action cannot be preformed");
        amount = 0;
        required = false;
    }

    public NotEnoughMoneyException(int amount, boolean required) {
        super("Error: You dont have $" + amount + " on your account");
        this.amount = amount;
        this.required = required;
    }

    public NotEnoughMoneyException(int amount) {
        super("Error: You dont have $" + amount + " on your account");
        this.amount = amount;
        this.required = false;
    }

    public NotEnoughMoneyException(String message){
        super(message);
        amount = 0;
        required = false;
    }
}
