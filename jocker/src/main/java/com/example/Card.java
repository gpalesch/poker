package com.example;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getNumericValue() {
        return switch (rank) {
            case "Ace" -> 14;
            case "King" -> 13;
            case "Queen" -> 12;
            case "Jack" -> 11;
            default -> Integer.parseInt(rank);
        };
    }
}