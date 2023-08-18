package persistence;

import model.Account;
import model.AccountsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountsManager am = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testEmptyCasino.json");
        try {
            AccountsManager wr = reader.read();
            assertEquals("Empty Casino", wr.getName());
            assertEquals(0, wr.numAccounts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMultipleAccounts() {
        JsonReader reader = new JsonReader("./data/testMultipleAccounts.json");
        try {
            AccountsManager accountsManager = reader.read();
            assertEquals("Casino", accountsManager.getName());

            List<Account> accounts = accountsManager.getAccountsManager();
            assertEquals(3, accounts.size());

            assertEquals("user1", accounts.get(0).getUsername());
            assertEquals("password1", accounts.get(0).getPassword());
            assertEquals(100, accounts.get(0).getBalance());

            assertEquals("user2", accounts.get(1).getUsername());
            assertEquals("password2", accounts.get(1).getPassword());
            assertEquals(50, accounts.get(1).getBalance());

            assertEquals("user3", accounts.get(2).getUsername());
            assertEquals("password3", accounts.get(2).getPassword());
            assertEquals(300, accounts.get(2).getBalance());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}
