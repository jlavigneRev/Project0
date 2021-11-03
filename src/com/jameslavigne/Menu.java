package com.jameslavigne;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

/**
 * @author James Lavigne
 * Class to handle the display and interaction of console menu
 */
public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    public static DecimalFormat df = new DecimalFormat("0.00");

    //console colors
    public static final String ANSI_NORMAL = "\033[0m";
    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private Menu() {
    }

    /**
     * Gets user input for menu selection
     *
     * @param max Number of choices listed in menu
     * @return number selected by user
     */
    public static int getMenuInput(int max) {
        while (true) {
            try {
                String inputString = scanner.nextLine();
                int input = Integer.parseInt(inputString);
                if (input >= 1 && input <= max) {
                    //valid number
                    return input;
                }
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }
            System.out.println(ANSI_RED + "Please, Enter a valid selection from (1-" + max + ")" + ANSI_NORMAL);
        }
    }

    public static String getUsernameInput() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        return username;
    }

    public static String getPasswordInput() {
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        return password;
    }

    public static String getNameInput() {
        System.out.print("Enter your name:");
        String name = scanner.nextLine();
        return name;

    }

    public static double getMoneyAmount(double max) {
        while (true) {
            try {
                String input = scanner.nextLine();
                double amount = Double.parseDouble(input);
                if (amount > 0 && amount <= max)
                    return amount;
            } catch (NumberFormatException e) {
            }
            System.out.println(ANSI_RED + "Please Enter a Valid Amount." + ANSI_NORMAL);
        }
    }

    public static int getAccIdInput() {
        System.out.print("Enter the Account ID:");
        while (true) {
            try {
                String input = scanner.nextLine();
                int id = Integer.parseInt(input);
                return id;
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }
            System.out.println(ANSI_RED + "Please enter a valid ID number." + ANSI_NORMAL);
        }
    }

    public static int getCustIdInput() {
        System.out.print("Enter the Customer ID:");
        while (true) {
            try {
                String input = scanner.nextLine();
                int id = Integer.parseInt(input);
                return id;
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }
            System.out.println(ANSI_RED + "Please enter a valid ID number." + ANSI_NORMAL);
        }
    }

    public static Customer getInputCustomer() {
        Customer customer = new Customer(0, getUsernameInput(), getPasswordInput(), getNameInput());
        return customer;
    }

    public static void printStartMenu() {
        System.out.println("*********************************************");
        System.out.println(ANSI_CYAN + "Hello, Welcome to the Bank Login Portal!" + ANSI_NORMAL);
        System.out.println("Please choose an option listed below (1-3):");
        System.out.println("Enter 1. Login");
        System.out.println("Enter 2. Create New User");
        System.out.println("Enter 3. Exit");
        System.out.println("********************************************");
    }

    public static void printLoginMenu() {
        System.out.println("********************************************");
        System.out.println("Please enter your login credentials below.");
    }

    public static void printCustomerMenu(String username, List<Account> accounts) {
        System.out.println("********************************************");
        System.out.println(ANSI_CYAN + "Welcome " + username + "!" + ANSI_NORMAL);
        int listSize = 0;
        if (accounts != null) {
            listSize = accounts.size();
        }
        System.out.println("Please choose an option listed below. (1-" + (listSize + 3) + "):");
        for (int i = 0; i < listSize; i++) {
            if (accounts.get(i).isApproved()) {
                System.out.println("Enter " + (i + 1) + ". View Account ID #" + accounts.get(i).getAccId() + " - $" + df.format(accounts.get(i).getBalance()));
            } else {
                System.out.println(ANSI_YELLOW + "Enter " + (i + 1) + ". Account ID #" + accounts.get(i).getAccId() + " is waiting for approval" + ANSI_NORMAL);
            }
        }
        System.out.println("Enter " + (listSize + 1) + ". Open New Bank Account");
        System.out.println("Enter " + (listSize + 2) + ". Logout");
        System.out.println("Enter " + (listSize + 3) + ". Exit");
    }

    public static void printAccountMenu(Account account) {
        System.out.println("********************************************");
        printAccountInfo(account);
        System.out.println("Enter 1. Deposit to account");
        System.out.println("Enter 2. Withdraw from account");
        System.out.println("Enter 3. Make a transfer to another account");
        System.out.println("Enter 4. Accept pending transfer");
        System.out.println("Enter 5. Back");
        System.out.println("Enter 6. Exit");
    }

    public static void printAccountInfo(Account account) {
        System.out.println("Getting your account info...");
        System.out.println(ANSI_CYAN + "--------------------------------");
        System.out.println("Account ID #" + account.getAccId());
        if (account.isApproved())
            System.out.println("Current Balance: $" + df.format(account.getBalance()));
        else
            System.out.println("Please contact an employee for the approval of this account.");
        System.out.println("--------------------------------" + ANSI_NORMAL);
    }

    //Employee view of account
    public static void viewAccount(Account account) {
        System.out.println(ANSI_CYAN + "Account ID #" + account.getAccId() + " - Owner ID #" + account.getCustId() +
                " - Balance: $" + account.getBalance() + " - Approved: " + account.isApproved() + ANSI_NORMAL);
    }

    public static void printDeposit() {
        System.out.print("Enter an amount to deposit: $");
    }

    public static void printWithdraw() {
        System.out.print("Enter an amount to withdraw: $");
    }

    public static void printCreateNewTransfer() {
        System.out.println("Creating a new Transfer Offer...");
    }

    public static void printPendingTransactions(List<Transaction> transactions) {
        System.out.println("******************************************");
        System.out.println("Getting Pending Transfer Requests...");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction tran = transactions.get(i);
            System.out.println("Enter " + (i + 1) + ". $" + tran.getAmount() + " from Account ID #" + tran.getSourceId());
        }
    }

    public static void printApprovedDenyChoice() {
        System.out.println("Enter 1. Approve Transaction");
        System.out.println("Enter 2. Deny Transaction");
    }

    public static void printEmployeeMenu(String username) {
        System.out.println("******************************************");
        System.out.println(ANSI_CYAN + "Welcome " + username + "!" + ANSI_NORMAL);
        System.out.println("Please choose an option listed below. (1-5)");
        System.out.println("Enter 1. View Accounts");
        System.out.println("Enter 2. Approve/Deny Accounts");
        System.out.println("Enter 3. View Transactions");
        System.out.println("Enter 4. Logout");
        System.out.println("Enter 5. Exit");
    }

    public static void printViewAccountMenu() {
        System.out.println("******************************************");
        System.out.println("Choose an Option Below. (1-3)");
        System.out.println("Enter 1. View Account by Account ID");
        System.out.println("Enter 2. View Accounts of Customer ID");
        System.out.println("Enter 3. Back");
    }

    public static void printApproveDenyMenu() {
        System.out.println("Choose to Approve or Deny an Account (1-2)");
        System.out.println("Enter 1. Approve an Account");
        System.out.println("Enter 2. Deny an Account");
    }

    public static void printApprovalMenu() {
        System.out.println("*****************************************");
        System.out.println("Use the options below to approve or deny accounts.");
        System.out.println("Choose an option (1-4)");
        System.out.println("Enter 1. View Pending Accounts");
        System.out.println("Enter 2. Approve/Deny Account By Account ID");
        System.out.println("Enter 3. Back");
        System.out.println("Enter 4. Exit");
    }

    public static void printPendingAccounts(List<Account> accounts) {
        System.out.println("*****************************************");
        if (accounts.size() == 0) {
            System.out.println("There Are No Accounts Currently Pending Approval.");
        } else {
            System.out.println("Viewing Current Accounts Awaiting Approval...");
            for (Account account : accounts) {
                System.out.println(ANSI_CYAN + "Account ID #" + account.getAccId() + " - Owned by Customer ID #" + account.getCustId() + " - Starting Balance of $" + account.getBalance() + ANSI_NORMAL);
            }
        }
    }

    public static void printTransactionLog(List<Transaction> transactionLog) {
        System.out.println(ANSI_CYAN + "-------------Transaction Log---------------------" + ANSI_NORMAL);
        for (Transaction transaction : transactionLog) {
            System.out.println(transaction);
        }
        System.out.println(ANSI_CYAN + "-------------------------------------------------" + ANSI_NORMAL);
    }

    public static void printInvalidLogin() {
        System.out.println(ANSI_RED + "Invalid login credentials" + ANSI_NORMAL);
    }

    public static void exit() {
        System.out.println(ANSI_CYAN + "Exiting...");
        System.out.println("Bye!" + ANSI_NORMAL);
        System.exit(0);
    }
}
