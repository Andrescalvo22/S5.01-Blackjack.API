package com.blackjack.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    private Suit suit;
    private Rank rank;
}
