package com.oop.monopolySpring.model.identifiers;


public class GameConstructor{
    private final int gameId;
    private final PlayerIdentifier[] players;

    public GameConstructor(int gameId, PlayerIdentifier[] players) {
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
