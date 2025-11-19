package com.blackjack.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameStatusDTO {

    private String gameId;
    private String playerId;

    private List<CardDTO> playerCards;
    private int playerValue;

    private List<CardDTO> dealerCards;
    private int dealerValue;

    private boolean gameOver;
    private String winner;
}
