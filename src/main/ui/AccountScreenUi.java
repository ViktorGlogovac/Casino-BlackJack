package ui;

import model.Account;
import model.AccountsManager;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.awt.Color.*;

//Represents the main menu where all the account information is dealt with
public class AccountScreenUi extends JFrame implements ActionListener {
    private static final String grandCasino = "./data/grandCasino.json";
    private Account currentAccount;
    private static AccountsManager accountChecker = new AccountsManager("Grand Casino");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JComboBox<String> printCombo;

    //GUI components:
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    ImageIcon grandCasinoLogo = new ImageIcon("./Images/GrandCasinoLogo.jpg");
    Color gold = new Color(246, 229, 203);
    JFrame frame;
    JPanel panel;
    JButton createAccountButton;
    JButton loginButton;
    JLabel userLabel;
    JLabel passLabel;
    JTextField userText;
    JPasswordField  passwordText;
    JTextField userTextLi;
    JPasswordField  passwordTextLi;
    JButton loadButton;
    JLabel depositLabel;
    JTextField amount;
    JTextField wage;
    JLabel balanceLabel;
    JButton checkBalance;
    JLabel usernameLabel;


    //EFFECTS: constructs account manager and runs application
    public AccountScreenUi(Account user) throws IOException {
        this.currentAccount = user;
        jsonWriter = new JsonWriter(grandCasino);
        jsonReader = new JsonReader(grandCasino);
        displayMainMenu();
        printLogClosedScreen();
    }

