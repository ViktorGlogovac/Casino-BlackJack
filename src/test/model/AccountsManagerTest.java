package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AccountsManagerTest {
    private AccountsManager testAccountManager;
    private AccountsManager testAccountManager2;

    @BeforeEach
    public void runBefore(){
        testAccountManager = new AccountsManager("Casino 1");
        testAccountManager2 = new AccountsManager("Casino 2");
    }

    @Test
    public void testCreateAccount(){
        Account account1 = new Account("user1", "password1", 0, 0,0,0);
        testAccountManager.createAccount(account1, true);
        assertEquals(Arrays.asList(account1),testAccountManager.getAccountsManager());

        Account account2 = new Account("user2", "password2", 0, 0,0,0);
        testAccountManager.createAccount(account2, true);
        assertEquals(Arrays.asList(account1,account2),testAccountManager.getAccountsManager());
    }

    @Test
    public void testValidateAccount(){
        Account account1 = new Account("user1", "password1", 0, 0,0,0);
        testAccountManager2.createAccount(account1, true);

        Account account3 = new Account("user1", "password1", 0, 0,0,0);
        assertTrue(testAccountManager2.validateAccount(account3));
        assertEquals(account1, testAccountManager2.getAccountSameUsername("user1", "password1"));
        assertEquals(null, testAccountManager2.getAccountSameUsername("user1", "password"));
        assertEquals(null, testAccountManager2.getAccountSameUsername("user", "password1"));

        Account account4 = new Account("user1", "password2", 0, 0,0,0);
        assertFalse(testAccountManager2.validateAccount(account4));

        Account account5 = new Account("user5", "password5", 0, 0,0,0);
        assertFalse(testAccountManager2.validateAccount(account5));
        assertEquals(null, testAccountManager2.getAccountSameUsername("user5", "password5"));
    }

    @Test
    public void testGetAccountSameUsername() {
        Account account1 = new Account("user1", "password1", 0, 0,0,0);
        Account account3 = new Account("user3", "password3", 0, 0,0,0);
        Account account5 = new Account("user5", "password5", 0, 0,0,0);
        testAccountManager.createAccount(account1, true);
        testAccountManager.createAccount(account3, true);
        testAccountManager.createAccount(account5, true);

        assertEquals(account5, testAccountManager.getAccountSameUsername("user5", "password5"));
        assertEquals(null, testAccountManager.getAccountSameUsername("user2", "password2"));
        assertEquals(account3, testAccountManager.getAccountSameUsername("user3", "password3"));
    }

}