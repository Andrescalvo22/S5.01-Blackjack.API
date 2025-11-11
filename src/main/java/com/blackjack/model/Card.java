package com.blackjack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String id;
    private Suit suit;
    private Rank rank;

    public Card(Rank rank, Suit suit) {
        this.id = UUID.randomUUID().toString();
        this.rank = rank;
        this.suit = suit;
    }
}
