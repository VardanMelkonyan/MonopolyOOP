package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.storage.GameStorage;

import java.util.ArrayList;

public interface Card {
    default void getMoney(int amount, Player player) {
        player.addBalance(amount);
    }

    default void payMoney(int amount, Player player) throws NotEnoughMoneyException {
        player.subtractBalance(amount, true);
    }

    default void goToGo(Player player) {
        player.setPosition(0);
        player.addBalance(200);
    }

    default void payEachPlayer(int amount, Player player) throws NotEnoughMoneyException {
        for (Player p : GameStorage.getGame().players) {
            if (p != player)
                player.pay(p, amount);
        }
    }

    default void goToBoardwalk(Player player) throws NotEnoughMoneyException, InvalidParamException {
        player.setPosition(39);
        ColoredProperty p = (ColoredProperty) Board.getPropertyAtIndex(player.getPosition());
        if (!p.isTaken())
            Game.buyOrNot(player, p);
        else if (!p.isMortgaged())
            player.pay(p.getOwner(), 2 * p.getRent());
    }

    default void goToReadingRailroad(Player player) throws NotEnoughMoneyException, InvalidParamException {
        // position cannot be before reading railroad(no need for check)
        player.addBalance(200);
        player.setPosition(5);

        RailRoadProperty p = (RailRoadProperty) Board.getPropertyAtIndex(player.getPosition());
        if (!p.isTaken())
            Game.buyOrNot(player, p);
        else if (!p.isMortgaged())
            player.pay(p.getOwner(), p.getRent());
    }

    default void repairHousesAndHotels(Player player) throws NotEnoughMoneyException {
        payTaxPerHouse(player, 25, 100);
    }

    default void payForStreetRepairs(Player player) throws NotEnoughMoneyException {
        payTaxPerHouse(player, 40, 115);
    }

    default void payTaxPerHouse(Player player, int perHouseTax, int hotelTax) throws NotEnoughMoneyException {
        ArrayList<Property> list = player.getProperties();
        for (Property p : list) {
            if (p.getGroup() == Property.PGroup.COLORED) {
                final int numOfHouses = ((ColoredProperty) p).getNumberOfHouses();
                int amountToBeSubtracted = 0;
                if (numOfHouses < 5 && numOfHouses > 0)
                    amountToBeSubtracted = numOfHouses * perHouseTax;
                else if (numOfHouses == 5)
                    amountToBeSubtracted = hotelTax;
                player.subtractBalance(amountToBeSubtracted, true);
            }
        }
    }

    default void goToIllinois(Player player) {
        player.setPosition(24);
    }

    default void goToNearestUtility(Player player) throws NotEnoughMoneyException, InvalidParamException {
        if (player.getPosition() == 22 || player.getPosition() == 36) {
            player.setPosition(28);
        } else {
            player.setPosition(12);
        }

        UtilityProperty p = (UtilityProperty) Board.getPropertyAtIndex(player.getPosition());
        if (!p.isTaken())
            Game.buyOrNot(player, p);
        else if (!p.isMortgaged()) {
            Dice.rollDice();
            player.charge(p);
        }

    }

    default void goToNearestRailroad(Player player) throws InvalidPositionException, NotEnoughMoneyException, InvalidParamException {
        switch (player.getPosition()) {
            case 7:
                player.setPosition(5);
                break;
            case 22:
                player.setPosition(25);
                break;
            case 36:
                player.setPosition(35);
            default:
                throw new InvalidPositionException();
        }

        RailRoadProperty p = (RailRoadProperty) Board.getPropertyAtIndex(player.getPosition());
        if (!p.isTaken())
            Game.buyOrNot(player, p);
        else if (!p.isMortgaged())
            player.pay(p.getOwner(), 2 * p.getRent());
    }

    default void goToStCharles(Player player) {
        player.setPosition(24);
    }

    default void getOutOfJailCard(Player player) {
        player.incrementOutOfJailCards();
    }

    default void goBack3Spaces(Player player) throws NotEnoughMoneyException,
            InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException, InvalidParamException {
        player.move(-3);
    }

    default void goDirectlyToJail(Player player) {
        player.setPosition(10);
    }

}
