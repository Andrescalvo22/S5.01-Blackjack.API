package com.blackjack.model;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
