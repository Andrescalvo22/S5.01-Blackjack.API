package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameStatusDTO {

    private List<CardDTO> playerCards;
    private int playerValue;

    private List<CardDTO> dealerCards;
    private int dealerValue;

    private boolean gameOver;
    private String message;

}