    //EFFECTS: displays a main menu screen with a create or login account, check balance,
    // save and load options
    public void displayMainMenu() throws IOException {
        setFrame();

        String fileLocation = "./Images/AccountUiBackground.png";
        ImageIcon accountUiBackground = new ImageIcon(fileLocation);
        Image backGroundImage = accountUiBackground.getImage();

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backGroundImage, 0, 0,getWidth(), getHeight(), this);
            }
        };

        panelAndButtonsSetupDisplayMenu();


        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Grand Casino");

        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: calls to print the log to console on closed screen
    private void printLogClosedScreen() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                displayLogToConsole();
            }
        });
    }

    //EFFECTS: prints the log to console on closed screen
    private void displayLogToConsole() {
        String logText = generateLogText();
        System.out.println(logText);
    }

    //EFFECTS: creates the log text
    private String generateLogText() {
        EventLog eventLog = EventLog.getInstance();
        StringBuilder logText = new StringBuilder();

        for (Event next : eventLog) {
            logText.append(next.toString()).append("\n\n");
        }

        return logText.toString();
    }

    //EFFECTS: sets up the frame
    private void setFrame() {
        if (frame == null) {
            frame = new JFrame();
            frame.setSize(WIDTH, HEIGHT);
            frame.setMinimumSize(new Dimension(640, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        } else {
            frame.getContentPane().removeAll();
        }
    }

    //EFFECTS: sets up the title and creator name on front page
    void grandCasinoTitle() {
        JLabel titleLabel = new JLabel("Grand Casino");
        titleLabel.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        titleLabel.setForeground(gold);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(350, 80, 300, 70);
        panel.add(titleLabel);

        JLabel creator = new JLabel("Viktor Glogovac");
        creator.setFont(new Font("Brush Script MT", Font.BOLD, 20));
        creator.setForeground(gold);
        creator.setHorizontalAlignment(JLabel.CENTER);
        creator.setBounds(400, 115, 300, 70);
        panel.add(creator);
    }


    //EFFECTS: helper function for DisplayMenu which sets up the panel with labels and buttons
    void panelAndButtonsSetupDisplayMenu() {
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        grandCasinoTitle();

        panel.add(createAccountButton = new JButton("Create an account"));
        createAccountButton.setBounds(450,170,160,25);
        createAccountButton.setActionCommand("create an account");
        createAccountButton.addActionListener(this);

        panel.add(checkBalance = new JButton("Check Balance"));
        checkBalance.setBounds(450,230,160,25);
        checkBalance.setActionCommand("check balance");
        checkBalance.addActionListener(this);

        panel.add(loadButton = new JButton("Save"));
        loadButton.setBounds(450,260,160,25);
        loadButton.setActionCommand("save");
        loadButton.addActionListener(this);

        panel.add(loadButton = new JButton("Load"));
        loadButton.setBounds(450,290,160,25);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);


        addLoginLogoutButtons();
        addDisplayClearLogButtons();
    }

    //EFFECTS: Displays the display log and clear log buttons
    void addDisplayClearLogButtons() {
        panel.add(loginButton = new JButton("Display Log"));
        loginButton.setBounds(450,390,160,25);
        loginButton.setActionCommand("Display Log");
        loginButton.addActionListener(this);

        panel.add(loginButton = new JButton("Clear Log"));
        loginButton.setBounds(450,420,160,25);
        loginButton.setActionCommand("Clear Log");
        loginButton.addActionListener(this);
    }

    //EFFECTS: creates the login and log out buttons
    void addLoginLogoutButtons() {
        panel.add(loginButton = new JButton("Login"));
        loginButton.setBounds(450,200,160,25);
        loginButton.setActionCommand("log in");
        loginButton.addActionListener(this);

        if (currentAccount != null) {
            panel.add(loginButton = new JButton("Log Out"));
            loginButton.setBounds(450,320,160,25);
            loginButton.setActionCommand("log out");
            loginButton.addActionListener(this);
        }
    }

    //EFFECTS: Displays the GUI screen for the createAccount screen
    public void createAccount() throws IOException {
        panel.removeAll();
        panel.setLayout(null);

        userText = new JTextField(20);
        userText.setBounds(450,200,165, 25);
        panel.add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(450, 260, 165, 25);
        panel.add(passwordText);

        JButton accountCreated;
        panel.add(accountCreated = new JButton("Login"));
        accountCreated.setActionCommand("create account: Login");
        accountCreated.addActionListener(this);
        accountCreated.setBounds(445,300,120,25);

        JButton returnMainMenu;
        panel.add(returnMainMenu = new JButton("Main Menu"));
        returnMainMenu.setActionCommand("create Account: Main Menu");
        returnMainMenu.addActionListener(this);
        returnMainMenu.setBounds(445,330,120,25);

        createAccountLabelSetup();

        frame.revalidate();
        frame.repaint();
    }

    //EFFECTS: Helper function of createAccount which sets up the label gui components
    void createAccountLabelSetup() {
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(425, 110, 200, 30);
        panel.add(titleLabel);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(450,170,80,25);
        userLabel.setForeground(WHITE);
        panel.add(userLabel);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(450,230,80,25);
        passLabel.setForeground(WHITE);
        panel.add(passLabel);
    }


    //MODIFIES: this
    //EFFECTS: asks a user for a username and password and check is the account is already in the file.
    // If it is redirect the user to the login screen. If it is not in the file create and new account.
    public void createAccountUiBackground(String username, String password) throws IOException {
        currentAccount = new Account(username, password, 0, 0,0,0);

        if (accountChecker.validateAccount(currentAccount)) {
            logIn();
        } else {
            accountChecker.createAccount(currentAccount, true);
            checkBalance();
        }

        frame.revalidate();
        frame.repaint();
    }


    //EFFECTS: Checks is the given username and password are already in the account manager,
    // if they are go to successfulLogin else say that it is an invalid account and return
    // back to login screen
    public void logInUiBackground(String username, String password) throws IOException {
        Account oldAccount = new Account(username, password, 0, 0,0,0);

        if (accountChecker.validateAccount(oldAccount)) {
            currentAccount = accountChecker.getAccountSameUsername(username, password);
            successfulLogIn();
        } else {
            String message = "Invalid Account. Username or Password is wrong.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, grandCasinoLogo);
            logIn();
        }

        frame.revalidate();
        frame.repaint();
    }


    //EFFECTS: asks a user for a username and password and check is the account is already in the file.
    //If it is direct the user to the game. If it is not in the file redirect the user to the main menu.
    public void logIn() throws IOException {
        panel.removeAll();
        panel.setLayout(null);

        userTextLi = new JTextField(20);
        userTextLi.setBounds(450,200,165, 25);
        panel.add(userTextLi);

        passwordTextLi = new JPasswordField();
        passwordTextLi.setBounds(450, 260, 165, 25);
        panel.add(passwordTextLi);

        JButton accountLoggedIn;
        panel.add(accountLoggedIn = new JButton("Login"));
        accountLoggedIn.setActionCommand("login account: Login");
        accountLoggedIn.addActionListener(this);
        accountLoggedIn.setBounds(445,300,120,25);

        JButton returnMainMenu;
        panel.add(returnMainMenu = new JButton("Main Menu"));
        returnMainMenu.setActionCommand("log in: Main Menu");
        returnMainMenu.addActionListener(this);
        returnMainMenu.setBounds(445,330,120,25);

        loginLabelSetup();

        frame.revalidate();
        frame.repaint();
    }

    //EFFECTS: Sets up the gui components such as the labels and buttons for
    // the login screen
    void loginLabelSetup() {
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        titleLabel.setBounds(425, 110, 200, 30);
        panel.add(titleLabel);


        userLabel = new JLabel("Username:");
        userLabel.setForeground(WHITE);
        userLabel.setBounds(450,170,80,25);
        panel.add(userLabel);

        passLabel = new JLabel("Password:");
        passLabel.setForeground(WHITE);
        passLabel.setBounds(450,230,80,25);
        panel.add(passLabel);


    }



    //EFFECTS: displays the login was successful and sends the user into the game
    public void successfulLogIn() throws IOException {
        panel.removeAll();
        panel.setLayout(null);

        successfulLoginLabelSetup();
        successLoginHelper();

        JButton depositWage;
        panel.add(depositWage = new JButton("Wage"));
        depositWage.setActionCommand("Deposit: wage");
        depositWage.addActionListener(this);
        depositWage.setBounds(543,270,80,25);

        JButton returnMainMenu;
        panel.add(returnMainMenu = new JButton("Main Menu"));
        returnMainMenu.setActionCommand("create Account: Main Menu");
        returnMainMenu.addActionListener(this);
        returnMainMenu.setBounds(440,310,120,25);

        frame.revalidate();
        frame.repaint();
    }

    //EFFECTS: Helper method for the successfulLogin screen which sets up the label
    // components in the gui
    void successfulLoginLabelSetup() {
        JLabel titleLabel = new JLabel("Deposit Funds");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        titleLabel.setBounds(445, 110, 180, 30);
        panel.add(titleLabel);

        addUsernameBalanceLabel();
        panel.add(balanceLabel);
        panel.add(usernameLabel);

        depositLabel = new JLabel("Deposit: ");
        depositLabel.setForeground(WHITE);
        depositLabel.setBounds(445,180,WIDTH,25);
        panel.add(depositLabel);

        JLabel moneyLabelDeposit = new JLabel("$");
        moneyLabelDeposit.setForeground(WHITE);
        moneyLabelDeposit.setBounds(445,210,WIDTH,25);
        panel.add(moneyLabelDeposit);

        JLabel wageLabel = new JLabel("Wage:");
        wageLabel.setForeground(WHITE);
        wageLabel.setBounds(445,240,WIDTH,25);
        panel.add(wageLabel);
    }

    //EFFECTS: creates the username and balance label
    // at the top right of the screen
    void addUsernameBalanceLabel() {
        balanceLabel = new JLabel("Balance: $" + currentAccount.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        balanceLabel.setForeground(WHITE);
        balanceLabel.setBounds(500,40,200,25);

        usernameLabel = new JLabel(currentAccount.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        usernameLabel.setForeground(WHITE);
        usernameLabel.setBounds(500,20,125,25);
    }



    //EFFECTS: More helper methods for the gui components so that the method
    // satisfies the requirements
    void successLoginHelper() {
        JLabel moneyLabelWage = new JLabel("$");
        moneyLabelWage.setForeground(WHITE);
        moneyLabelWage.setBounds(445,270,WIDTH,25);
        panel.add(moneyLabelWage);

        amount = new JTextField(20);
        amount.setBounds(453,210,80, 25);
        panel.add(amount);

        JButton depositDeposit;
        panel.add(depositDeposit = new JButton("Deposit"));
        depositDeposit.setActionCommand("Deposit: deposit");
        depositDeposit.addActionListener(this);
        depositDeposit.setBounds(543,210,80,25);

        wage = new JTextField(20);
        wage.setBounds(453,270,80, 25);
        panel.add(wage);
    }

    //EFFECTS: checks if the user is logged in, if not sends to main menu. If they are displays
    //the balance and asks if they would like to deposit.
    public void checkBalance() throws IOException {
        panel.removeAll();
        panel.setLayout(null);

        checkBalanceGui();
        checkBalanceGuiHelper();

        frame.revalidate();
        frame.repaint();
    }

    //EFFECTS: Helper method for the check balance which creates components in the gui
    void checkBalanceGui() {
        JLabel titleLabel = new JLabel("Bank");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        titleLabel.setBounds(450, 110, 120, 30);
        panel.add(titleLabel);


        addUsernameBalanceLabel();
        panel.add(balanceLabel);
        panel.add(usernameLabel);
    }

    //EFFECTS: Helper method for the check balance which creates components in the gui
    void checkBalanceGuiHelper() {
        JLabel depositMoney = new JLabel("Would you like to deposit?");
        depositMoney.setForeground(WHITE);
        depositMoney.setBounds(445,180,WIDTH,25);
        panel.add(depositMoney);

        JButton createAccountDeposit;
        panel.add(createAccountDeposit = new JButton("Deposit"));
        createAccountDeposit.setActionCommand("create account: deposit");
        createAccountDeposit.addActionListener(this);
        createAccountDeposit.setBounds(445,210,80,25);

        JButton returnMainMenu;
        panel.add(returnMainMenu = new JButton("Main Menu"));
        returnMainMenu.setActionCommand("create Account: Main Menu");
        returnMainMenu.addActionListener(this);
        returnMainMenu.setBounds(445,240,120,25);
    }



    //EFFECTS: saves account manager to file
    public void saveAccountManager() {
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


    //EFFECTS: Displays the event log on a scrollable screen
    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:SuppressWarnings"})
    private void displayLog() {
        String logText = generateLogText();


        JTextArea textArea = new JTextArea(20, 50);
        textArea.setText(logText);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(AccountScreenUi.this, scrollPane, "Event Log", JOptionPane.INFORMATION_MESSAGE, grandCasinoLogo);
    }

    //EFFECTS: clears the log
    private void clearLog() {
        EventLog.getInstance().clear();
    }

    //EFFECTS: creates the route for each press of a button
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings", "checkstyle:LineLength"})
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            //EFFECTS: Sends user to create account screen unless currentAccount != null,
            // then sends error message
            case "create an account":
                try {
                    if (currentAccount != null) {
                        JOptionPane.showMessageDialog(null, "Must logout first", "Error", JOptionPane.ERROR_MESSAGE, grandCasinoLogo);
                    } else {
                        loadAccountManager();
                        createAccount();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            //EFFECTS: Sends user to log in screen unless currentAccount != null,
            // then sends error message
            case "log in":
                try {
                    if (currentAccount == null) {
                        loadAccountManager();
                        logIn();
                    } else {
                        checkBalance();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
                //EFFECTS: checks the username and password by going through the logInUiBackground
            case "login account: Login":
                try {
                    logInUiBackground(userTextLi.getText(), passwordTextLi.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "create account: Login":
                try {
                    createAccountUiBackground(userText.getText(), passwordText.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "create Account: Main Menu":
            case "log in: Main Menu":
                try {
                    saveAccountManager();
                    displayMainMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "load":
                loadAccountManager();
                try {
                    displayMainMenu();
                    JLabel success = new JLabel("Loaded Grand Casino");
                    success.setForeground(gold);
                    success.setBounds(460,340,300,25);
                    panel.add(success);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Deposit: deposit":
                int depositAmount = Integer.parseInt(amount.getText());
                currentAccount.deposit(depositAmount);
                //"$ " + currentAccount.getBalance());
                balanceLabel.setText("Balance: $" + currentAccount.getBalance());
                break;
            case "Deposit: wage":
                int wageAmount = Integer.parseInt(wage.getText());
                if (wageAmount <= currentAccount.getBalance() && wageAmount > 0) {
                    try {
                        frame.dispose();
                        balanceLabel.setText("Balance: $" + currentAccount.getBalance());
                        //System.out.println("$ " + currentAccount.getBalance());
                        GameUi game = new GameUi(currentAccount, wageAmount);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE, grandCasinoLogo);
                }
                break;
            case "save":
                try {
                    saveAccountManager();
                    displayMainMenu();
                    JLabel success = new JLabel("Saved Grand Casino");
                    success.setForeground(gold);
                    success.setBounds(460, 340, 300, 25);
                    panel.add(success);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "check balance":
                try {
                    if (currentAccount == null) {
                        JOptionPane.showMessageDialog(null, "Not logged in!", "Error", JOptionPane.ERROR_MESSAGE, grandCasinoLogo);
                        displayMainMenu();
                    } else {
                        loadAccountManager();
                        checkBalance();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "create account: deposit":
                try {
                    successfulLogIn();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "log out":
                currentAccount = null;
                try {
                    displayMainMenu();
                    JLabel success = new JLabel("Logged out");
                    success.setForeground(gold);
                    success.setBounds(460,315,300,25);
                    panel.add(success);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Display Log":
                displayLog();
                break;
            case "Clear Log":
                clearLog();
                break;
        }
    }
}
