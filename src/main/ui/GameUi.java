package ui;

import model.Account;
import model.Card;
import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

//Represents a blackjack game with a GUI implementation
public class GameUi extends JFrame implements ActionListener {
    private Account user;
    private Deck deck;
    private ArrayList<Card> userHand;
    private ArrayList<Card> dealerHand;
    private int wage;

    //GUI components
    JFrame frame;
    JDialog dialog;
    JButton playAgainButton;
    JButton checkBalanceButton;
    JButton mainMenu;
    JButton exit;
    JPanel bankPanel;
    JPanel userPanel;
    JPanel inputPanel;
    ImageIcon grandCasinoLogo;
    JTextField wager;
    JTextField deposit;
    JButton hit;
    JButton stand;
    ImageIcon card;



    //EFFECTS: Creates a BlackJack game with a given user, new deck, the user and dealer hands
    public GameUi(Account user, int wage) throws IOException {
        this.wage = wage;
        this.user = user;
        this.deck = new Deck();
        this.userHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        grandCasinoLogo = new ImageIcon("/Users/viktorglogovac/Documents/project_k4h2g/Images/GrandCasinoLogo.jpg");

        setDisplay(wage);

    }

    //EFFECTS: Creates a gui screen which splits the screen into three panels (top: dealer, middle: user,
    // bottom: user input). The dealer and user are empty but the input panel notifies the users balance
    // and their stats.
    void setDisplay(int bet) throws IOException {
        if (frame == null) {
            frame = new JFrame();
            frame.setSize(WIDTH, HEIGHT);
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } else {
            frame.getContentPane().removeAll();
        }

        setDisplayHelper(bet);

        try {
            playGame(bet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //EFFECTS: Helper method for the setDisplay method
    // which creates the gui components
    void setDisplayHelper(int bet) {
        this.deck = new Deck();
        this.wage = bet;

        JPanel gamePanel = new JPanel();
        inputPanel = new JPanel();

        FlowLayout inputPanelLayout = new FlowLayout();
        inputPanel.setLayout(inputPanelLayout);
        inputPanel.add(new JLabel("Balance: $" + user.getBalance()));
        inputPanel.add(new JLabel("Total Bet: $" + bet));
        inputPanel.add(new JLabel("Wins: " + user.getWins()));
        inputPanel.add(new JLabel("Loses: " + user.getLosses()));
        inputPanel.add(new JLabel("Draws: " + user.getDraws()));

        GridLayout gamePanelLay = new GridLayout(2,1);
        gamePanel.setLayout(gamePanelLay);
        this.bankPanel = new JPanel();
        bankPanel.setBorder(BorderFactory.createTitledBorder("Bank"));
        this.userPanel = new JPanel();
        userPanel.setBorder(BorderFactory.createTitledBorder(user.getUsername()));
        gamePanel.add(bankPanel);
        gamePanel.add(userPanel);

        frame.add(inputPanel,BorderLayout.SOUTH);
        frame.add(gamePanel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
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

    //EFFECTS: Displays the hand of a user on the user panel.
    void printHandUser(ArrayList<Card> hand) {
        userPanel.removeAll();

        for (int i = 0; i < hand.size(); i++) {
            int desiredWidth = 100;
            int desiredHeight = (int) (desiredWidth * 1.33);

            String cardFile = "/Users/viktorglogovac/Documents/project_k4h2g/CardsCollection/";
            card = new ImageIcon(cardFile + hand.get(i).getFace() + "_" + hand.get(i).getSuit() + ".png");
            Image originalImage = card.getImage();
            Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            userPanel.add(new JLabel(resizedIcon));


            frame.revalidate();
            frame.repaint();
        }
        userPanel.add(new JLabel("Hand Value: " + handValue(hand)));
    }

    //EFFECTS: Displays the Dealers hand on the bank panel. If hidden == true and its the first
    // card display one hidden card and the other card face up.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    void printHandDealer(ArrayList<Card> hand, boolean hidden) {
        bankPanel.removeAll();
        int desiredWidth = 100;
        int desiredHeight = (int) (desiredWidth * 1.33);

        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && hidden) {
                String cardFile = "/Users/viktorglogovac/Documents/project_k4h2g/CardsCollection/GrandCasino_back.png";
                card = new ImageIcon(cardFile);
                Image originalImage = card.getImage();
                Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                bankPanel.add(new JLabel(resizedIcon));
                frame.repaint();
            }
            if (hidden && i != 1) {
                String cardFile = "/Users/viktorglogovac/Documents/project_k4h2g/CardsCollection/";
                card = new ImageIcon(cardFile + hand.get(i).getFace() + "_" + hand.get(i).getSuit() + ".png");
                Image originalImage = card.getImage();
                Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                bankPanel.add(new JLabel(resizedIcon));
                frame.revalidate();
                frame.repaint();
            } else if (!hidden) {
                String cardFile = "/Users/viktorglogovac/Documents/project_k4h2g/CardsCollection/";
                card = new ImageIcon(cardFile + hand.get(i).getFace() + "_" + hand.get(i).getSuit() + ".png");
                Image originalImage = card.getImage();
                Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                bankPanel.add(new JLabel(resizedIcon));

                frame.revalidate();
                frame.repaint();
            }
        }
    }


    //EFFECTS: calls the print class for the user and dealer accordingly
    // and enables the hit and stand button.
    void userTurn() {
        printHandUser(userHand);
        printHandDealer(dealerHand, true);

        hit.setEnabled(true);
        stand.setEnabled(true);
    }

    //REQUIRES: user has selected "hit"
    //MODIFIES: this
    //EFFECTS: check if the hand value is == 21 or > 21, if it is return true.
    boolean hitActionUser(int bet) throws InterruptedException {
        int cardIndex = (int)(Math.random() * deck.deckSize());
        userHand.add(deck.dealCard(cardIndex));
        if (handValue((userHand)) == 21) {
            printHandUser(userHand);
            winAction(bet);
            return true;
        } else if (handValue(userHand) > 21) {
            printHandUser(userHand);
            loseAction(bet);
            return true;
        }
        userTurn();
        return false;
    }



    //EFFECTS: displays the win screen.
    public void winAction(int bet) {
        user.deposit(bet);
        user.addWin();

        String message = "You Win $" + bet + "!\n" + "Your balance: $" + user.getBalance();
        JOptionPane.showMessageDialog(null, message, "Winner!", JOptionPane.INFORMATION_MESSAGE, grandCasinoLogo);
    }


    //MODIFIES: this
    //EFFECTS: displays the loose screen. Add a loss and withdraw bet from account.
    public void loseAction(int bet) {
        user.addLoss();
        user.withdraw(bet);
        String message = "You Lost $" + bet + "!\n" + "Your balance: $" + user.getBalance();
        String warningTitle = "Better luck next time!";
        JOptionPane.showMessageDialog(null, message,warningTitle, JOptionPane.INFORMATION_MESSAGE, grandCasinoLogo);
    }


    //MODIFIES: this
    //EFFECTS: display the dealers hand. Keep hitting until the hand value < 17.
    // If the dealer busts user wins.
    public void dealerTurn(int bet) throws IOException {
        printHandDealer(dealerHand, false);

        while (handValue(dealerHand) < 17) {
            //System.out.println("Dealer hits");
            int cardIndex = (int)(Math.random() * deck.deckSize());
            dealerHand.add(deck.dealCard(cardIndex));
            printHandDealer(dealerHand, false);
        }

        if (handValue(dealerHand) > 21) {
            winAction(bet);
        } else if (handValue(dealerHand) < handValue(userHand)) {
            printHandDealer(dealerHand, false);
            winAction(bet);
        } else if (handValue(dealerHand) == handValue(userHand)) {
            String message = "Your balance: $" + user.getBalance();
            JOptionPane.showMessageDialog(null, message, "Tie", JOptionPane.INFORMATION_MESSAGE, grandCasinoLogo);
            user.addDraw();
        } else if (handValue(dealerHand) > handValue(userHand)) {
            loseAction(bet);
        }

        hit.setEnabled(false);
        stand.setEnabled(false);
        afterGameDisplayMenu();
    }


    //EFFECTS: checks if bet > user balance. Then deal the player and
    // the dealer their cards and play the game.
    public void playGame(int bet) {
        userPanel.removeAll();
        bankPanel.removeAll();
        if (user.getBalance() >= bet) {
            userHand.clear();
            dealerHand.clear();
            dealInitialCards();
            printHandUser(userHand);
            printHandDealer(dealerHand, true);

            inputPanel.add(hit = new JButton("Hit"));
            hit.setActionCommand("hit action");
            hit.addActionListener(this);

            inputPanel.add(stand = new JButton("Stand"));
            stand.setActionCommand("stand action");
            stand.addActionListener(this);

            userPanel.revalidate();
            userPanel.repaint();
            bankPanel.revalidate();
            bankPanel.repaint();
            inputPanel.revalidate();
            inputPanel.repaint();
        }
    }

    //EFFECTS: display menu after the game with the options:
    // Play again, check balance, main menu, exit.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void afterGameDisplayMenu() throws IOException {
        dialog = new JDialog(frame, "Game Over", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        panel.add(playAgainButton = new JButton("Play"));
        playAgainButton.setBounds(10,80,120,25);
        playAgainButton.setActionCommand("Play again");
        playAgainButton.addActionListener(this);

        panel.add(checkBalanceButton = new JButton("Check Balance"));
        checkBalanceButton.setBounds(10,80,120,25);
        checkBalanceButton.setActionCommand("Check Balance");
        checkBalanceButton.addActionListener(this);

        panel.add(mainMenu = new JButton("Main Menu"));
        mainMenu.setBounds(10,80,120,25);
        mainMenu.setActionCommand("Main Menu");
        mainMenu.addActionListener(this);

        panel.add(exit = new JButton("Exit"));
        exit.setBounds(10,80,120,25);
        exit.setActionCommand("Exit");
        exit.addActionListener(this);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }




    //EFFECTS: Asks the user for the wage and plays the game.
    private void playGameAgain() throws IOException {
        dialog = new JDialog(frame, "Casino Cage", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel wageLable = new JLabel("How much do you want to wage?");
        panel.add(wageLable);
        wager = new JTextField(20);
        wager.setBounds(10,80,80, 25);
        panel.add(wager);

        JButton wagerButton;
        panel.add(wagerButton = new JButton("Place Bet"));
        wagerButton.setBounds(10,130,120,25);
        wagerButton.setActionCommand("Place Bet");
        wagerButton.addActionListener(this);

        JButton backButton;
        panel.add(wagerButton = new JButton("Back"));
        wagerButton.setBounds(10,160,120,25);
        wagerButton.setActionCommand("Back");
        wagerButton.addActionListener(this);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    //EFFECTS: Displays the balance and asks the user if they would like to deposit money.
    private void displayBalance() throws IOException {
        dialog = new JDialog(frame, "Casino Cage", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        panel.add(new JLabel("Current Balance: $" + user.getBalance()));
        panel.add(new JLabel("Would you like to deposit?"));

        JButton yesButton;
        panel.add(yesButton = new JButton("Yes"));
        yesButton.setBounds(10,130,120,25);
        yesButton.setActionCommand("Yes Button");
        yesButton.addActionListener(this);

        JButton noButton;
        panel.add(noButton = new JButton("No"));
        noButton.setBounds(10,130,120,25);
        noButton.setActionCommand("No Button");
        noButton.addActionListener(this);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    //EFFECTS: Creates a screen for when the user click "yes" to wanting to
    // deposit money.
    void yesAction() {
        dialog = new JDialog(frame, "Casino Cage", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        panel.add(new JLabel("How much would you like to deposit? $"));
        deposit = new JTextField(20);
        deposit.setBounds(10,80,80, 25);
        panel.add(deposit);

        JButton placeBet;
        panel.add(placeBet = new JButton("Deposit"));
        placeBet.setBounds(10,130,120,25);
        placeBet.setActionCommand("deposit");
        placeBet.addActionListener(this);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }



    //EFFECTS: creates respective actions for each button pressed
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings", "checkstyle:LineLength"})
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Play again":
                try {
                    dialog.dispose();
                    playGameAgain();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Check Balance":
                try {
                    dialog.dispose();
                    displayBalance();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Main Menu":
                try {
                    dialog.dispose();
                    AccountScreenUi as = new AccountScreenUi(user);
                    as.saveAccountManager();
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Exit":
                try {
                    AccountScreenUi as = new AccountScreenUi(user);
                    as.saveAccountManager();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                System.exit(0);
                break;
            case "hit action":
                try {
                    if (hitActionUser(wage)) {
                        afterGameDisplayMenu();
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "stand action":
                try {
                    dealerTurn(wage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Place Bet":
                int wageAmount = Integer.parseInt(wager.getText());
                if (wageAmount > user.getBalance() || wageAmount < 0) {
                    String message = "Invalid bet amount. Your bet exceeds your balance of $";
                    JOptionPane.showMessageDialog(null,  message + user.getBalance(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dialog.dispose();
                try {
                    setDisplay(wageAmount);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Yes Button":
                dialog.dispose();
                yesAction();
                break;
            case "No Button":
                dialog.dispose();
                try {
                    playGameAgain();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "deposit":
                dialog.dispose();
                int depositAmount = Integer.parseInt(deposit.getText());
                user.deposit(depositAmount);

                try {
                    playGameAgain();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Back":
                try {
                    dialog.dispose();
                    afterGameDisplayMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;

        }
    }
}
