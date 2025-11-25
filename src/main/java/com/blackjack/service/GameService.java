package com.blackjack.service;

import com.blackjack.exception.GameNotFoundException;
import com.blackjack.model.*;
import com.blackjack.repository.GameRepository;
import com.blackjack.sqlmodel.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;

    public Mono<Game> startGame(String playerId) {
        Deck deck = new Deck();
        List<Card> deckList = new ArrayList<>(deck.getCards());

        Hand playerHand = new Hand(new ArrayList<>());
        Hand dealerHand = new Hand(new ArrayList<>());

        playerHand.getCards().add(deckList.remove(0));
        playerHand.getCards().add(deckList.remove(0));

        dealerHand.getCards().add(deckList.remove(0));

        Game game = Game.builder()
                .playerId(playerId)
                .playerHand(playerHand)
                .dealerHand(dealerHand)
                .deck(deckList)
                .gameOver(false)
                .winner(null)
                .createdAt(Instant.now())
                .build();

        return gameRepository.save(game);
    }

    public Mono<Game> getGame(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)));
    }

    public Mono<Game> hit(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)))
                .flatMap(game -> {

                    if (game.isGameOver()) {
                        return Mono.error(new IllegalArgumentException("Game is already over"));
                    }

                    List<Card> deck = game.getDeck();
                    if (deck.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("No cards left in deck"));
                    }

                    game.getPlayerHand().getCards().add(deck.remove(0));

                    int value = game.getPlayerHand().calculateValue();

                    if (value > 21) {
                        game.setGameOver(true);
                        game.setWinner("DEALER");

                        updatePlayerStats(game.getPlayerId(), "DEALER");
                    }

                    return gameRepository.save(game);
                });
    }

    public Mono<Game> stand(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)))
                .flatMap(game -> {

                    if (game.isGameOver()) {
                        return Mono.error(new IllegalArgumentException("Game is already over"));
                    }

                    Hand dealer = game.getDealerHand();
                    List<Card> deck = game.getDeck();

                    while (dealer.calculateValue() < 17 && !deck.isEmpty()) {
                        dealer.getCards().add(deck.remove(0));
                    }

                    int playerValue = game.getPlayerHand().calculateValue();
                    int dealerValue = dealer.calculateValue();

                    String winner;

                    if (dealerValue > 21 || playerValue > dealerValue) {
                        winner = "PLAYER";
                    } else if (playerValue < dealerValue) {
                        winner = "DEALER";
                    } else {
                        winner = "PUSH";
                    }

                    game.setWinner(winner);
                    game.setGameOver(true);

                    updatePlayerStats(game.getPlayerId(), winner);

                    return gameRepository.save(game);
                });
    }

    public Mono<Void> deleteGame(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)))
                .flatMap(game -> gameRepository.deleteById(id));
    }

    private void updatePlayerStats(String playerId, String winner) {
        Long id = Long.parseLong(playerId);

        PlayerEntity player = playerService.getById(id);

        player.setGamesPlayed(player.getGamesPlayed() + 1);

        switch (winner) {
            case "PLAYER" -> player.setWins(player.getWins() + 1);
            case "DEALER" -> player.setLosses(player.getLosses() + 1);
            case "PUSH" -> player.setTies(player.getTies() + 1);
        }

        playerService.save(player);
    }
}



