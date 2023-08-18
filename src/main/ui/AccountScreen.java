package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

//Represents the main menu where all the account information is dealt with
public class AccountScreen {
    private static final String grandCasino = "./data/grandCasino.json";
    private Scanner scanner;
    private Account currentAccount;
    private static AccountsManager accountChecker = new AccountsManager("Grand Casino");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: constructs account manager and runs application
    public AccountScreen() throws IOException {
        jsonWriter = new JsonWriter(grandCasino);
        jsonReader = new JsonReader(grandCasino);
        displayMainMenu();
    }

    //EFFECTS: displays a main menu screen with a create or login account, check balance,
    // save and load options
    public void displayMainMenu() throws IOException {
        System.out.println("1. Create an account");
        System.out.println("2. Log in");
        System.out.println("3. Check balance");
        System.out.println("4. Save");
        System.out.println("5. Load");

        Scanner screenInputScanner = new Scanner(System.in);
        int userInput = Integer.parseInt(screenInputScanner.nextLine());

        if (userInput == 1) {
            createAccount();
        } else if (userInput == 2) {
            logIn();
        } else if (userInput == 3) {
            checkBalance();
            displayMainMenu();
        } else if (userInput == 4) {
            saveAccountManager();
        } else if (userInput == 5) {
            loadAccountManager();
            displayMainMenu();
        } else {
            System.out.println("Invalid");
            displayMainMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: asks a user for a username and password and check is the account is already in the file.
    // If it is redirect the user to the login screen. If it is not in the file create and new account.
    public void createAccount() throws IOException {
        System.out.print("Enter username: ");
        Scanner scannerA = new Scanner(System.in);
        String username = scannerA.nextLine();
        System.out.print("Enter password: ");
        Scanner scannerB = new Scanner(System.in);
        String password = scannerB.nextLine();
        currentAccount = new Account(username, password, 0, 0,0,0);

        if (accountChecker.validateAccount(currentAccount)) {
            System.out.println("Account already exists.");
            logIn();
        } else {
            accountChecker.createAccount(currentAccount, true);
            System.out.println("Account created successfully.");
            checkBalance();
        }

    }


    //EFFECTS: asks a user for a username and password and check is the account is already in the file.
    //If it is direct the user to the game. If it is not in the file redirect the user to the main menu.
    public void logIn() throws IOException {
        System.out.print("Enter username: ");
        Scanner scannerA = new Scanner(System.in);
        String username = scannerA.nextLine();
        System.out.print("Enter password: ");
        Scanner scannerB = new Scanner(System.in);
        String password = scannerB.nextLine();

        Account oldAccount = new Account(username, password, 0, 0,0,0);

        // Assuming Account has a validate method to check credentials
        if (accountChecker.validateAccount(oldAccount)) {
            currentAccount = accountChecker.getAccountSameUsername(username, password);
            successfulLogIn();
        } else {
            System.out.println("Invalid username or password.");
            displayMainMenu();
        }
    }

    //EFFECTS: displays the login was successful and sends the user into the game
    public void successfulLogIn() throws IOException {
        System.out.println("Logged in successfully.");
        System.out.println("How much do you want to deposit?");
        Scanner scannerC = new Scanner(System.in);
        int amount = Integer.parseInt(scannerC.nextLine());
        currentAccount.deposit(amount);
        System.out.println("How much do you want to wage?");
        Scanner scannerD = new Scanner(System.in);
        int wage = Integer.parseInt(scannerD.nextLine());

        Game game = new Game(currentAccount, wage);
    }

    //EFFECTS: checks if the user is logged in, if not sends to main menu. If they are displays
    //the balance and asks if they would like to deposit.
    public void checkBalance() throws IOException {
        if (currentAccount == null) {
            System.out.println("Not logged in");
            displayMainMenu();
        }

        System.out.println("Current balance: " + currentAccount.getBalance());
        System.out.println("Would you like to deposit? (Y/N)");
        Scanner scanUserInput = new Scanner(System.in);
        String userInput = scanUserInput.nextLine();

        if (userInput.equals("Y")) {
            System.out.println("How much do you want to deposit?");
            Scanner despositInput = new Scanner(System.in);
            int amount = despositInput.nextInt();
            currentAccount.deposit(amount);
            displayMainMenu();
        } else if (userInput.equals("N")) {
            displayMainMenu();
        } else {
            System.out.println("Invalid input");
            checkBalance();
        }
    }


    //EFFECTS: saves account manager to file
    private void saveAccountManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(accountChecker);
            jsonWriter.close();
            System.out.println("Saved " + accountChecker.getName() + " to " + grandCasino);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + grandCasino);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads account manager from file
    private void loadAccountManager() {
        try {
            accountChecker = jsonReader.read();
            System.out.println("Loaded " + accountChecker.getName() + " from " + grandCasino);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + grandCasino);
        }
    }

}