package com.oop.monopolySpring.service;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.model.*;
import com.oop.monopolySpring.model.identifiers.PlayerIdentifier;
import com.oop.monopolySpring.model.identifiers.TradeIdentifier;
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
            InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException, InvalidParamException {
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

    public Property buyProperty(Player player, int propertyIndex) throws NotEnoughMoneyException, InvalidParamException {
        Property property = Board.getPropertyAtIndex(propertyIndex);
        player.buyProperty(property);
        return property;
    }

    public Property buyWithAuction(Player player, int propertyIndex, int amount) throws NotEnoughMoneyException, InvalidParamException {
        Property property = Board.getPropertyAtIndex(propertyIndex);
        player.buyProperty(property, amount);
        return property;
    }

    public Property buildHoseOrHotel(int propertyIndex) throws InvalidParamException, NotEnoughMoneyException {
        try {
            ColoredProperty property = (ColoredProperty) Board.getPropertyAtIndex(propertyIndex);
            property.buildHouse();
            return property;
        } catch (NotEnoughMoneyException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidParamException("Invalid property index");
        }
    }

    public Property sellHouseOrHotel(int propertyIndex) throws InvalidParamException {
        try {
            ColoredProperty property = (ColoredProperty) Board.getPropertyAtIndex(propertyIndex);
            property.sellHouseOrHotel();
            return property;
        } catch (Exception e) {
            throw new InvalidParamException("Invalid property index");
        }
    }

    public Game trade(TradeIdentifier trade) throws InvalidParamException, NotEnoughMoneyException {
        Bank.trade(trade.getPropertyAtIndex1(), trade.getPropertyAtIndex2(), trade.getAmount1(), trade.getAmount2(), trade.getPlayer1().getPlayer(), trade.getPlayer2().getPlayer());
        return GameStorage.getGame();
    }

    public boolean checkForBankruptcy(PlayerIdentifier playerIdentifier, int debtAmount) {
        Player player = playerIdentifier.getPlayer();
        int fortune = Bank.checkFortune(player);

        return debtAmount > fortune;
    }

}

