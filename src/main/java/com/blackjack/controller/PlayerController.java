package com.blackjack.controller;

import com.blackjack.model.Player;
import com.blackjack.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public Mono<Player> create(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @GetMapping("/{id}")
    public Mono<Player> getById(@PathVariable String id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping
    public Flux<Player> getAll() {
        return playerService.getAllPlayers();
    }

    @PutMapping("/{id}")
    public Mono<Player> update(@PathVariable String id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return playerService.deletePlayer(id);
    }
}

