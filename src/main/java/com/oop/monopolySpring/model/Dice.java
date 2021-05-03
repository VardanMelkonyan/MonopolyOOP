package com.oop.monopolySpring.model;

public class Dice {
    private static int lastRoll = 0;
    private static boolean lastIsDouble = false;
    private static int[] dice = new int[2];

    private static int randomDie() {
        return (int) ((Math.random() * 6) + 1);
    }

    public static int rollDice() {
        dice[0] = randomDie();
        dice[1] = randomDie();
        lastRoll = dice[0] + dice[1];
        lastIsDouble = dice[0] == dice[1];
        return lastRoll;
    }

    public static int getLastRoll() {
        return lastRoll;
    }

    public static boolean isLastIsDouble() {
        return lastIsDouble;
    }

    public static int[] getDice() {
        return dice;
    }
}
