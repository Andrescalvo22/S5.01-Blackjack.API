package com.blackjack.controller;

import com.blackjack.dto.*;
import com.blackjack.mapper.DtoMapper;
import com.blackjack.model.Game;
import com.blackjack.service.GameService;
import com.blackjack.service.PlayerService;
import com.blackjack.sqlmodel.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;       // Mongo
    private final PlayerService playerService;   // MySQL

    @PostMapping("/new")
    public Mono<GameStartDTO> newGame(@RequestBody GameStartDTO gameStartDTO) {

        PlayerEntity newPlayer = PlayerEntity.builder()
                .name(gameStartDTO.getName())
                .score(0)
                .gamesPlayed(0)
                .build();

        PlayerEntity savedPlayer = playerService.create(newPlayer);

        return gameService.startGame(savedPlayer.getId().toString())
                .map(DtoMapper::toGameStartDTO);
    }

    @GetMapping("/{id}")
    public Mono<GameStatusDTO> getGame(@PathVariable String id) {
        return gameService.getGame(id)
                .map(DtoMapper::toGameStatusDTO);
    }

    @PostMapping("/{id}/play")
    public Mono<ActionResponseDTO> play(
            @PathVariable String id,
            @RequestBody PlayRequestDTO request
    ) {
        String move = request.getMove().toLowerCase();

        return switch (move) {
            case "hit" -> gameService.hit(id)
                    .map(game -> DtoMapper.toActionResponseDTO("Card drawn", game));

            case "stand" -> gameService.stand(id)
                    .map(game -> DtoMapper.toActionResponseDTO("Stand complete", game));

            default -> Mono.error(new IllegalArgumentException("Invalid move"));
        };
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }
}


