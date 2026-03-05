package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void cardRankAndSuit() {
        Card card = new Card("Ace", "Spades");
        assertThat(card.getRank()).isEqualTo("Ace");
        assertThat(card.getSuit()).isEqualTo("Spades");
    }
    
    @Test
    void cardNumericValue() {
        Card ace = new Card("Ace", "Hearts");
        Card ten = new Card("10", "Diamonds");

        assertThat(ace.getNumericValue()).isEqualTo(14);
        assertThat(ten.getNumericValue()).isEqualTo(10);
    }

    @Test
    void bestFiveCardsofSeven() {
        List<Card> availableCards = List.of(
            new Card("2", "Clubs"),
            new Card("10", "Hearts"),
            new Card("Ace", "Spades"),
            new Card("King", "Diamonds"),
            new Card("3", "Hearts"),
            new Card("Jack", "Clubs"),
            new Card("7", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.HIGH_CARD);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("Ace", "King", "Jack", "10", "7");
    }
}
