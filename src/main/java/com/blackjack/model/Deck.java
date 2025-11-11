package com.blackjack.model;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> cards;

    public void initializeDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }

    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.poll();
    }

    public int remainingCards() {
        return cards.size();
    }

    public Deck() {
        this.cards = new LinkedList<>();
        initializeDeck();
        shuffle();
    }
}
