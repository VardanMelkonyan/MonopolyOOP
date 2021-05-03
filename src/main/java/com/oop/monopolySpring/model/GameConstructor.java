package com.oop.monopolySpring.model;

public class GameConstructor {
    private final int gameId;
    private final PlayerIdentifier[] players;

    public GameConstructor(int gameId, PlayerIdentifier[] players) {
        System.out.println("player name: " + players[0].getName());
        System.out.println("Game id: " + gameId);

        this.gameId = gameId;
        this.players = players;
    }

    public int getGameId() {
        return gameId;
    }

    public PlayerIdentifier[] getPlayers() {
        return players;
    }
}
