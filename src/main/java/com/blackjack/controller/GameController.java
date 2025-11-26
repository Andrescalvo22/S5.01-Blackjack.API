package com.blackjack.controller;

import com.blackjack.dto.*;
import com.blackjack.mapper.DtoMapper;
import com.blackjack.service.GameService;
import com.blackjack.service.PlayerService;
import com.blackjack.sqlmodel.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameStartDTO> newGame(@RequestBody GameStartRequestDTO request) {

        PlayerEntity newPlayer = PlayerEntity.builder()
                .name(request.getName())
                .score(0)
                .gamesPlayed(0)
                .build();

        PlayerEntity savedPlayer = playerService.create(newPlayer);

        return gameService.startGame(savedPlayer.getId().toString())
                .map(DtoMapper::toGameStartDTO);
    }

    @GetMapping("/{id}")
    public Mono<GameStatusDTO> getGame(@PathVariable("id") String id) {
        return gameService.getGame(id)
                .map(DtoMapper::toGameStatusDTO);
    }

    @PostMapping("/{id}/hit")
    public Mono<ActionResponseDTO> hit(@PathVariable("id") String id) {
        return gameService.hit(id)
                .map(game -> DtoMapper.toActionResponseDTO("Card drawn", game));
    }

    @PostMapping("/{id}/stand")
    public Mono<ActionResponseDTO> stand(@PathVariable("id") String id) {
        return gameService.stand(id)
                .map(game -> DtoMapper.toActionResponseDTO("Stand complete", game));
    }

    @DeleteMapping("/{id}/delete")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return gameService.deleteGame(id);
    }

    @PostMapping("/{id}/play")
    public Mono<ActionResponseDTO> play(@PathVariable("id") String id, @RequestBody PlayRequestDTO request) {
        String move = request.getMove().toLowerCase();

        return switch (move) {
            case "hit" -> gameService.hit(id)
                    .map(game -> DtoMapper.toActionResponseDTO("Card Drawn!", game));
            case "stand" -> gameService.stand(id)
                    .map(game -> DtoMapper.toActionResponseDTO("Stand Completed!", game));
            default -> Mono.error(new IllegalArgumentException("Invalid Move: " + move));
        };
    }
}


