package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStartDTO {
    private String gameId;
    private String playerId;
    private String name;

    private List<CardDTO> playerCards;
    private List<CardDTO> dealerCards;
}
