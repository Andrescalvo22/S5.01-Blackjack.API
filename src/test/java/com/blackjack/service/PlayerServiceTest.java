package com.blackjack.service;

import com.blackjack.exception.PlayerNotFoundException;
import com.blackjack.sqlmodel.PlayerEntity;
import com.blackjack.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository repository;

    @InjectMocks
    private PlayerService service;
    private PlayerEntity player;

    @BeforeEach
    void setup() {
        player = PlayerEntity.builder()
                .id(1L)
                .name("Andres")
                .wins(2)
                .losses(1)
                .ties(0)
                .gamesPlayed(3)
                .score(0)
                .build();
    }

    @Test
    void testGetById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(player));

        PlayerEntity result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Andres", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> service.getById(1L));
        verify(repository).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(player)).thenReturn(player);

        PlayerEntity saved = service.save(player);

        assertEquals("Andres", saved.getName());
        verify(repository).save(player);
    }

    @Test
    void testRanking_OrderCorrect() {

        PlayerEntity p1 = PlayerEntity.builder()
                .id(1L)
                .name("A")
                .wins(3)
                .gamesPlayed(5)
                .build();

        PlayerEntity p2 = PlayerEntity.builder()
                .id(2L)
                .name("B")
                .wins(5)
                .gamesPlayed(10)
                .build();

        PlayerEntity p3 = PlayerEntity.builder()
                .id(3L)
                .name("C")
                .wins(1)
                .gamesPlayed(2)
                .build();

        List<PlayerEntity> list = Arrays.asList(p1, p2, p3);

        when(repository.findAll()).thenReturn(list);

        List<PlayerEntity> ranking = service.getRanking();

        assertEquals(1L, ranking.get(0).getId());
        assertEquals(2L, ranking.get(1).getId());
        assertEquals(3L, ranking.get(2).getId());

        verify(repository).findAll();
    }
}
