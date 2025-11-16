package com.blackjack.controller;

import com.blackjack.model.Hand;
import com.blackjack.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController() {
        this.gameService = new GameService();
    }

    @PostMapping("/start")
    public String startGame() {
        gameService.startNewGame();
        return "New Game Started!";
    }

    @PostMapping("/hit")
    public String playerHit() {
        if (gameService.isGameOver()) {
            return "Game is Over! Start a new Game.";
        }
        gameService.playerHit();
        if (gameService.isGameOver()) {
            return "Player lost, dealer wins!";
        }
        return "Card drawn, player hand value: " + gameService.getPlayerHand().calculateValue();
    }

    @PostMapping("/stand")
    public String playerStand() {
        if (gameService.isGameOver()) {
            return "Game is Over! Start a new Game.";
        }
        gameService.playerStand();
        String winner = gameService.determineWinner();
        return "Game Over! " + winner;
    }

    @GetMapping("/status")
    public String gameStatus() {
        Hand playerHand = gameService.getPlayerHand();
        Hand dealerHand = gameService.getDealerHand();
        return "Player Hand: " + playerHand.getCards() + "(value: " + playerHand.calculateValue() + ") \n" +
                "Dealer Hand: " + dealerHand.getCards() + "(value: " + dealerHand.calculateValue() + ") \n" +
                "Game Over: " + gameService.isGameOver();
    }

}

