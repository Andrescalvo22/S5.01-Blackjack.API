package com.blackjack.service;

import com.blackjack.exception.GameNotFoundException;
import com.blackjack.model.*;
import com.blackjack.repository.GameRepository;
import com.blackjack.sqlmodel.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;



import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    private Game game;

    @BeforeEach
    void setup() {
        List<Card> deck = new ArrayList<>();
        deck.add(new Card(Suit.HEARTH, Rank.TEN));
        deck.add(new Card(Suit.SPADES, Rank.TWO));

        game = Game.builder()
                .id("game123")
                .playerId("10")
                .playerHand(new Hand(new ArrayList<>(List.of(
                        new Card(Suit.CLUBS, Rank.FIVE),
                        new Card(Suit.DIAMONDS, Rank.SIX)))))
                .dealerHand(new Hand(new ArrayList<>(List.of(new Card(Suit.SPADES, Rank.FOUR)))))
                .deck(deck)
                .gameOver(false)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testGetGameFound() {
        when(gameRepository.findById("game123")).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.getGame("game123"))
                .expectNext(game)
                .verifyComplete();
    }

    @Test
    void testGameNotFound() {
        when(gameRepository.findById("bad"))
                .thenReturn(Mono.empty());

        StepVerifier.create(gameService.getGame("bad"))
                .expectError(GameNotFoundException.class)
                .verify();
    }


    @Test
    void testStartGame() {
        when(gameRepository.save(any())).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.startGame("10"))
                .expectNextMatches(g ->
                                g.getPlayerHand().getCards().size() == 2 &&
                                g.getDealerHand().getCards().size() == 1)
                .verifyComplete();
    }

    @Test
    void testHitAddsCard() {
        Game game = this.game;

        game.getDeck().add(new Card(Suit.SPADES, Rank.KING));

        int before = game.getPlayerHand().getCards().size();

        when(gameRepository.findById("game123"))
                .thenReturn(Mono.just(game));

        when(gameRepository.save(any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(gameService.hit("game123"))
                .assertNext(g ->
                        assertEquals(before + 1, g.getPlayerHand().getCards().size())
                )
                .verifyComplete();
    }


    @Test
    void testHitGameNotFound() {
        when(gameRepository.findById("bad")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.hit("bad"))
                .expectError(GameNotFoundException.class)
                .verify();
    }

    @Test
    void testStandDealerDraws() {
        when(gameRepository.findById("game123")).thenReturn(Mono.just(game));
        when(gameRepository.save(any())).thenReturn(Mono.just(game));

        PlayerEntity player = PlayerEntity.builder()
                .id(10L)
                .name("Andres")
                .gamesPlayed(0)
                .wins(0)
                .losses(0)
                .ties(0)
                .score(0)
                .build();

        when(playerService.getById(10L)).thenReturn(player);
        when(playerService.save(any())).thenReturn(player);

        StepVerifier.create(gameService.stand("game123"))
                .expectNextMatches(g ->
                        g.isGameOver()
                )
                .verifyComplete();
    }

    @Test
    void testDeleteGame() {
        when(gameRepository.findById("game123")).thenReturn(Mono.just(game));
        when(gameRepository.deleteById("game123")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame("game123"))
                .verifyComplete();
    }

    @Test
    void testDeleteGameNotFound() {
        when(gameRepository.findById("bad")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame("bad"))
                .expectError(GameNotFoundException.class)
                .verify();
    }
}
