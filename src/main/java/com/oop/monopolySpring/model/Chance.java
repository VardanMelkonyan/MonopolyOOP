package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;

public enum Chance implements Card{
    GO(0, "Go to Start"),
    ILLINOIS_AVE(1, "Go to Illinois avenue"),
    NEAR_UTILITY(2, "Go to nearest utility"),
    NEAR_RAILROAD(3, "Go to nearest railroad"),
    ST_CHARLES_PLACE(4, "Go to St. Charles if pass start receive $200"),
    PROFIT_50(5, "Get $50"),
    GET_OUT_OF_JAIL_CARD(6, "Get a get out of jail card"),
    GO_BACK(7, "Go back 3 steps"),
    GO_TO_JAIL_WITHOUT_200(8, "Go to jail if pass start do not collect $200"),
    GENERAL_REPAIRS(9, "Make general repairs  for each house-$25 for each hotel - $100"),
    POOR_TAX(10, "Pay poor tax $15"),
    GO_TO_READING_RAILROAD(11, "Go to reading railroad if pass start receive $200"),
    GO_TO_BOARDWALK(12, "Go to Boardwalk"),
    PAY_EACH_PLAYER_50(13, "Pay each player $50"),
    PROFIT_150(14, "Get $150 as a building loan"),
    PROFIT_100(15, "Get $100 for winning crossword competition");

    private static final ArrayList<Chance> chances = new ArrayList<Chance>();
    private final int id;
    private final String description;

    Chance(int number, String description) {
        this.id = number;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void init() {
        Collections.addAll(chances, Chance.values());
        Collections.shuffle(chances);
    }

    public static Chance getChance(Player player) throws NotEnoughMoneyException, InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException, InvalidParamException {
        Chance myChance = chances.get(0);
        System.out.println("According to your chance card you should " + myChance.getDescription());
        myChance.chanceAction(player);
        chances.remove(0);
        chances.add(myChance);
        return myChance;
    }

    private void chanceAction(Player player) throws NotEnoughMoneyException, InvalidPositionException,
            CardNotFoundException, OutOfBoardBoundsException, InvalidParamException {
        switch (id) {
            case 0 -> goToGo(player);
            case 1 -> goToIllinois(player);
            case 2 -> goToNearestUtility(player);
            case 3 -> goToNearestRailroad(player);
            case 4 -> goToStCharles(player);
            case 5 -> getMoney(50, player);
            case 6 -> getOutOfJailCard(player);
            case 7 -> goBack3Spaces(player);
            case 8 -> goDirectlyToJail(player);
            case 9 -> repairHousesAndHotels(player);
            case 10 -> payMoney(15, player);
            case 11 -> goToReadingRailroad(player);
            case 12 -> goToBoardwalk(player);
            case 13 -> payEachPlayer(50, player);
            case 14 -> getMoney(150, player);
            case 15 -> getMoney(100, player);
            default -> throw new CardNotFoundException();
        }
    }
}
