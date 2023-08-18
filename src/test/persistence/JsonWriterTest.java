package persistence;

import model.Account;
import model.AccountsManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountsManager wr = new AccountsManager("Casino");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            AccountsManager am = new AccountsManager("Casino");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCasino.json");
            writer.open();
            writer.write(am);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCasino.json");
            am = reader.read();
            assertEquals("Casino", am.getName());
            assertEquals(0, am.numAccounts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



    @Test
    void testWriterGeneralAccountsManager() {
        try {
            AccountsManager am = new AccountsManager("My Casino");
            am.createAccount(new Account("user1", "password1", 1, 1,2,3), false);
            am.createAccount(new Account("user2", "password2", 2, 4,5,6), false);
            am.createAccount(new Account("user3", "password3", 3, 7,8,9), false);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountsManager.json");
            writer.open();
            writer.write(am);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountsManager.json");
            am = reader.read();
            assertEquals("My Casino", am.getName());
            List<Account> accounts = am.getAccountsManager();
            assertEquals(3, accounts.size());

            assertEquals("user1", accounts.get(0).getUsername());
            assertEquals("password1", accounts.get(0).getPassword());
            assertEquals(1, accounts.get(0).getBalance());
            assertEquals(1, accounts.get(0).getWins());
            assertEquals(2, accounts.get(0).getDraws());
            assertEquals(3, accounts.get(0).getLosses());
            accounts.get(0).deposit(5);
            assertEquals(6, accounts.get(0).getBalance());

            assertEquals("user2", accounts.get(1).getUsername());
            assertEquals("password2", accounts.get(1).getPassword());
            assertEquals(2, accounts.get(1).getBalance());
            assertEquals(4, accounts.get(1).getWins());
            assertEquals(5, accounts.get(1).getDraws());
            assertEquals(6, accounts.get(1).getLosses());

            assertEquals("user3", accounts.get(2).getUsername());
            assertEquals("password3", accounts.get(2).getPassword());
            assertEquals(3, accounts.get(2).getBalance());
            assertEquals(7, accounts.get(2).getWins());
            assertEquals(8, accounts.get(2).getDraws());
            assertEquals(9, accounts.get(2).getLosses());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
