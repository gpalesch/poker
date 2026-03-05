package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandEvaluator {
    public static HandResult evaluateBestHand(List<Card> availableCards) {
        List<Card> sortedCards = availableCards.stream()
            .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
            .collect(Collectors.toList());
        
        Map<String, List<Card>> cardsByRank = availableCards.stream()
            .collect(Collectors.groupingBy(Card::getRank));
        
        List<Card> pair = cardsByRank.values().stream()
            .filter(cards -> cards.size() == 2)
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        if (!pair.isEmpty()) {
            List<Card> remaining = sortedCards.stream()
                .filter(card -> !pair.contains(card))
                .limit(3)
                .collect(Collectors.toList());
            
            List<Card> chosen5 = pair.stream()
                .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
                .collect(Collectors.toList());
            chosen5.addAll(remaining);
            
            return new HandResult(HandCategory.ONE_PAIR, chosen5);
        }
        
        List<Card> chosen5 = sortedCards.stream().limit(5).collect(Collectors.toList());
        return new HandResult(HandCategory.HIGH_CARD, chosen5);
    }
}
