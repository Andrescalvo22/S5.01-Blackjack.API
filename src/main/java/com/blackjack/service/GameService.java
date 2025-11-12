package com.blackjack.service;

import com.blackjack.model.*;
import lombok.Getter;

@Getter
public class GameService {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean gameOver;

    public GameService() {
        startNewGame();
    }

    public void startNewGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        gameOver = false;

        playerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());

        dealerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    public void playerHit() {
        if (!gameOver) {
            playerHand.addCard(deck.draw());
            if (playerHand.calculateValue() > 21) {
                gameOver = true;
            }
        }
    }

    private void dealerTurn() {
        while (dealerHand.calculateValue() < 17) {
            dealerHand.addCard(deck.draw());
        }
    }

    public void playerStand() {
        if (!gameOver) {
            dealerTurn();
            gameOver = true;
        }
    }

    public String determineWinner() {
        int playerValue = playerHand.calculateValue();
        int dealerValue = dealerHand.calculateValue();

        if (playerValue > 21) return "Dealer Wins!";
        if (dealerValue > 21) return "Player Wins!";
        if (playerValue > dealerValue) return "Player Wins!";
        if (dealerValue > playerValue) return "Dealer Wins";
        return "Push (draw)";
    }
}


