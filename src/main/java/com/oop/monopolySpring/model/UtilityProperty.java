package com.oop.monopolySpring.model;

public class UtilityProperty extends Property{
    private final int pairIndex;

    public UtilityProperty(int position, String name, int pairIndex) {
        super(position, name, 150, 75, PGroup.UTILITY);
        this.pairIndex = pairIndex;
    }

    public int getRent() {
        return Dice.getLastRoll() * ((this.getOwner() == Board.getPropertyAtIndex(pairIndex).getOwner()) ? 10 : 4);
    }
}
