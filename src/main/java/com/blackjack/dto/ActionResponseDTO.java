package com.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter

public class ActionResponseDTO {
    private String message;
    private GameStatusDTO status;
}
