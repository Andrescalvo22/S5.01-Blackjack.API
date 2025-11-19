package com.blackjack.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionResponseDTO {
    private String message;
    private GameStatusDTO status;
}

