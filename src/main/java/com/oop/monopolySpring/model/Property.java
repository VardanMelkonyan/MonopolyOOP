package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.NotEnoughMoneyException;

public abstract class Property {
    private final int price;
    private final String name;
    private final int mortgageValue;
    private boolean mortgaged;
    private final PGroup group;
    private Player owner;

    public enum PGroup {
        COLORED, UTILITY, RAIL_ROAD
    }

    public Property(String name, int price, int mortgageValue, PGroup group) {
        this.name = name;
        this.price = price;
        this.mortgageValue = mortgageValue;
        this.group = group;
        this.owner = null;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public abstract int getRent();

    protected Player getOwner() {
        return owner;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public PGroup getGroup() {
        return group;
    }

    public boolean isTaken() {
        return owner != null;
    }

    public void setMortgaged() {
        owner.addBalance(this.mortgageValue);
        this.mortgaged = true;
    }

    public void liftMortgage() throws NotEnoughMoneyException {
        owner.subtractBalance(mortgageValue + (int)(0.1 * mortgageValue));
        mortgaged = false;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void sell(Player newOwner, int cost) throws NotEnoughMoneyException {
        newOwner.subtractBalance(cost);
        owner.addBalance(cost);
        owner.removeProperty(this);
        newOwner.addProperty(this);
        this.owner = newOwner;
    }
    
    public void payRent(Player player) throws NotEnoughMoneyException {
        player.subtractBalance(getRent());
        owner.addBalance(getRent()); 
    }

    public void reset() {
        owner = null;
    }


}
