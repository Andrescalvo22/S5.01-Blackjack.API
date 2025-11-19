package com.blackjack.controller;

import com.blackjack.model.Game;
import com.blackjack.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/start/{playerId}")
    public Mono<Game> startGame(@PathVariable String playerId) {
        return gameService.startGame(playerId);
    }

    @GetMapping("/{id}")
    public Mono<Game> getGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @PostMapping("/{id}/hit")
    public Mono<Game> hit(@PathVariable String id) {
        return gameService.hit(id);
    }

    @PostMapping("/{id}/stand")
    public Mono<Game> stand(@PathVariable String id) {
        return gameService.stand(id);
    }
}


