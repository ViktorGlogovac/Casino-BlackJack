package model;

// Represents a card with a suit, value, face, and it's an ace
public class Card {
    private String suit;
    private int value;
    private String face;
    private boolean ace;

    //EFFECTS: creates a card with the given suit, face, card value and store whether it is an ace
    public Card(String suit, String face, int value, boolean ace) {
        this.suit = suit;
        this.face = face;
        this.value = value;
        this.ace = ace;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getFace() {
        return face;
    }

    public boolean isAce() {
        return ace;
    }
}
