package com.blackjack.controller;

import com.blackjack.dto.ActionResponseDTO;
import com.blackjack.dto.GameStartDTO;
import com.blackjack.dto.GameStartRequestDTO;
import com.blackjack.dto.GameStatusDTO;
import com.blackjack.mapper.DtoMapper;
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

    private final GameService gameService;
    private final PlayerService playerService;

    @PostMapping("/new")
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

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return gameService.deleteGame(id);
    }
}

