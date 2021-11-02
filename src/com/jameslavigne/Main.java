package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Revature Project0 : Banking App
 */
public class Main {

    public static void main(String[] args) {
        EmployeeDao empDao = EmployeeDaoFactory.getEmployeeDao();
        CustomerDao custDao = CustomerDaoFactory.getCustomerDao();
        TransactionDao tranDao = TransactionDaoFactory.getTransactionDao();
        AccountDao accDao = AccountDaoFactory.getAccountDao();

        boolean browsingStart = true;
        while (browsingStart) {
            Menu.printStartMenu();

            Customer customer;
            Employee employee;
            String username;
            String password;
            String name;

            int currentUserId;

            int startChoice = Menu.getMenuInput(3);

            switch (startChoice) {
                case 1:
                    //Login
                    Menu.printLoginMenu();
                    username = Menu.getUsernameInput();
                    password = Menu.getPasswordInput();

// ******************************* Employee Menus **************************************
                    if (empDao.validEmployeeLogin(username, password)) {
                        //successful login as employee
                        Menu.printEmployeeMenu(username);
                        //get user choice
                        switch (Menu.getMenuInput(5)) {
                            case 1:
                                //view accounts
                                System.out.println("View Accounts");
                                break;
                            case 2:
                                //approve or deny accounts
                                boolean browsingAprroval = true;
                                while (browsingAprroval) {
                                    Menu.printApprovalMenu();
                                    //get approval action choice
                                    switch (Menu.getMenuInput(4)) {
                                        case 1:
                                            //View Pending Accounts
                                            List<Account> pendingAccounts = accDao.getPendingAccounts();
                                            Menu.printPendingAccounts(pendingAccounts);
                                            break;
                                        case 2:
                                            //Approve/Deny Account By ID
                                            Menu.printApproveDenyMenu();
                                            int approvChoice = Menu.getMenuInput(2);
                                            int pendingAccId = Menu.getIAccdInput();
                                            if (approvChoice == 1) {
                                                accDao.approveAccount(pendingAccId);
                                            } else {
                                                accDao.denyAccount(pendingAccId);
                                            }
                                            break;
                                        case 3:
                                            //Logout (back to login menu)
                                            browsingAprroval = false;
                                            break;
                                        case 4:
                                            //Exit App
                                            Menu.exit();
                                            break;
                                    }
                                }
                                break;
                            case 3:
                                //view transactions
                                System.out.println("View Transaction Log");
                                break;
                            case 4:
                                //go back to login screen
                                System.out.println("Logging out...");
                                break;
                            case 5:
                                //exit app
                                Menu.exit();
                        }
//******************************* Customer Menus *****************************************************
                    } else if (custDao.validCustomerLogin(username, password)) {
                        //successful login as customer
                        currentUserId = custDao.login(username);
                        boolean browsingCustMenu = true;
                        while(browsingCustMenu) {
                            //get users accounts
                            List<Account> accounts = accDao.getAccountsByCustId(currentUserId);
                            Menu.printCustomerMenu(username, accounts);
                            int numAccounts;
                            if (accounts != null) {
                                numAccounts = accounts.size();
                            } else {
                                numAccounts = 0;
                            }

                            //get user selection
                            int customerMenuChoice = Menu.getMenuInput(numAccounts + 3);
                            if (customerMenuChoice > 0 && customerMenuChoice <= numAccounts) {
                                //get account info of chosen account
                                Account currAccount = accounts.get(customerMenuChoice - 1);
                                int currAccId = currAccount.getAccId();
                                if(currAccount.isApproved()) {
                                    boolean browsingAccountMenu = true;
                                    while (browsingAccountMenu) {
                                        //bank account menu
                                        Menu.printAccountMenu(currAccount);
                                        int accMenuChoice = Menu.getMenuInput(6);
                                        switch (accMenuChoice) {
                                            case 1:
                                                //Deposit
                                                Menu.printDeposit();
                                                double depositAmount = Menu.getMoneyAmount();
                                                accDao.deposit( currAccId,depositAmount);
                                                browsingAccountMenu = false;
                                                break;
                                            case 2:
                                                //Withdraw
                                                Menu.printWithdraw();
                                                double withdraw = Menu.getMoneyAmount();
                                                browsingAccountMenu = false;
                                                break;
                                            case 3:
                                                //make transfer
                                                break;
                                            case 4:
                                                //accept pending transfer request
                                                break;
                                            case 5:
                                                //Back to previous menu
                                                browsingAccountMenu = false;
                                                break;
                                            case 6:
                                                //Exit
                                                Menu.exit();
                                                break;
                                        }

                                    }
                                } else {
                                    Menu.printAccountInfo(currAccount);
                                }
                            } else if (customerMenuChoice == numAccounts + 1) {
                                //Open new bank account
                                Account newAcc = new Account(0, currentUserId, 0, false);
                                accDao.addAccount(newAcc);
                            } else if (customerMenuChoice == numAccounts + 2) {
                                //Logout
                                browsingCustMenu = false;
                            } else if (customerMenuChoice == numAccounts + 3) {
                                //Exit
                                Menu.exit();
                            } else {
                                System.out.println("Something went wrong while viewing your account!");
                            }
                        }
//*****************************************************************************************************
                    } else {
                        //invalid login credentials
                        Menu.printInvalidLogin();
                    }
                    break;
                case 2:
                    //Create account
                    System.out.println("Create a new user account!");
                    customer = Menu.getInputCustomer();
                    custDao.addCustomer(customer);
                    break;

                case 3:
                    //Exit
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Enter a valid number");
            }
        }
    }
}
