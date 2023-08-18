package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents an accounts manager with a collection of accounts
public class AccountsManager implements Writable {
    private String name;
    private ArrayList<Account> accounts;

    public AccountsManager(String name) {
        this.accounts = new ArrayList<>();
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: Creates an account if username is not already taken return true, returns false otherwise
    public void createAccount(Account user, Boolean accountCreated) {
        accounts.add(user);
        if (accountCreated) {
            EventLog.getInstance().logEvent(new Event("User: " + user.getUsername() + " created."));
        }
    }

    //EFFECTS: checks is the username and password are the same as the database,
    // if they are then return true. Else return false.
    public boolean validateAccount(Account user) {
        for (Account account : accounts) {
            if (user.getUsername().equals(account.getUsername()) && user.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: validate account == true
    //EFFECTS: return the account with same username
    public Account getAccountSameUsername(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccountsManager() {
        return accounts;
    }

    //MODIFIES: file
    //EFFECTS: fills in the account manager information to into a file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns accounts in this accountsManager as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account account : accounts) {
            jsonArray.put(account.toJson());
        }

        return jsonArray;
    }

    public String getName() {
        return this.name;
    }

    public int numAccounts() {
        return this.accounts.size();
    }
}