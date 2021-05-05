package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;

public enum CommunityChest implements Card {
    GO(0, "Go to Start"),
    PROFIT_75(1, "Collect $75 due to Bank error"),
    DOCTOR_FEE(2, "Pay $50 doctor's fee"),
    GET_OUT_OF_JAIL_CARD(3, "Get a get out of jail card"),
    GO_TO_JAIL_WITHOUT_200(4, "Go to jail if pass start do not getMoney $200"),
    BIRTHDAY_PROFIT(5, "Get $10 from each player as birthday gift"),
    GRAND_OPERA_NIGHT(6, "Get $50 from each player for opening the seats"),
    INCOME_TAX_REFUND(7, "Collect income tax refund $50"),
    LIFE_INSURANCE(8, "Life insurance matures getMoney $100"),
    HOSPITAL_FEE(9, "Pay hospital fee $100"),
    SCHOOL_FEE(10, "Pay school fee $50"),
    RECEIVE_CONSULTANCY_FEE(11, "Receive $25 consultancy fee"),
    PAY_FOR_STREET_REPAIRS(12, "Pay $40 per house and $115 per hotel"),
    PROFIT_10(13, "Get $10 as you won a second prize in a beauty contest"),
    INHERITANCE(14, "You inherit $100"),
    PROFIT_50(15, "From sale of stock you receive $50"),
    HOLIDAY_FUND(16, "Receive $100");

    private static final ArrayList<CommunityChest> communityCards = new ArrayList<CommunityChest>();
    private final int id;
    private final String description;

    CommunityChest(int number, String description) {
        this.id = number;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public static void init() {
        Collections.addAll(communityCards, CommunityChest.values());
        Collections.shuffle(communityCards);
    }

    public static CommunityChest getCommunityCard(Player player) throws NotEnoughMoneyException, CardNotFoundException {
        CommunityChest myCommunityCard = communityCards.get(0);
        System.out.println("According to Community Chest " + myCommunityCard.getDescription());
        myCommunityCard.communityCardAction(player);
        communityCards.remove(0);
        communityCards.add(myCommunityCard);
        return myCommunityCard;
    }

    private void communityCardAction(Player player) throws NotEnoughMoneyException, CardNotFoundException {
        switch (id) {
            case 0 -> goToGo(player);
            case 1 -> getMoney(75, player);
            case 2, 10 -> payMoney(50, player);
            case 3 -> getOutOfJailCard(player);
            case 4 -> goDirectlyToJail(player);
            case 5 -> payEachPlayer(10, player);
            case 6 -> payEachPlayer(50, player);
            case 7 -> getMoney(20, player);
            case 8, 14, 16 -> getMoney(100, player);
            case 9 -> payMoney(100, player);
            case 11 -> getMoney(25, player);
            case 12 -> payForStreetRepairs(player);
            case 13 -> getMoney(10, player);
            case 15 -> getMoney(50, player);
            default -> throw new CardNotFoundException();
        }
    }
}
