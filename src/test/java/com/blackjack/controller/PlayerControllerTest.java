package com.blackjack.controller;

import com.blackjack.exception.PlayerNotFoundException;
import com.blackjack.service.PlayerService;
import com.blackjack.sqlmodel.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class PlayerControllerTest {

    private WebTestClient webTestClient;

    private PlayerService playerService;

    private PlayerEntity player;

    @BeforeEach
    void setUp() {

        playerService = Mockito.mock(PlayerService.class);

        PlayerController controller = new PlayerController(playerService);

        webTestClient = WebTestClient.bindToController(controller).build();

        player = PlayerEntity.builder()
                .id(1L)
                .name("Andres")
                .score(10)
                .gamesPlayed(5)
                .wins(3)
                .losses(1)
                .ties(1)
                .build();
    }

    @Test
    void testCreatePlayer() {
        when(playerService.create(any(PlayerEntity.class))).thenReturn(player);

        webTestClient.post()
                .uri("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "name": "Andres"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Andres");
    }

    @Test
    void testGetPlayerById() {
        when(playerService.getById(1L)).thenReturn(player);

        webTestClient.get()
                .uri("/player/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Andres");
    }

    @Test
    void testGetPlayerById_NotFound() {
        when(playerService.getById(99L))
                .thenThrow(new PlayerNotFoundException("Player not found"));

        webTestClient.get()
                .uri("/player/99")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testGetAllPlayers() {
        when(playerService.getAll()).thenReturn(List.of(player));

        webTestClient.get()
                .uri("/player")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Andres");
    }

    @Test
    void testUpdatePlayer() {
        PlayerEntity updated = PlayerEntity.builder()
                .id(1L)
                .name("NewName")
                .score(10)
                .gamesPlayed(5)
                .wins(3)
                .losses(1)
                .ties(1)
                .build();

        when(playerService.update(eq(1L), any(PlayerEntity.class))).thenReturn(updated);

        webTestClient.put()
                .uri("/player/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "name": "NewName"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("NewName");
    }

    @Test
    void testDeletePlayer() {
        Mockito.doNothing().when(playerService).delete(1L);

        webTestClient.delete()
                .uri("/player/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testRanking() {
        when(playerService.getRanking()).thenReturn(List.of(player));

        webTestClient.get()
                .uri("/player/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Andres");
    }
}

