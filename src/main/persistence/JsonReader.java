//REFERENCES: JsonSerializationDemo for saving/reading data
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Account;
import model.AccountsManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads account manager from JSON data stored in file
public class JsonReader {
    private String fileName;

    public JsonReader(String fileName) {
        this.fileName = fileName;
    }

    // EFFECTS: reads account manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountsManager read() throws IOException {
        String jsonData = readFile(fileName);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AccountsManager from JSON object and returns it
    private AccountsManager parseAccountManager(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AccountsManager wr = new AccountsManager(name);
        addAccounts(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to account manager
    private void addAccounts(AccountsManager wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(wr, nextAccount);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to account manager
    private void addAccount(AccountsManager wr, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        int wallet = jsonObject.getInt("wallet");
        int wins = jsonObject.getInt("wins");
        int draws = jsonObject.getInt("draws");
        int losses = jsonObject.getInt("losses");
        Account account = new Account(username, password, wallet, wins, draws, losses);
        wr.createAccount(account, false);
    }

}
