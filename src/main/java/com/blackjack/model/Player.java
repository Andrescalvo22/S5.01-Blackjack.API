package com.blackjack.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "players")
public class Player {

    @Id
    private String id;

    private String name;

    private int score;

    private int gamesPlayed;

    private Instant createdAt;
}

