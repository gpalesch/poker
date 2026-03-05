package com.example;

import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private Card card1;
    private Card card2;
    
    public Player(String name, Card card1, Card card2) {
        this.name = Objects.requireNonNull(name);
        this.card1 = Objects.requireNonNull(card1);
        this.card2 = Objects.requireNonNull(card2);
    }
    
    public String getName() {
        return name;
    }
    
    public List<Card> getHoleCards() {
        return List.of(card1, card2);
    }
    
    public HandResult getBestHand(List<Card> board) {
        List<Card> allCards = new java.util.ArrayList<>(getHoleCards());
        allCards.addAll(board);
        return HandEvaluator.evaluateBestHand(allCards);
    }
}
