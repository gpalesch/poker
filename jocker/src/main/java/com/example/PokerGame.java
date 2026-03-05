package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokerGame {
    
    public static List<String> findWinners(List<Player> players, List<Card> board) {
        if (players == null || players.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<PlayerWithHand> playerHands = players.stream()
            .map(p -> new PlayerWithHand(p.getName(), p.getBestHand(board)))
            .collect(Collectors.toList());
        
        PlayerWithHand bestHand = playerHands.get(0);
        
        for (int i = 1; i < playerHands.size(); i++) {
            int comparison = HandComparator.compareHands(playerHands.get(i).getHand(), bestHand.getHand());
            if (comparison > 0) {
                bestHand = playerHands.get(i);
            }
        }
        
        PlayerWithHand finalBestHand = bestHand;
        return playerHands.stream()
            .filter(ph -> HandComparator.compareHands(ph.getHand(), finalBestHand.getHand()) == 0)
            .map(PlayerWithHand::getName)
            .collect(Collectors.toList());
    }
    
    private static class PlayerWithHand {
        private String name;
        private HandResult hand;
        
        PlayerWithHand(String name, HandResult hand) {
            this.name = name;
            this.hand = hand;
        }
        
        String getName() {
            return name;
        }
        
        HandResult getHand() {
            return hand;
        }
    }
}
