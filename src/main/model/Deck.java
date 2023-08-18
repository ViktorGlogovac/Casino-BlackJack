package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Represents a deck of 52 different cards
public class Deck {
    private List<Card> deck;

    //MODIFIES: this
    //Effects: creates a deck so that each suit has a value from 2 to Ace.
    // There is 52 cards in the deck.
    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:SuppressWarnings"})
    public Deck() {
        this.deck = new ArrayList<>();

        List<String> suits = Arrays.asList("Heart", "Diamond", "Club", "Spade");
        List<String> faces = Arrays.asList("Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace");
        List<Integer> values = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11);

        for (String suit : suits) {
            for (int i = 0; i < faces.size(); i++) {
                String face = faces.get(i);
                int value = values.get(i);
                boolean ace = face.equals("Ace");
                deck.add(new Card(suit, face, value, ace));
            }
        }
    }

    public int deckSize() {
        return deck.size();
    }

    //Requires: 0 <= index <= 51
    //Effects: Removes the card from the deck in the given index;
    public Card dealCard(int index) {
        return deck.remove(index);
    }
}