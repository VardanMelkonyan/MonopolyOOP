package com.oop.monopolySpring.controller;

import com.oop.monopolySpring.exceptions.GameInitializationException;
import com.oop.monopolySpring.exceptions.InvalidParamException;
import com.oop.monopolySpring.model.Game;
import com.oop.monopolySpring.model.GameConstructor;
import com.oop.monopolySpring.model.Player;
import com.oop.monopolySpring.model.PlayerIdentifier;
import com.oop.monopolySpring.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mono")
public class GameController {
    private final GameService gameService;

    public GameController(){
        gameService = new GameService();
    }

    @GetMapping("/start")
    public ResponseEntity<Integer> getGameId(){
        return ResponseEntity.ok(gameService.createGameId());
    }

    @GetMapping("/game-exists")
    public ResponseEntity<Boolean> getGameId(@RequestParam(value = "gameId", defaultValue = "0") int gameId){
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
        System.out.println(name + ", " + figureName);
        return ResponseEntity.ok(gameService.getPlayer(new PlayerIdentifier(name, figureName)));
    }

}
