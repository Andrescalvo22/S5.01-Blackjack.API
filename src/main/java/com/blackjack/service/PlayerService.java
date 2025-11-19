package com.blackjack.service;

import com.blackjack.model.Player;
import com.blackjack.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Player> getPlayerById(String id) {
        return playerRepository.findById(id);
    }

    public Mono<Player> createPlayer(Player player) {
        return playerRepository.save(player);
    }
}

