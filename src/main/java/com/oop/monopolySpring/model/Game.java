package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.model.identifiers.PlayerIdentifier;
import com.oop.monopolySpring.storage.GameStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    protected ArrayList<Player> players = new ArrayList<Player>();
    private int remainingNumberOfHouses;
    private int remainingNumberOfHotels;
    private final Property[] properties;
    private int[] lastRoll;
    private int turn;

    public Game(Player[] players) throws GameInitializationException {
        remainingNumberOfHotels = 12;
        remainingNumberOfHouses = 32;
        Board.setup();
        properties = Board.getProperties();
        Chance.init();
        CommunityChest.init();
        turn = 0;
        lastRoll = new int[2];
        if (players.length > 4)
            throw new GameInitializationException();
        this.players.addAll(Arrays.asList(players));
        GameStorage.setGame(this);
    }

    public static void buyOrNot(Player player, Property property) throws NotEnoughMoneyException, InvalidParamException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Do you want to buy " + property.getName() + " " + player.getName() + "?: ");
        String answer = scan.next().toUpperCase().trim();

        if (answer.equals("YES"))
            player.buyProperty(property);
        else if (answer.equals("NO"))
            Bank.setAuction(property);
        else
            System.out.println("Please write YES or NO!");
    }

    public void play() throws OutOfBoardBoundsException {
        System.out.println("Starting the game ... ");
        while (true) {
            Player currentPlayer = players.get(turn);
            System.out.println( "It is " + currentPlayer.getName() +"'s turn. $" + currentPlayer.getBalance()); /// Demo
            try {
                currentPlayer.roll();
            } catch (NotEnoughMoneyException e) {
                System.err.println(e.getMessage());
            }
            System.out.println();
            turn = (turn < players.size() - 1 ? turn + 1 : 0);
        }
    }

    public void decreaseRemainingNumberOfHouses(int number) {
        remainingNumberOfHouses -= number;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void decreaseRemainingNumberOfHotels(int number) {
        remainingNumberOfHotels -= number;
    }

    public int getRemainingNumberOfHotels() {
        return remainingNumberOfHotels;
    }

    public int getRemainingNumberOfHouses() {
        return remainingNumberOfHouses;
    }

    public int getTurn() {
        return turn;
    }

    public Player getCurrentPlayer(){
        return players.get(turn);
    }

    public int[] getLastRoll() {
        return lastRoll;
    }

    public void nextPlayer() {
        turn = (turn < players.size() - 1 ? turn + 1 : 0);
    }

    public void setLastRoll(int[] lastRoll) {
        this.lastRoll = lastRoll;
    }

    public Player getPlayerWithIdentifier(PlayerIdentifier player){
        for (Player p : this.players)
            if (player.compareToPlayer(p)) {
                return p;
            }
        System.out.println("Didnt found");
        return null;
    }

    public Property[] getProperties() {
        return properties;
    }
}
