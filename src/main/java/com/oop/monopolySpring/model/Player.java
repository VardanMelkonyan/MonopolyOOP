package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.storage.GameStorage;

import java.util.ArrayList;

public class Player {
    public enum Figure {CAR, DOG, HAT, SHOE}

    private final String name;
    private final ArrayList<Property> properties;
    private Figure figure;
    private int balance;
    private int position;
    private int outOfJailCards;
    private boolean inJail;
    private int daysInJail;
    private int doubles;

    // Constructor
    public Player(String name, String figureName) throws InvalidParamException {
        this.name = name;
        this.setBalance(1500);
        this.properties = new ArrayList<Property>();
        this.position = 0;
        this.outOfJailCards = 0;
        this.inJail = false;
        this.doubles = 0;
        this.daysInJail = 0;
        this.setFigure(figureName);
    }

    // Accessors
    public String getName() {
        return this.name;
    }

    public ArrayList<Property> getProperties() {
        return new ArrayList<Property>(this.properties);
    }

    public int getBalance() {
        return this.balance;
    }

    // Mutators
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position % 40;
    }

    public int getOutOfJailCards() {
        return outOfJailCards;
    }

    public Figure getFigure() {
        return figure;
    }

    public boolean isInJail() {
        return inJail;
    }

    public boolean isTurn() {
        return GameStorage.getGame().getCurrentPlayer() == this;
    }

    public int getDoubles() {
        return doubles;
    }

    public void incrementOutOfJailCards() {
        outOfJailCards++;
    }

    public void resetDoubles() {
        this.doubles = 0;
    }

    public void incrementDoubles() {
        this.doubles++;
    }

    // Setting the figure of player
    public void setFigure(String figureName) throws InvalidParamException {
        for (Figure f : Figure.values()) {
            if (f.name().equals(figureName)) {
                this.figure = f;
                break;
            }
        }
        if (figure == null)
            throw new InvalidParamException("The passed name of the figure is invalid");
    }

    // Method that adds specified amount of money to balance of player
    public void addBalance(int amount) {
        this.balance += amount;
    }

    // Method that subtracts specified amount of money from balance of player
    public void subtractBalance(int amount) throws NotEnoughMoneyException {
        if (amount > balance)
            throw new NotEnoughMoneyException(amount);
        this.balance -= amount;
    }

    public void subtractBalance(int amount, boolean required) throws NotEnoughMoneyException {
        if (amount > balance)
            throw new NotEnoughMoneyException(amount, required);
        this.balance -= amount;
    }

    // Method that adds properties to ArrayList
    public void addProperty(Property property) {
        properties.add(property);
    }

    // Method that removes properties from ArrayList
    public void removeProperty(Property property) {
        properties.remove(property);
    }

    // Method that sends player to jail
    public void goToJail() {
        inJail = true;
        position = 10;
    }

    // Method that sends player to jail if he gets 3 doubles sequentially
    public boolean goToJailWithDouble() {
        if (this.doubles == 3) {
            inJail = true;
            position = 10;
            resetDoubles();
            return true;
        }
        return false;
    }

    // Method that gets player out of jail
    public void getOutOfJail() throws NotEnoughMoneyException {
        if (outOfJailCards > 0)
            outOfJailCards--;
        else {
            this.subtractBalance(50);
            daysInJail = 0;
        }
        inJail = false;
    }

    // Method that takes out of jail if you get doubles in dice roll
    public boolean getOutOfJailWithDouble() {
        if (this.doubles == 1) {
            inJail = false;
            resetDoubles();
            daysInJail = 0;
            return true;
        }
        return false;
    }

    // Method that helps player to move
    public void move(int steps) throws OutOfBoardBoundsException, NotEnoughMoneyException,
            InvalidPositionException, CardNotFoundException {
        if ((position + steps) > 39) {
            this.addBalance(200);
            System.out.println("You passed GO, get $200");
        }
        position = (position + steps) % 40;

        switch (Board.PositionType.getPositionType(position)) {
            case CHANCE:
                System.out.println("You are currently on Chance unit " + this.getName()); /// Demo
                Chance.getChance(this);
                break;
            case PROPERTY:
                Property p = Board.getPropertyAtIndex(position);
                System.out.println("You are currently on " + p.getName() + ", which costs $" + p.getPrice());
                if (!p.isTaken())
                    Game.buyOrNot(this, p);
                else if (!p.isMortgaged()) {
                    this.charge(p);
                }
                break;
            case GO:
                System.out.println("You are on GO");
                break;
            case PARKING:
                System.out.println("You are on Free Parking");
                break;
            case JAIL:
                System.out.println("You are just visiting Jail");
                break;
            case GO_TO_JAIL:
                System.out.println("You are going to Jail");
                this.goToJail();
                break;
            case INCOME_TAX:
                System.out.println("You must pay income tax of $200");
                this.subtractBalance(200, true);
                break;
            case LUXURY_TAX:
                System.out.println("You must pay luxury tax of $100");
                this.subtractBalance(100, true);
                break;
            case COMMUNITY:
                System.out.println("You are currently on Community Chest unit " + this.getName());
                CommunityChest.getCommunityCard(this);
                break;
        }
    }

    //Method that buys a property
    public void buyProperty(Property property) throws NotEnoughMoneyException {
        this.subtractBalance(property.getPrice());
        this.properties.add(property);
        property.setOwner(this);
    }

    public void buyProperty(Property property, int amount) throws NotEnoughMoneyException {
        this.subtractBalance(amount);
        this.properties.add(property);
        property.setOwner(this);
    }

    // Method that helps player to pay charge
    public void charge(Property property) throws NotEnoughMoneyException {
        int amount = property.getRent();
        System.out.println("You are on " + property.getOwner().getName() + "'s property, pay $" + amount);
        this.subtractBalance(amount, true);
        property.getOwner().addBalance(amount);
    }

    // Method that helps player to pay
    public void pay(Player receiver, int amount) throws NotEnoughMoneyException {
        this.subtractBalance(amount, true);
        receiver.addBalance(amount);
    }

    public void roll() throws NotEnoughMoneyException, InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException {
        int steps = Dice.rollDice();
        System.out.println("The rolled number is " + steps + ", " + this.name);
        if (Dice.isLastIsDouble()) {
            incrementDoubles();
            System.out.println("The roll was double");
        }
        if (isInJail()) {
            daysInJail++;
        }
        if (daysInJail == 4) {
            getOutOfJail();
        }
        if (isInJail() && getOutOfJailWithDouble()) {
            System.out.println("You get out of Jail because you have got doubles");
            move(steps);
            return;
        }
        if (!isInJail() && goToJailWithDouble()) {
            System.out.println("You are going to jail because you have got doubles 3 times in a row");
            return;
        }
        move(steps);
        if (Dice.isLastIsDouble())
            roll();
    }

}
