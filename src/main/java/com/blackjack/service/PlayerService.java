package com.blackjack.service;

import com.blackjack.exception.PlayerNotFoundException;
import com.blackjack.model.Player;
import com.blackjack.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Mono<Player> createPlayer(Player player) {
        player.setCreatedAt(Instant.now());
        player.setGamesPlayed(0);
        player.setScore(0);
        return playerRepository.save(player);
    }

    public Mono<Player> getPlayerById(String id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + id)));
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Player> updatePlayer(String id, Player updated) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + id)))
                .flatMap(existing -> {
                    existing.setName(updated.getName());
                    return playerRepository.save(existing);
                });
    }

    public Mono<Void> deletePlayer(String id) {
        return playerRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + id)))
                .then(playerRepository.deleteById(id));
    }
}

