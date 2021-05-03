package com.oop.monopolySpring.storage;

import com.oop.monopolySpring.model.Game;

public class GameStorage {
    private static int gameId;
    private static Game game;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameStorage.game = game;
    }

    public static int getGameId() {
        return gameId;
    }

    public static void setGameId(int gameId) {
        GameStorage.gameId = gameId;
    }
}
