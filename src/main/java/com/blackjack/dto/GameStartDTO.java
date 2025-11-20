package com.blackjack.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameStartDTO {
    private String gameId;
    private String playerId;
    private String name;

    private List<CardDTO> playerCards;
    private List<CardDTO> dealerCards;
}
