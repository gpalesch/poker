package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HandEvaluator {
    public static HandResult evaluateBestHand(List<Card> availableCards) {
        List<Card> sortedCards = availableCards.stream()
            .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
            .collect(Collectors.toList());
        
        List<Card> chosen5 = sortedCards.stream().limit(5).collect(Collectors.toList());
        return new HandResult(HandCategory.HIGH_CARD, chosen5);
    }
}
