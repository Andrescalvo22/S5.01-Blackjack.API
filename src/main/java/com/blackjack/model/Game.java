package com.blackjack.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private String playerId;

    private Hand playerHand;

    private Hand dealerHand;

    private List<Card> deck;

    private boolean gameOver;

    private String winner;

    private Instant createdAt;
}
