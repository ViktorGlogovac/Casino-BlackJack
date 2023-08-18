package model;


import org.json.JSONObject;
import persistence.Writable;

//Represents an account having a username, password, amount of wins, losses, draws and the blane in a wallet
public class Account implements Writable {
    private String username;
    private String password;
    private int wins;
    private int losses;
    private int draws;
    private int wallet;

    //EFFECTS: Creates an account with a username, password, and wallet with a balance of 0
    public Account(String username, String password, int wallet, int wins, int draws, int losses) {
        this.username = username;
        this.password = password;
        this.wallet = wallet;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //REQUIRES: Amount > 0
    //MODIFIES: this
    //EFFECTS: deposits the value amount (in $) into the account wallet
    public void deposit(double amount) {
        this.wallet += amount;
        EventLog.getInstance().logEvent(new Event("$" + amount + " deposited."));
    }

    //REQUIRES: amount >= 0 and amount <= getBalance()
    // MODIFIES: this
    // EFFECTS: amount is withdrawn from account
    public void withdraw(int amount) {
        wallet = wallet - amount;
        EventLog.getInstance().logEvent(new Event("$" + amount + " withdrawn."));
    }

    public int getBalance() {
        return wallet;
    }

    //MODIFIES: this
    //EFFECTS: adds a win to wins
    public void addWin() {
        wins += 1;
        EventLog.getInstance().logEvent(new Event("Win added."));
    }

    //MODIFIES: this
    //EFFECTS: adds a loss to losses
    public void addLoss() {
        losses += 1;
        EventLog.getInstance().logEvent(new Event("Loss added."));
    }

    //MODIFIES: this
    //EFFECTS: adds a draw to draws
    public void addDraw() {
        draws += 1;
        EventLog.getInstance().logEvent(new Event("Draw added."));
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    //MODIFIES: this
    //EFFECTS: Takes the input amount and removes the bet from the wallet
    public void bet(int amount) {
        this.wallet -= amount;
        EventLog.getInstance().logEvent(new Event("Bet $" + amount + " placed."));
    }

    //MODIFIES: file
    //EFFECTS: fills in the account information to into a file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("wallet", wallet);
        json.put("wins", wins);
        json.put("draws", draws);
        json.put("losses", losses);
        return json;
    }

}
