package com.oop.monopolySpring.model.identifiers;

import com.oop.monopolySpring.model.Board;
import com.oop.monopolySpring.model.Property;

public class TradeIdentifier {
    private PlayerIdentifier player1;
    private PlayerIdentifier player2;
    private int propertyIndex1;
    private int propertyIndex2;
    private int amount1;
    private int amount2;

    public TradeIdentifier(PlayerIdentifier player1, PlayerIdentifier player2, int propertyIndex1, int propertyIndex2, int amount1, int amount2) {
        this.player1 = player1;
        this.player2 = player2;
        this.propertyIndex1 = propertyIndex1;
        this.propertyIndex2 = propertyIndex2;
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public int getAmount1() {
        return amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public Property getPropertyAtIndex1() {
        return Board.getPropertyAtIndex(propertyIndex1);
    }

    public Property getPropertyAtIndex2() {
        return Board.getPropertyAtIndex(propertyIndex2);
    }

    public PlayerIdentifier getPlayer1() {
        return player1;
    }

    public PlayerIdentifier getPlayer2() {
        return player2;
    }
}