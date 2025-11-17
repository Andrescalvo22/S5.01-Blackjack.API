package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class GameStatusDTO {

    private List<CardDTO> playerCards;
    private int playerValue;

    private List<CardDTO> dealerCards;
    private int dealerValue;

    private boolean gameOver;

}
