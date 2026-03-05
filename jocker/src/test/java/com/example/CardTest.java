package com.example;

import static org.assertj.core.api.Assertions.assertThat;
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
}
