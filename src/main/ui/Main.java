package ui;

import model.Account;
import model.AccountsManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new AccountScreenUi(null);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

