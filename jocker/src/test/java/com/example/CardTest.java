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

    @Test
    void identifyFourOfAKind() {
        List<Card> availableCards = List.of(
            new Card("Queen", "Hearts"),
            new Card("Queen", "Spades"),
            new Card("Queen", "Diamonds"),
            new Card("Queen", "Clubs"),
            new Card("Ace", "Spades"),
            new Card("King", "Clubs"),
            new Card("3", "Hearts")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.FOUR_OF_A_KIND);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("Queen", "Queen", "Queen", "Queen", "Ace");
    }

    @Test
    void identifyStraightFlush() {
        List<Card> availableCards = List.of(
            new Card("5", "Clubs"),
            new Card("6", "Clubs"),
            new Card("7", "Clubs"),
            new Card("8", "Clubs"),
            new Card("9", "Clubs"),
            new Card("King", "Hearts"),
            new Card("3", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.STRAIGHT_FLUSH);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("9", "8", "7", "6", "5");
    }

    @Test
    void identifyAceLowStraightWheel() {
        List<Card> availableCards = List.of(
            new Card("Ace", "Clubs"),
            new Card("2", "Diamonds"),
            new Card("3", "Hearts"),
            new Card("4", "Spades"),
            new Card("5", "Clubs"),
            new Card("King", "Hearts"),
            new Card("Queen", "Spades")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.STRAIGHT);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("5", "4", "3", "2", "Ace");
    }

    @Test
    void identifyBoardPlays() {
        List<Card> availableCards = List.of(
            new Card("Ace", "Hearts"),
            new Card("King", "Diamonds"),
            new Card("Queen", "Clubs"),
            new Card("Jack", "Spades"),
            new Card("10", "Hearts"),
            new Card("2", "Clubs"),
            new Card("3", "Diamonds")
        );

        HandResult result = HandEvaluator.evaluateBestHand(availableCards);
        assertThat(result.getCategory()).isEqualTo(HandCategory.HIGH_CARD);
        assertThat(result.getChosen5()).extracting(Card::getRank).containsExactly("Ace", "King", "Queen", "Jack", "10");
    }

    @Test
    void tieBreakTwoPairHigherPair() {
        List<Card> hand1 = List.of(
            new Card("King", "Hearts"),
            new Card("King", "Spades"),
            new Card("5", "Diamonds"),
            new Card("5", "Clubs"),
            new Card("Ace", "Hearts")
        );
        
        List<Card> hand2 = List.of(
            new Card("Queen", "Hearts"),
            new Card("Queen", "Spades"),
            new Card("5", "Diamonds"),
            new Card("5", "Clubs"),
            new Card("Ace", "Hearts")
        );
        
        HandResult result1 = new HandResult(HandCategory.TWO_PAIR, hand1);
        HandResult result2 = new HandResult(HandCategory.TWO_PAIR, hand2);
        
        int comparison = HandComparator.compareHands(result1, result2);
        assertThat(comparison).isGreaterThan(0);
    }

    @Test
    void tieBreakOnePairKickers() {
        List<Card> hand1 = List.of(
            new Card("10", "Hearts"),
            new Card("10", "Spades"),
            new Card("Ace", "Diamonds"),
            new Card("King", "Clubs"),
            new Card("Queen", "Hearts")
        );
        
        List<Card> hand2 = List.of(
            new Card("10", "Hearts"),
            new Card("10", "Diamonds"),
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Jack", "Clubs")
        );
        
        HandResult result1 = new HandResult(HandCategory.ONE_PAIR, hand1);
        HandResult result2 = new HandResult(HandCategory.ONE_PAIR, hand2);
        
        int comparison = HandComparator.compareHands(result1, result2);
        assertThat(comparison).isGreaterThan(0);
    }

    @Test
    void tieBreakSplit() {
        List<Card> hand1 = List.of(
            new Card("Ace", "Hearts"),
            new Card("King", "Spades"),
            new Card("Queen", "Diamonds"),
            new Card("Jack", "Clubs"),
            new Card("10", "Hearts")
        );
        
        List<Card> hand2 = List.of(
            new Card("Ace", "Diamonds"),
            new Card("King", "Clubs"),
            new Card("Queen", "Hearts"),
            new Card("Jack", "Spades"),
            new Card("10", "Diamonds")
        );
        
        HandResult result1 = new HandResult(HandCategory.HIGH_CARD, hand1);
        HandResult result2 = new HandResult(HandCategory.HIGH_CARD, hand2);
        
        boolean isSplit = HandComparator.isSplit(result1, result2);
        assertThat(isSplit).isTrue();
        
        int comparison = HandComparator.compareHands(result1, result2);
        assertThat(comparison).isEqualTo(0);
    }

    @Test
    void tieBreakDifferentCategories() {
        List<Card> hand1 = List.of(
            new Card("King", "Hearts"),
            new Card("King", "Spades"),
            new Card("King", "Diamonds"),
            new Card("Queen", "Clubs"),
            new Card("Jack", "Hearts")
        );
        
        List<Card> hand2 = List.of(
            new Card("10", "Hearts"),
            new Card("9", "Spades"),
            new Card("8", "Diamonds"),
            new Card("7", "Clubs"),
            new Card("6", "Hearts")
        );
        
        HandResult result1 = new HandResult(HandCategory.THREE_OF_A_KIND, hand1);
        HandResult result2 = new HandResult(HandCategory.STRAIGHT, hand2);
        
        int comparison = HandComparator.compareHands(result1, result2);
        assertThat(comparison).isGreaterThan(0);
    }

    @Test
    void findWinnerSinglePlayer() {
        List<Card> board = List.of(
            new Card("Ace", "Hearts"),
            new Card("King", "Diamonds"),
            new Card("Queen", "Clubs"),
            new Card("Jack", "Spades"),
            new Card("10", "Hearts")
        );
        
        Player player1 = new Player("Alice", new Card("9", "Clubs"), new Card("8", "Diamonds"));
        List<Player> players = List.of(player1);
        
        List<String> winners = PokerGame.findWinners(players, board);
        assertThat(winners).containsExactly("Alice");
    }

    @Test
    void findWinnerMultiplePlayers() {
        List<Card> board = List.of(
            new Card("King", "Hearts"),
            new Card("King", "Diamonds"),
            new Card("5", "Clubs"),
            new Card("3", "Spades"),
            new Card("2", "Hearts")
        );
        
        Player player1 = new Player("Alice", new Card("King", "Clubs"), new Card("10", "Diamonds"));
        Player player2 = new Player("Bob", new Card("Queen", "Clubs"), new Card("Jack", "Diamonds"));
        List<Player> players = List.of(player1, player2);
        
        List<String> winners = PokerGame.findWinners(players, board);
        assertThat(winners).containsExactly("Alice");
    }

    @Test
    void findWinnerSplit() {
        List<Card> board = List.of(
            new Card("Ace", "Hearts"),
            new Card("King", "Diamonds"),
            new Card("Queen", "Clubs"),
            new Card("Jack", "Spades"),
            new Card("10", "Hearts")
        );
        
        Player player1 = new Player("Alice", new Card("9", "Clubs"), new Card("8", "Diamonds"));
        Player player2 = new Player("Bob", new Card("2", "Clubs"), new Card("3", "Diamonds"));
        List<Player> players = List.of(player1, player2);
        
        List<String> winners = PokerGame.findWinners(players, board);
        assertThat(winners).containsExactly("Alice", "Bob");
    }
}
