package com.blackjack.controller;

import com.blackjack.dto.ActionResponseDTO;
import com.blackjack.dto.GameStatusDTO;
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
    public GameStatusDTO startGame() {
        gameService.startNewGame();
        return gameService.getGameStatus();
    }

    @PostMapping("/hit")
    public ActionResponseDTO playerHit() {
        return gameService.playerHitAction();
    }

    @PostMapping("/stand")
    public ActionResponseDTO playerStand() {
        return gameService.playerStandAction();
    }

    @GetMapping("/status")
    public GameStatusDTO gameStatus() {
        return gameService.getGameStatus();
    }
}

