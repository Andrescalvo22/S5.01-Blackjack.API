package com.blackjack.service;

import com.blackjack.exception.PlayerNotFoundException;
import com.blackjack.sqlmodel.PlayerEntity;
import com.blackjack.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerEntity create(PlayerEntity player) {
        player.setScore(0);
        player.setGamesPlayed(0);
        return repository.save(player);
    }

    public PlayerEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
    }

    public List<PlayerEntity> getAll() {
        return repository.findAll();
    }

    public PlayerEntity update(Long id, PlayerEntity data) {
        PlayerEntity player = repository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));

        player.setName(data.getName());
        return repository.save(player);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<PlayerEntity> getRanking() {

        return repository.findAll()
                .stream()
                .sorted((p1, p2) -> {

                    double rate1 = p1.getGamesPlayed() == 0 ? 0 :
                            (double) p1.getWins() / p1.getGamesPlayed();
                    double rate2 = p2.getGamesPlayed() == 0 ? 0 :
                            (double) p2.getWins() / p2.getGamesPlayed();

                    int compareRate = Double.compare(rate2, rate1);
                    if (compareRate != 0) return compareRate;

                    int compareWins = Integer.compare(p2.getWins(), p1.getWins());
                    if (compareWins != 0) return compareWins;

                    return Integer.compare(p1.getGamesPlayed(), p2.getGamesPlayed());
                })
                .toList();
    }

    public PlayerEntity save(PlayerEntity player) {
        return repository.save(player);
    }
}

