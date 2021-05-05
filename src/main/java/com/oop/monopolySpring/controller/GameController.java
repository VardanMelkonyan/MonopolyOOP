package com.oop.monopolySpring.controller;

import com.oop.monopolySpring.exceptions.*;
import com.oop.monopolySpring.model.*;
import com.oop.monopolySpring.model.identifiers.*;
import com.oop.monopolySpring.service.GameService;
import com.oop.monopolySpring.storage.GameStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mono")
public class GameController {
    private final GameService gameService;

    public GameController() {
        gameService = new GameService();
    }

    @GetMapping("/start")
    public ResponseEntity<Integer> getGameId() {
        return ResponseEntity.ok(gameService.createGameId());
    }

    @GetMapping("/game-exists")
    public ResponseEntity<Boolean> getGameStatus(@RequestParam(value = "gameId", defaultValue = "0") int gameId) {
        return ResponseEntity.ok(gameService.gameExists(gameId));
    }

    @PostMapping("/create-game")
    public ResponseEntity<Game> createGame(@RequestBody GameConstructor constructor) throws InvalidParamException, GameInitializationException {
        if (!gameService.gameExists(constructor.getGameId()))
            throw new InvalidParamException("Game dose not exist!");
        Game g = gameService.createGame(constructor.getPlayers());
        return ResponseEntity.ok(g);
    }

    @GetMapping("/current-player")
    public ResponseEntity<Player> getCurrentPlayer() {
        return ResponseEntity.ok(gameService.getTurn());
    }

    @GetMapping("/get-player")
    public ResponseEntity<Player> getPlayer(@RequestParam(name = "name") String name, @RequestParam(name = "figureName") String figureName) {
        return ResponseEntity.ok(gameService.getPlayer(new PlayerIdentifier(name, figureName)));
    }

    @GetMapping("/is-in-jail")
    public ResponseEntity<Boolean> isInJail(@RequestParam(name = "name") String name, @RequestParam(name = "figureName") String figureName) throws InvalidParamException {
        return ResponseEntity.ok(gameService.isInJail(new PlayerIdentifier(name, figureName)));
    }


    @PostMapping("/get-out-of-jail")
    public ResponseEntity<Boolean> getOutOfJail(@RequestBody PlayerIdentifier player) throws NotEnoughMoneyException {
        return ResponseEntity.ok(gameService.getOutOfJail(player));
    }

    @PostMapping("/buy-property")
    public ResponseEntity<Property> buyProperty(@RequestBody BuyPropertyIdentifier buyPropertyIdentifier) throws NotEnoughMoneyException, InvalidParamException {
        return ResponseEntity.ok(gameService.buyProperty(buyPropertyIdentifier.getPlayer(), buyPropertyIdentifier.getPropertyIndex()));
    }

    @PostMapping("/buy-property-auction")
    public ResponseEntity<Property> buyWithAuction(@RequestBody BuyPropertyWithAuctionIdentifier buyPropertyWithAuctionIdentifier) throws NotEnoughMoneyException, InvalidParamException {
        return ResponseEntity.ok(gameService.buyWithAuction(buyPropertyWithAuctionIdentifier.getPlayer(), buyPropertyWithAuctionIdentifier.getPropertyIndex(), buyPropertyWithAuctionIdentifier.getAmount()));
    }

    @PostMapping("/build-house-hotel")
    public ResponseEntity<Property> buildHoseOrHotel(@RequestParam(name = "propertyIndex") int propertyIndex) throws NotEnoughMoneyException, InvalidParamException {
        return ResponseEntity.ok(gameService.buildHoseOrHotel(propertyIndex));
    }

    @PostMapping("/sell-house-hotel")
    public ResponseEntity<Property> sellHoseOrHotel(@RequestParam(name = "propertyIndex") int propertyIndex) throws InvalidParamException {
        return ResponseEntity.ok(gameService.sellHouseOrHotel(propertyIndex));
    }

    @PostMapping("/trade")
    public ResponseEntity<Game> trade(@RequestBody TradeIdentifier tradeIdentifier) throws InvalidParamException, NotEnoughMoneyException {
        Game game = gameService.trade(tradeIdentifier);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/roll")
    public ResponseEntity<Game> roll(@RequestBody PlayerIdentifier playerIdentifier) throws InvalidParamException, NotEnoughMoneyException, NotYourTurnException, InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException {
        Game game = gameService.roll(playerIdentifier);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/check-for-bankruptcy")
    public ResponseEntity<Boolean> checkForBankruptcy(@RequestParam(name = "name") String name, @RequestParam(name = "figureName") String figureName, @RequestParam(name = "debtAmount") int debtAmount) {
        return ResponseEntity.ok(gameService.checkForBankruptcy(new PlayerIdentifier(name, figureName), debtAmount));
    }

    @PostMapping("/chance")
    public ResponseEntity<Chance> getChance(@RequestBody PlayerIdentifier playerIdentifier) throws InvalidParamException, NotEnoughMoneyException, InvalidPositionException, CardNotFoundException, OutOfBoardBoundsException {
        Chance chance = gameService.getChance(playerIdentifier);
        return ResponseEntity.ok(chance);
    }

    @PostMapping("/community")
    public ResponseEntity<CommunityChest> getCommunityCard(@RequestBody PlayerIdentifier playerIdentifier) throws NotEnoughMoneyException, CardNotFoundException {
        CommunityChest card = gameService.getCommunityCard(playerIdentifier);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/go-to-jail")
    public ResponseEntity<Game> goToJail(@RequestBody PlayerIdentifier playerIdentifier) {
        Game game = gameService.goToJail(playerIdentifier);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/pay-tax")
    public ResponseEntity<Game> payTax(@RequestParam(name = "name") String name, @RequestParam(name = "figureName") String figureName, @RequestParam(name = "taxAmount") int taxAmount) throws NotEnoughMoneyException {
        gameService.payTax(new PlayerIdentifier(name, figureName), taxAmount);
        return ResponseEntity.ok(GameStorage.getGame());
    }

    @PostMapping("/property-move")
    public ResponseEntity<Boolean> propertyMove(@RequestBody BuyPropertyIdentifier buyPropertyIdentifier) throws NotEnoughMoneyException {
        boolean result = gameService.propertyMove(buyPropertyIdentifier.getPlayer(), buyPropertyIdentifier.getPropertyIndex());
        return ResponseEntity.ok(result);
    }
}