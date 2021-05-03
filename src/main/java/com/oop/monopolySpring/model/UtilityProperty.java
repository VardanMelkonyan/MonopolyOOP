package com.oop.monopolySpring.model;

public class UtilityProperty extends Property{
    private final int pairIndex;

    public UtilityProperty(String name, int pairIndex) {
        super(name, 150, 75, PGroup.UTILITY);
        this.pairIndex = pairIndex;
    }

    public int getRent() {
        return Dice.getLastRoll() * ((this.getOwner() == Board.getPropertyAtIndex(pairIndex).getOwner()) ? 10 : 4);
    }
}
