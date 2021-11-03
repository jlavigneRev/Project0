package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Revature Project0 : Banking App
 */
public class Main {

    public static void main(String[] args) {
        final double MAX_MONEY_TRANSACTION = 100000;

        EmployeeDao empDao = EmployeeDaoFactory.getEmployeeDao();
        CustomerDao custDao = CustomerDaoFactory.getCustomerDao();
        TransactionDao tranDao = TransactionDaoFactory.getTransactionDao();
        AccountDao accDao = AccountDaoFactory.getAccountDao();

        while (true) {
            Menu.printStartMenu();

            Customer customer;
            String username;
            String password;

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
                        boolean browsingEmployeeMenu = true;
                        while (browsingEmployeeMenu) {
                            //successful login as employee
                            Menu.printEmployeeMenu(username);
                            //get user choice
                            switch (Menu.getMenuInput(5)) {
                                case 1:
                                    //view accounts
                                    Menu.printViewAccountMenu();
                                    switch (Menu.getMenuInput(3)) {
                                        case 1:
                                            //View account by account id
                                            int viewAccId = Menu.getAccIdInput();
                                            Account viewableAccount = accDao.getAccountById(viewAccId);
                                            Menu.viewAccount(viewableAccount);
                                            break;
                                        case 2:
                                            //View accounts owned by user id
                                            int viewCustId = Menu.getAccIdInput();
                                            List<Account> viewableAccounts = accDao.getAccountsByCustId(viewCustId);
                                            for (Account account : viewableAccounts) {
                                                Menu.viewAccount(account);
                                            }
                                            break;
                                        case 3:
                                            //go back
                                            break;
                                    }
                                    break;
                                case 2:
                                    //approve or deny accounts
                                    boolean browsingApproval = true;
                                    while (browsingApproval) {
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
                                                int pendingAccId = Menu.getAccIdInput();
                                                if (approvChoice == 1) {
                                                    accDao.approveAccount(pendingAccId);
                                                } else {
                                                    accDao.denyAccount(pendingAccId);
                                                }
                                                break;
                                            case 3:
                                                //Back (back to employee menu)
                                                browsingApproval = false;
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
                                    List<Transaction> transactionLog = tranDao.getAllTransaction();
                                    Menu.printTransactionLog(transactionLog);
                                    break;
                                case 4:
                                    //go back to login screen
                                    System.out.println("Logging out...");
                                    browsingEmployeeMenu = false;
                                    break;
                                case 5:
                                    //exit app
                                    Menu.exit();
                            }
                        }
//******************************* Customer Menus *****************************************************
                    } else if (custDao.validCustomerLogin(username, password)) {
                        //successful login as customer
                        currentUserId = custDao.login(username);
                        boolean browsingCustMenu = true;
                        while (browsingCustMenu) {
                            //get users accounts
                            List<Account> accounts = accDao.getAccountsByCustId(currentUserId);
                            Menu.printCustomerMenu(username, accounts);
                            int numAccounts;
                            if (accounts != null) {
                                numAccounts = accounts.size();
                            } else {
                                numAccounts = 0;
                            }

                            //get user selection (dynamic sized menu)
                            int customerMenuChoice = Menu.getMenuInput(numAccounts + 3);
                            if (customerMenuChoice > 0 && customerMenuChoice <= numAccounts) {
                                //get account info of chosen account
                                Account currAccount = accounts.get(customerMenuChoice - 1);
                                int currAccId = currAccount.getAccId();
                                if (currAccount.isApproved()) {
                                    boolean browsingAccountMenu = true;
                                    while (browsingAccountMenu) {
                                        //bank account menu
                                        Menu.printAccountMenu(currAccount);
                                        int accMenuChoice = Menu.getMenuInput(6);
                                        switch (accMenuChoice) {
                                            case 1:
                                                //Deposit
                                                Menu.printDeposit();
                                                double depositAmount = Menu.getMoneyAmount(MAX_MONEY_TRANSACTION);
                                                if (accDao.deposit(currAccId, depositAmount)) {
                                                    //record transaction
                                                    Transaction transaction = new Transaction();
                                                    transaction.setSourceId(currAccId); //self deposit source
                                                    transaction.setDestId(currAccId);
                                                    transaction.setAmount(depositAmount);
                                                    transaction.setPending(false);
                                                    tranDao.addTransaction(transaction);
                                                }
                                                browsingAccountMenu = false;
                                                break;
                                            case 2:
                                                //Withdraw
                                                Menu.printWithdraw();
                                                double withdrawAmount = Menu.getMoneyAmount(currAccount.getBalance());
                                                if (accDao.withdraw(currAccId, withdrawAmount)) {
                                                    //record transaction
                                                    Transaction transaction = new Transaction();
                                                    transaction.setSourceId(currAccId); //self withdraw source
                                                    transaction.setDestId(currAccId);
                                                    transaction.setAmount(-withdrawAmount);
                                                    transaction.setPending(false);
                                                    tranDao.addTransaction(transaction);
                                                }
                                                browsingAccountMenu = false;
                                                break;
                                            case 3:
                                                //make transfer to another account
                                                Menu.printCreateNewTransfer();
                                                int transferDestId = Menu.getAccIdInput();
                                                System.out.print("Enter an amount: $");
                                                double transferAmount = Menu.getMoneyAmount(MAX_MONEY_TRANSACTION);
                                                if(transferAmount > currAccount.getBalance()){
                                                    System.out.println(Menu.ANSI_RED + "Insufficient Funds for Transfer" + Menu.ANSI_NORMAL);
                                                } else {
                                                    Transaction transaction = new Transaction();
                                                    transaction.setSourceId(currAccId); //self withdraw source
                                                    transaction.setDestId(transferDestId);
                                                    transaction.setAmount(transferAmount);
                                                    transaction.setPending(true);
                                                    tranDao.addTransaction(transaction);
                                                }
                                                browsingAccountMenu = false;
                                                break;
                                            case 4:
                                                //accept pending transfer request
                                                List<Transaction> pendingTransactions;
                                                pendingTransactions = tranDao.getPendingFromCustId(currAccId);
                                                Menu.printPendingTransactions(pendingTransactions);
                                                if (pendingTransactions.size() > 0) {
                                                    //select pending transaction
                                                    int transChoice = Menu.getMenuInput(pendingTransactions.size() + 1);
                                                    Menu.printApprovedDenyChoice();
                                                    int approveOrDeny = Menu.getMenuInput(2);
                                                    Transaction selectedTrans = pendingTransactions.get(transChoice - 1);
                                                    if (approveOrDeny == 1) {
                                                        //check src account has enough money
                                                        if(accDao.getBalance(selectedTrans.getSourceId()) > selectedTrans.getAmount()) {
                                                            //approve transaction
                                                            if (tranDao.approveTransaction(selectedTrans.getTranId())) {
                                                                //transfer money
                                                                if (accDao.transfer(selectedTrans.getSourceId(), selectedTrans.getDestId(), selectedTrans.getAmount())) {
                                                                    System.out.println("Transfer successful!");
                                                                    tranDao.deleteTransaction(selectedTrans.getTranId());
                                                                } else {
                                                                    System.out.println("Transfer of money failed");
                                                                }
                                                            } else {
                                                                System.out.println("Could Not Approve Transfer");
                                                            }
                                                        } else {
                                                            //not enough money in source account
                                                            System.out.println(Menu.ANSI_RED + "Source Account Missing Required Funds, Cannot Approve Transfer" + Menu.ANSI_NORMAL);
                                                        }
                                                    } else {
                                                        //deny / delete transaction
                                                        tranDao.deleteTransaction(selectedTrans.getTranId());
                                                    }
                                                } else {
                                                    System.out.println(Menu.ANSI_CYAN + "No Pending Transfer Offer" + Menu.ANSI_NORMAL);
                                                }
                                                browsingAccountMenu = false;
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
                    //Create user account
                    System.out.println("Create a new user account!");
                    customer = Menu.getInputCustomer();
                    boolean usernameTaken = custDao.usernameTaken(customer.getUsername());
                    if(!usernameTaken)
                        custDao.addCustomer(customer);
                    else
                        System.out.println(Menu.ANSI_RED + "Username already taken, please try again." + Menu.ANSI_NORMAL);
                    break;

                case 3:
                    //Exit
                    Menu.exit();
                    break;

                default:
                    System.out.println("Enter a valid number");
            }
        }
    }
}
