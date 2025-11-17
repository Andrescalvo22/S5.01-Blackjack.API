package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ActionResponseDTO {
    private String message;
    private GameStatusDTO status;
}
