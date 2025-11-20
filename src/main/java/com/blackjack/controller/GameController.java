package com.blackjack.controller;

import com.blackjack.dto.ActionResponseDTO;
import com.blackjack.dto.GameStartDTO;
import com.blackjack.dto.GameStatusDTO;
import com.blackjack.mapper.DtoMapper;
import com.blackjack.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/start/{playerId}")
    public Mono<GameStartDTO> startGame(@PathVariable String playerId) {
        return gameService.startGame(playerId)
                .map(DtoMapper::toGameStartDTO);
    }

    @GetMapping("/{id}")
    public Mono<GameStatusDTO> getGame(@PathVariable String id) {
        return gameService.getGame(id)
                .map(DtoMapper::toGameStatusDTO);
    }

    @PostMapping("/{id}/hit")
    public Mono<ActionResponseDTO> hit(@PathVariable String id) {
        return gameService.hit(id)
                .map(game -> DtoMapper.toActionResponseDTO("Card drawn", game));
    }

    @PostMapping("/{id}/stand")
    public Mono<ActionResponseDTO> stand(@PathVariable String id) {
        return gameService.stand(id)
                .map(game -> DtoMapper.toActionResponseDTO("Stand complete", game));
    }
}



