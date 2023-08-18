package ui;

import model.Account;
import model.Card;
import model.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



//Represents a black jack game
public class Game {
    private Account user;
    private Deck deck;
    private ArrayList<Card> userHand;
    private ArrayList<Card> dealerHand;


    //EFFECTS: Creates a BlackJack game with a given user, new deck, the user and dealer hands
    public Game(Account user, int wage) throws IOException {
        this.user = user;
        this.deck = new Deck();
        this.userHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        playGame(wage);
    }

    //MODIFIES: this
    //EFFECTS: removes a card from the deck and hands it to the user or dealer
    void dealInitialCards() {
        int cardIndexA = (int)(Math.random() * deck.deckSize());
        userHand.add(deck.dealCard(cardIndexA));
        int cardIndexB = (int)(Math.random() * (deck.deckSize()));
        userHand.add(deck.dealCard(cardIndexB));
        int cardIndexC = (int)(Math.random() * (deck.deckSize()));
        dealerHand.add(deck.dealCard(cardIndexC));
        int cardIndexD = (int)(Math.random() * (deck.deckSize()));
        dealerHand.add(deck.dealCard(cardIndexD));
    }

    //EFFECTS: gets the hand array and searches if the hand has an ace if so make sure
    // that if the hand value is over 21 then subtract 10
    int handValue(ArrayList<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.isAce()) {
                aceCount++;
            }
            value += card.getValue();
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    //EFFECTS: displays the hand of a user or dealer. If its a users hand display all cards.
    // If it's a dealers hand hide the first card.
    void printHand(ArrayList<Card> hand, boolean dealerHand) {
        System.out.println("Hand contains:");
        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && dealerHand) {
                System.out.println("[hidden]");
            } else {
                System.out.println(hand.get(i).getFace() + " (" + hand.get(i).getFace() + ")");
            }
        }
    }


    //EFFECTS: user plays game, if they want to hit send to hitAction. Return true.
    // If they select "stand" return false.
    boolean userTurn(int bet) {
        while (true) {
            System.out.println(user.getUsername() + "'s turn:");
            printHand(userHand, false);
            System.out.println("Dealer's hand:");
            printHand(dealerHand, true);
            System.out.println("Do you want to hit or stand?");
            Scanner scanner = new Scanner(System.in);
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                if (hitAction(bet)) {
                    return false;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return true;
    }

    //REQUIRES: user has selected "hit"
    //MODIFIES: this
    //EFFECTS: check if the hand value is == 21 or > 21, if it is return true.
    boolean hitAction(int bet) {
        int cardIndex = (int)(Math.random() * deck.deckSize());
        userHand.add(deck.dealCard(cardIndex));
        if (handValue((userHand)) == 21) {
            winAction(bet);
            return true;
        } else if (handValue(userHand) > 21) {
            loseAction(bet);
            return true;
        }
        return false;
    }



    //EFFECTS: displays the win screen
    public void winAction(int bet) {
        System.out.println("You win " + bet + "!");
        user.deposit(bet);
        user.addWin();
        System.out.println("Your balance: $" + user.getBalance());
    }


    //MODIFIES: this
    //EFFECTS: displays the lose screen. Add a loss and withdraw bet from account.
    public void loseAction(int bet) {
        System.out.println("You lose " + bet + "!");
        user.addLoss();
        user.withdraw(bet);
        System.out.println("Your balance: $" + user.getBalance());
    }


    //MODIFIES: this
    //EFFECTS: display the dealers hand. Keep hitting until the hand value < 17.
    // If the dealer busts user wins.
    public void dealerTurn(int bet) {
        System.out.println("Dealer's turn:");
        printHand(dealerHand, false);

        while (handValue(dealerHand) < 17) {
            System.out.println("Dealer hits");
            int cardIndex = (int)(Math.random() * deck.deckSize());
            dealerHand.add(deck.dealCard(cardIndex));
            printHand(dealerHand, false);
        }

        if (handValue(dealerHand) > 21) {
            winAction(bet);
        } else if (handValue(dealerHand) < handValue(userHand)) {
            winAction(bet);
        } else if (handValue(dealerHand) == handValue(userHand)) {
            System.out.println("It's a tie! You get your bet back.");
            user.addDraw();
            System.out.println("Your balance: $" + user.getBalance());
        } else if (handValue(dealerHand) > handValue(userHand)) {
            loseAction(bet);
        }
    }


    //EFFECTS: checks if bet > user balance. Then deal the player and
    // the dealer their cards and play the game.
    public void playGame(int bet) throws IOException {
        if (user.getBalance() >= bet) {
            userHand.clear();
            dealerHand.clear();
            dealInitialCards();
            if (userTurn(bet)) {
                dealerTurn(bet);
            }
            afterGameDisplayMenu();
        } else {
            System.out.println("Insufficient Funds");
            afterGameDisplayMenu();
        }
    }

    //EFFECTS: display menu after the game with the options:
    // Play again, check balance, main menu, exit.
    private void afterGameDisplayMenu() throws IOException {
        System.out.println("1. Play again");
        System.out.println("2. Check balance");
        System.out.println("3. Main Menu");
        System.out.println("4. Exit");
        Scanner screenInputScanner = new Scanner(System.in);
        int userInput = Integer.parseInt(screenInputScanner.nextLine());

        if (userInput == 1) {
            playGameAgain();
        } else if (userInput == 2) {
            displayBalance();
        } else if (userInput == 3) {
            new AccountScreen();
        } else if (userInput == 4) {
            System.exit(0);
        } else {
            System.out.println("Invalid");
            return;
        }
    }

    private Scanner scanner;

    //EFFECTS: Asks the user for the wage and plays the game.
    private void playGameAgain() throws IOException {
        System.out.println("How much do you want to wage?");
        Scanner scannerD = new Scanner(System.in);
        int wage = Integer.parseInt(scannerD.nextLine());
        playGame(wage);
    }

    //EFFECTS: Displays the balance and asks the user if they would like to deposit money.
    private void displayBalance() throws IOException {
        System.out.println("Current balance: " + user.getBalance());
        System.out.println("Would you like to deposit? (Y/N)");
        Scanner scanUserInput = new Scanner(System.in);
        String userInput = scanUserInput.nextLine();

        if (userInput.equals("Y")) {
            System.out.println("How much do you want to deposit?");
            Scanner scannerInput = new Scanner(System.in);
            int amount = Integer.parseInt(scannerInput.nextLine());
            user.deposit(amount);
            playGameAgain();
        } else if (userInput.equals("N")) {
            playGameAgain();
        } else {
            System.out.println("Invalid input");
            displayBalance();
        }
    }
}
