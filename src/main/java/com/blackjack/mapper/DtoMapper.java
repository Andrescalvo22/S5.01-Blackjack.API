package com.blackjack.mapper;

import com.blackjack.dto.*;
import com.blackjack.model.Card;
import com.blackjack.model.Game;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DtoMapper {

    public CardDTO toCardDTO(Card card) {
        return CardDTO.builder()
                .rank(card.getRank().name())
                .suit(card.getSuit().name())
                .build();
    }

    public List<CardDTO> toCardDTOList(List<Card> cards) {
        return cards.stream()
                .map(DtoMapper::toCardDTO)
                .collect(Collectors.toList());
    }

    public GameStartDTO toGameStartDTO(Game game) {

        List<CardDTO> dealerVisibleCard = List.of(
                toCardDTO(game.getDealerHand().getCards().get(0))
        );

        return GameStartDTO.builder()
                .gameId(game.getId())
                .playerId(game.getPlayerId())
                .playerCards(toCardDTOList(game.getPlayerHand().getCards()))
                .dealerCards(dealerVisibleCard)
                .build();
    }

    public GameStatusDTO toGameStatusDTO(Game game) {

        boolean revealDealerCards = game.isGameOver();

        List<CardDTO> dealerCards = revealDealerCards
                ? toCardDTOList(game.getDealerHand().getCards())
                : List.of(toCardDTO(game.getDealerHand().getCards().get(0)));

        int dealerValue = revealDealerCards
                ? game.getDealerHand().calculateValue()
                : 0;

        return GameStatusDTO.builder()
                .gameId(game.getId())
                .playerId(game.getPlayerId())

                .playerCards(toCardDTOList(game.getPlayerHand().getCards()))
                .playerValue(game.getPlayerHand().calculateValue())

                .dealerCards(dealerCards)
                .dealerValue(dealerValue)

                .gameOver(game.isGameOver())
                .winner(game.getWinner())
                .build();
    }

    public ActionResponseDTO toActionResponseDTO(String message, Game game) {
        return ActionResponseDTO.builder()
                .message(message)
                .status(toGameStatusDTO(game))
                .build();
    }
}
