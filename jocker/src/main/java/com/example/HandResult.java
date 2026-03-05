package com.example;

import java.util.List;

public class HandResult {
    private HandCategory category;
    private List<Card> chosen5;

    public HandResult(HandCategory category, List<Card> chosen5) {
        this.category = category;
        this.chosen5 = chosen5;
    }

    public HandCategory getCategory() {
        return category;
    }

    public List<Card> getChosen5() {
        return chosen5;
    }
}
