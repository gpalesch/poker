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

    @Test
    void identifyOnePair() {
        List<Card> availableCards = List.of(
            new Card("2", "Clubs"),
            new Card("10", "Hearts"),
            new Card("Ace", "Spades"),
            new Card("King", "Diamonds"),
            new Card("3", "Hearts"),
            new Card("Jack", "Clubs"),
            new Card("10", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.ONE_PAIR);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("10", "10", "Ace", "King", "Jack");
    }

    @Test
    void identifyTwoPair() {
        List<Card> availableCards = List.of(
            new Card("8", "Clubs"),
            new Card("10", "Hearts"),
            new Card("Ace", "Diamonds"),
            new Card("10", "Spades"),
            new Card("King", "Clubs"),
            new Card("3", "Hearts"),
            new Card("8", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.TWO_PAIR);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("10", "10", "8", "8", "Ace");
    }

    @Test
    void identifyThreeOfAKind() {
        List<Card> availableCards = List.of(
            new Card("8", "Clubs"),
            new Card("8", "Spades"),
            new Card("8", "Hearts"),
            new Card("10", "Spades"),
            new Card("Ace", "Diamonds"),
            new Card("King", "Clubs"),
            new Card("3", "Hearts")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.THREE_OF_A_KIND);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("8", "8", "8", "Ace", "King");
    }

    @Test
    void identifyStraight() {
        List<Card> availableCards = List.of(
            new Card("5", "Clubs"),
            new Card("6", "Spades"),
            new Card("7", "Hearts"),
            new Card("8", "Spades"),
            new Card("9", "Diamonds"),
            new Card("King", "Clubs"),
            new Card("3", "Hearts")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("9", "8", "7", "6", "5");
    }

    @Test
    void identifyFlush() {
        List<Card> availableCards = List.of(
            new Card("2", "Hearts"),
            new Card("6", "Hearts"),
            new Card("7", "Hearts"),
            new Card("8", "Hearts"),
            new Card("9", "Hearts"),
            new Card("King", "Clubs"),
            new Card("3", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.FLUSH);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("9", "8", "7", "6", "2");
    }

    @Test
    void identifyFullHouse() {
        List<Card> availableCards = List.of(
            new Card("King", "Hearts"),
            new Card("King", "Spades"),
            new Card("King", "Diamonds"),
            new Card("Ace", "Spades"),
            new Card("Ace", "Clubs"),
            new Card("Queen", "Clubs"),
            new Card("3", "Hearts")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.FULL_HOUSE);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("King", "King", "King", "Ace", "Ace");
    }
}
