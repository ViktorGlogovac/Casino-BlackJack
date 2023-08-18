package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    private Deck deck1;

    @BeforeEach
    public void runBefore() {
        deck1 = new Deck();
    }

    @Test
    public void testConstructor() {
        assertEquals(52,deck1.deckSize());
    }

    @Test
    public void testDealCard() {
        deck1.dealCard(0);
        assertEquals(51,deck1.deckSize());

        deck1.dealCard(48);
        deck1.dealCard(32);
        deck1.dealCard(5);
        assertEquals(48,deck1.deckSize());
    }

}
