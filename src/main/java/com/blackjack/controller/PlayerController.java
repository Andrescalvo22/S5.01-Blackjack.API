package com.blackjack.controller;

import com.blackjack.dto.UpdatePlayerNameDTO;
import com.blackjack.service.PlayerService;
import com.blackjack.sqlmodel.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerSQLService;

    @PostMapping
    public PlayerEntity createPlayer(@RequestBody PlayerEntity player) {
        return playerSQLService.create(player);
    }

    @GetMapping("/{id}")
    public PlayerEntity getPlayer(@PathVariable Long id) {
        return playerSQLService.getById(id);
    }

    @GetMapping
    public List<PlayerEntity> getAllPlayers() {
        return playerSQLService.getAll();
    }

    @PutMapping("/{id}")
    public PlayerEntity updatePlayer(@PathVariable("id") Long id, @RequestBody UpdatePlayerNameDTO request) {
        PlayerEntity entity = new PlayerEntity();
        entity.setName(request.getName());

        return playerSQLService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerSQLService.delete(id);
    }

    @GetMapping("/ranking")
    public List<PlayerEntity> getRanking() {
        return playerSQLService.getRanking();
    }
}


