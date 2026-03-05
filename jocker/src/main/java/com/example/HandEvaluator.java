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
        
        Map<String, List<Card>> cardsBySuit = availableCards.stream()
            .collect(Collectors.groupingBy(Card::getSuit));

        // Check for straight flush
        for (List<Card> suitCards : cardsBySuit.values()) {
            if (suitCards.size() >= 5) {
                List<Integer> suitValues = suitCards.stream()
                    .map(Card::getNumericValue)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
                
                for (int i = 0; i <= suitValues.size() - 5; i++) {
                    boolean isStraight = true;
                    for (int j = 0; j < 4; j++) {
                        if (suitValues.get(i + j + 1) != suitValues.get(i + j) + 1) {
                            isStraight = false;
                            break;
                        }
                    }
                    if (isStraight) {
                        final int straightIndex = i;
                        List<Card> straightFlush = suitCards.stream()
                            .filter(card -> suitValues.subList(straightIndex, straightIndex + 5).contains(card.getNumericValue()))
                            .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
                            .collect(Collectors.toList());
                        
                        return new HandResult(HandCategory.ROYAL_FLUSH, straightFlush);
                    }
                }
            }
        }

        // Check for four of a kind
        List<Card> fourOfAKind = cardsByRank.values().stream()
            .filter(cards -> cards.size() == 4)
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        if (!fourOfAKind.isEmpty()) {
            List<Card> remaining = sortedCards.stream()
                .filter(card -> !fourOfAKind.contains(card))
                .limit(1)
                .collect(Collectors.toList());
            
            List<Card> chosen5 = new java.util.ArrayList<>(fourOfAKind);
            chosen5.addAll(remaining);
            
            return new HandResult(HandCategory.FOUR_OF_A_KIND, chosen5);
        }

        // Check for full house
        List<Card> threeOfAKindForFullHouse = cardsByRank.values().stream()
            .filter(cards -> cards.size() == 3)
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        List<Card> pairForFullHouse = cardsByRank.values().stream()
            .filter(cards -> cards.size() >= 2 && !threeOfAKindForFullHouse.containsAll(cards))
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        if (!threeOfAKindForFullHouse.isEmpty() && !pairForFullHouse.isEmpty()) {
            List<Card> fullHouse = new java.util.ArrayList<>(threeOfAKindForFullHouse);
            fullHouse.addAll(pairForFullHouse.stream().limit(2).collect(Collectors.toList()));
            return new HandResult(HandCategory.FULL_HOUSE, fullHouse);
        }
    
        // Check for flush
        
        List<Card> flush = cardsBySuit.values().stream()
            .filter(cards -> cards.size() >= 5)
            .flatMap(List::stream)
            .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
            .limit(5)
            .collect(Collectors.toList());
        
        if (!flush.isEmpty()) {
            return new HandResult(HandCategory.FLUSH, flush);
        }

        // Check for straight
        List<Integer> uniqueValues = availableCards.stream()
            .map(Card::getNumericValue)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        
        List<Integer> wheelValues = List.of(2, 3, 4, 5, 14);
        boolean isWheel = uniqueValues.containsAll(wheelValues);
        if (isWheel) {
            List<Card> wheelCards = availableCards.stream()
                .filter(card -> wheelValues.contains(card.getNumericValue()))
                .sorted((c1, c2) -> {
                    if (c1.getNumericValue() == 14 && c2.getNumericValue() != 14) return 1;
                    if (c1.getNumericValue() != 14 && c2.getNumericValue() == 14) return -1;
                    return Integer.compare(c2.getNumericValue(), c1.getNumericValue());
                })
                .limit(5)
                .collect(Collectors.toList());
            
            return new HandResult(HandCategory.STRAIGHT, wheelCards);
        }
        
        for (int i = 0; i <= uniqueValues.size() - 5; i++) {
            boolean isStraight = true;
            for (int j = 0; j < 4; j++) {
                if (uniqueValues.get(i + j + 1) != uniqueValues.get(i + j) + 1) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) {
                final int straightIndex = i;
                List<Card> straightCards = availableCards.stream()
                    .filter(card -> uniqueValues.subList(straightIndex, straightIndex + 5).contains(card.getNumericValue()))
                    .sorted(Comparator.comparingInt(Card::getNumericValue).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
                
                return new HandResult(HandCategory.STRAIGHT, straightCards);
            }
        }
        
        // Check for three of a kind
        List<Card> threeOfAKind = cardsByRank.values().stream()
            .filter(cards -> cards.size() == 3)
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        if (!threeOfAKind.isEmpty()) {
            List<Card> remaining = sortedCards.stream()
                .filter(card -> !threeOfAKind.contains(card))
                .limit(2)
                .collect(Collectors.toList());
            
            List<Card> chosen5 = new java.util.ArrayList<>(threeOfAKind);
            chosen5.addAll(remaining);
            
            return new HandResult(HandCategory.THREE_OF_A_KIND, chosen5);
        }
        
        
        // Check for two pairs
        List<List<Card>> pairs = cardsByRank.values().stream()
            .filter(cards -> cards.size() == 2)
            .sorted((p1, p2) -> Integer.compare(p2.get(0).getNumericValue(), p1.get(0).getNumericValue()))
            .collect(Collectors.toList());
        
        if (pairs.size() >= 2) {
            List<Card> twoPairs = pairs.stream()
                .limit(2)
                .flatMap(List::stream)
                .collect(Collectors.toList());
            
            List<Card> remaining = sortedCards.stream()
                .filter(card -> !twoPairs.contains(card))
                .limit(1)
                .collect(Collectors.toList());
            
            List<Card> chosen5 = new java.util.ArrayList<>(twoPairs);
            chosen5.addAll(remaining);
            
            return new HandResult(HandCategory.TWO_PAIR, chosen5);
        }
        
        // Check for one pair
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
