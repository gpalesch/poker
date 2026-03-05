package com.example;

import java.util.List;

public class HandComparator {
    
    public static int compareHands(HandResult hand1, HandResult hand2) {
        int categoryComparison = hand2.getCategory().ordinal() - hand1.getCategory().ordinal();
        if (categoryComparison != 0) {
            return categoryComparison;
        }
        
        return compareByTieBreakRules(hand1, hand2);
    }
    
    private static int compareByTieBreakRules(HandResult hand1, HandResult hand2) {
        List<Card> cards1 = hand1.getChosen5();
        List<Card> cards2 = hand2.getChosen5();
        
        for (int i = 0; i < 5; i++) {
            int value1 = cards1.get(i).getNumericValue();
            int value2 = cards2.get(i).getNumericValue();
            
            if (value1 != value2) {
                return value1 - value2;
            }
        }
        
        return 0;
    }
    
    public static boolean isSplit(HandResult hand1, HandResult hand2) {
        return compareHands(hand1, hand2) == 0 && hand1.getCategory() == hand2.getCategory();
    }
}
