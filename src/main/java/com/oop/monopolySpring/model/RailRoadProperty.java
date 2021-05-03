package com.oop.monopolySpring.model;

public class RailRoadProperty extends Property{
    private final int[] pairIndexes = new int[]{5, 15, 25, 35};

    public RailRoadProperty(String name) {
        super(name, 200, 100, PGroup.RAIL_ROAD);
    }

    @Override
    public int getRent() {
        int sameOwner = 0;
        for (int index: pairIndexes) {
            if (getOwner() == Board.getPropertyAtIndex(index).getOwner())
                sameOwner++;
        }
        switch (sameOwner) {
            case 1:
                return 25;
            case 2:
                return 50;
            case 3:
                return 100;
            case 4:
                return 200;
            default:
                return 0;
        }
    }
}
