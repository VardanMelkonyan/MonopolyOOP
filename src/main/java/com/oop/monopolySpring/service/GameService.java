package com.oop.monopolySpring.service;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.model.*;
import com.oop.monopolySpring.storage.GameStorage;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public int createGameId() {
        int id = ((int) (Math.random() * 10000));
        GameStorage.setGameId(id);
        return id;
    }

    public boolean gameExists(int gameId) {
        return GameStorage.getGameId() == gameId;
    }

    public Game createGame(PlayerIdentifier[] players) throws GameInitializationException, InvalidParamException {
        Player[] ps = new Player[players.length];
        int i = 0;
        for (PlayerIdentifier p : players) {
            ps[i] = new Player(p.getName(), p.getFigureName());
            i++;
        }
        Game game = new Game(ps);
        GameStorage.setGame(game);
        return GameStorage.getGame();
    }

    public Player getTurn() {
        return GameStorage.getGame().getCurrentPlayer();
    }

    public int[] roll(PlayerIdentifier player) throws NotYourTurnException, NotEnoughMoneyException,
            InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException {
        if (!GameStorage.getGame().getPlayerWithIdentifier(player).isTurn())
            throw new NotYourTurnException();
        GameStorage.getGame().getCurrentPlayer().roll();
        return Dice.getDice();
    }

    public boolean isInJail(PlayerIdentifier player) throws InvalidParamException {
        Player p = GameStorage.getGame().getPlayerWithIdentifier(player);
        if (p == null)
            throw new InvalidParamException();
        return p.isInJail();
    }

    public Player getPlayer(PlayerIdentifier player) {
        return GameStorage.getGame().getPlayerWithIdentifier(player);
    }

    public boolean getOutOfJail(PlayerIdentifier player) throws NotEnoughMoneyException {
        Player p = GameStorage.getGame().getPlayerWithIdentifier(player);
        if (p.isInJail()) {
            p.getOutOfJail();
            return true;
        }
        return false;
    }

    public void buyProperty(PlayerIdentifier player, int propertyIndex) throws NotEnoughMoneyException {
        Player p = GameStorage.getGame().getPlayerWithIdentifier(player);
        Property property = Board.getPropertyAtIndex(propertyIndex);
        p.buyProperty(property);
    }

    public void buyWithAuction(PlayerIdentifier player, int propertyIndex, int amount) throws NotEnoughMoneyException {
        Player p = GameStorage.getGame().getPlayerWithIdentifier(player);
        Property property = Board.getPropertyAtIndex(propertyIndex);
        p.buyProperty(property, amount);
    }

    public void buildHoseOrHotel(int propertyIndex) throws InvalidParamException, NotEnoughMoneyException {
        try {
            ColoredProperty property = (ColoredProperty) Board.getPropertyAtIndex(propertyIndex);
            property.buildHouse();
        } catch (NotEnoughMoneyException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidParamException("Invalid property index");
        }
    }

    public void sellHouseOrHotel(int propertyIndex) throws InvalidParamException {
        try {
            ColoredProperty property = (ColoredProperty) Board.getPropertyAtIndex(propertyIndex);
            property.sellHouseOrHotel();
        } catch (Exception e) {
            throw new InvalidParamException("Invalid property index");
        }
    }
}

