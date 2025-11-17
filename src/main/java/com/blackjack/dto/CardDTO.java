package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDTO {
    private String rank;
    private String suit;
}
