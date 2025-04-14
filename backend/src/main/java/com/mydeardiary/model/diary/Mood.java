package com.mydeardiary.model.diary;

public enum Mood {
    VERY_SAD("😢"),
    SAD("🙁"),
    NEUTRAL("😐"),
    HAPPY("😊"),
    VERY_HAPPY("😁");

    private final String emoji;

    Mood(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}