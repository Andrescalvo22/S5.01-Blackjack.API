package com.blackjack.controller;

import com.blackjack.dto.GameStartRequestDTO;
import com.blackjack.dto.PlayRequestDTO;
import com.blackjack.model.*;
import com.blackjack.service.GameService;
import com.blackjack.service.PlayerService;
import com.blackjack.sqlmodel.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameService gameService;
    private PlayerService playerService;
    private GameController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        playerService = mock(PlayerService.class);
        controller = new GameController(gameService, playerService);

        webTestClient = WebTestClient.bindToController(controller)
                .build();
    }

    private Game sampleGame() {
        return Game.builder()
                .id("game123")
                .playerId("10")
                .playerHand(new Hand(List.of(
                        new Card(Suit.HEARTH, Rank.ACE),
                        new Card(Suit.CLUBS, Rank.FOUR)
                )))
                .dealerHand(new Hand(List.of(
                        new Card(Suit.SPADES, Rank.SIX)
                )))
                .deck(List.of())
                .gameOver(false)
                .winner(null)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testGame_Found() {
        when(gameService.getGame("game123")).thenReturn(Mono.just(sampleGame()));

        webTestClient.get()
                .uri("/game/game123")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.gameId").isEqualTo("game123");

        verify(gameService).getGame("game123");
    }

    @Test
    void testGetGame_NotFound() {
        when(gameService.getGame("bad")).thenReturn(Mono.error(new RuntimeException("not found")));

        webTestClient.get()
                .uri("/game/bad")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(gameService).getGame("bad");
    }

    @Test
    void testCreatedGame() {
        GameStartRequestDTO request = new GameStartRequestDTO();
        request.setName("Andres");

        PlayerEntity savedPlayer = PlayerEntity.builder()
                .id(1L)
                .name("Andres")
                .score(0)
                .gamesPlayed(0)
                .wins(0)
                .losses(0)
                .ties(0)
                .build();

        when(playerService.create(any())).thenReturn(savedPlayer);
        when(gameService.startGame("1")).thenReturn(Mono.just(sampleGame()));

        webTestClient.post()
                .uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.gameId").isEqualTo("game123");

        verify(playerService).create(any());
        verify(gameService).startGame("1");
    }

    @Test
    void testPlay_Hit() {
        PlayRequestDTO request = new PlayRequestDTO();
        request.setMove("hit");

        when(gameService.hit("game123")).thenReturn(Mono.just(sampleGame()));

        webTestClient.post()
                .uri("/game/game123/play")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status.gameId").isEqualTo("game123");

        verify(gameService).hit("game123");
    }
}




