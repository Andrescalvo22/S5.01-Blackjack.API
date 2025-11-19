package com.blackjack.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hand {

    private List<Card> cards = new ArrayList<>();

    public int calculateValue() {
        int value = 0;
        int aces = 0;

        for (Card card : cards) {
            value += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) aces++;
        }

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }
}

