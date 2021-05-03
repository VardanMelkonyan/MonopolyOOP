package com.oop.monopolySpring.model;


import com.oop.monopolySpring.exceptions.NotEnoughMoneyException;
import com.oop.monopolySpring.storage.GameStorage;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    private static Scanner scan = new Scanner(System.in);

    public static void trade(Property property1, Property property2, int amount1, int amount2, Player player1, Player player2) {

    }

    private static Bet askForBet(Player player) {
        System.out.print("Your bet " + player.getName() + ": ");
        int bet = scan.nextInt();
        return (new Bet(bet, player));
    }

    private static class Bet {
        final int bet;
        final Player player;

        Bet (int bet, Player player) {
            this.bet = bet;
            this.player = player;
        }

        public static Bet maxBet(ArrayList<Bet> bets) {
            Bet result = bets.get(0);
            for (Bet b : bets) {
                if (b.bet > result.bet)
                    result = b;
            }

            return result;
        }
    }

    public static void setAuction(Property property) throws NotEnoughMoneyException {
        if (!property.isTaken()) {
            ArrayList<Bet> bets = new ArrayList<Bet>();
            System.out.println("The following property is being auctioned. Please make your bets");
            for (Player p : GameStorage.getGame().players)
                bets.add(askForBet(p));


            while (true) {
                Bet winner = Bet.maxBet(bets);
                bets.clear();
                bets.add(winner);

                System.out.println("The maximum bet is " + winner.bet + " made by " + winner.player.getName());
                System.out.println("If you wanna make additional bets type yes and make your bet, else type no");

                for (int i = 0; i < GameStorage.getGame().players.size(); i++) {
                    Player p = GameStorage.getGame().players.get(i);
                    System.out.print("Do you wanna make a bet " + p.getName() + ": ");
                    String answer = scan.next().toUpperCase();
                    if (answer.equals("YES"))
                        bets.add(askForBet(p));
                    else if (answer.equals("NO"))
                        System.out.println("Thank you for answer!!!");
                    else {
                        System.out.println("Please type YES or NO");
                        i--;
                    }
                }
                if (bets.size() == 1) {
                    System.out.println("The property is sold to " + winner.player.getName());
                    winner.player.buyProperty(property, winner.bet);
                    break;
                }
            }
        }
    }
}
