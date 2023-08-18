package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {
    private Card ace_diamonds;

    @BeforeEach
    public void runBefore(){
        ace_diamonds = new Card("Diamonds", "Ace", 11, true);
    }

    @Test
    public void testConstructor() {
        assertEquals("Diamonds",ace_diamonds.getSuit());
        assertEquals("Ace",ace_diamonds.getFace());
        assertEquals(11,ace_diamonds.getValue());
        assertTrue(ace_diamonds.isAce());
    }


}
