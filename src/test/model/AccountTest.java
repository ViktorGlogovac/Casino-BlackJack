package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    private Account testAccountA;
    private Account testAccountB;

    @BeforeEach
    public void runBefore(){
        testAccountA = new Account("Name A", "Password A", 0, 0,0,0);
        testAccountB = new Account("Name B", "Password B", 0, 0,0,0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Name A", testAccountA.getUsername());
        assertEquals("Password A", testAccountA.getPassword());
        assertEquals(0,testAccountA.getBalance());
    }


    @Test
    public void testDeposit () {
        testAccountA.deposit(100);
        assertEquals(100, testAccountA.getBalance());

        testAccountA.deposit(50);
        testAccountA.deposit(60);
        assertEquals(210, testAccountA.getBalance());
    }


    @Test
    public void testWithdraw() {
        testAccountA.deposit(100);
        testAccountA.withdraw(50);
        assertEquals(50, testAccountA.getBalance());

        testAccountA.deposit(50);
        testAccountA.deposit(100);
        testAccountA.withdraw(30);
        assertEquals(170, testAccountA.getBalance());
    }

    @Test
    public void testAddWin() {
        testAccountA.addWin();
        assertEquals(1, testAccountA.getWins());

        testAccountA.addWin();
        testAccountA.addWin();
        testAccountA.addWin();
        assertEquals(4, testAccountA.getWins());
    }

    @Test
    public void testAddLoss() {
        testAccountA.addLoss();
        assertEquals(1, testAccountA.getLosses());

        testAccountA.addLoss();
        testAccountA.addLoss();
        testAccountA.addLoss();
        assertEquals(4, testAccountA.getLosses());
    }

    @Test
    public void testAddDraws() {
        testAccountA.addDraw();
        assertEquals(1, testAccountA.getDraws());

        testAccountA.addDraw();
        testAccountA.addDraw();
        testAccountA.addDraw();
        assertEquals(4, testAccountA.getDraws());
    }

    @Test
    public void testMakeBet() {
        testAccountA.deposit(100);
        testAccountA.bet(20);
        assertEquals(80, testAccountA.getBalance());

        testAccountA.bet(20);
        assertEquals(60, testAccountA.getBalance());

        testAccountA.bet(80);
        assertEquals(-20, testAccountA.getBalance());
    }
}
