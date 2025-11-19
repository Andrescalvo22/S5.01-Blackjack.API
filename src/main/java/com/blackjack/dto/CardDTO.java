package com.blackjack.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {
    private String rank;
    private String suit;
}
