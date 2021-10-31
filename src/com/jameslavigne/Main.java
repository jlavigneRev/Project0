package com.jameslavigne;

public class Main {

    public static void main(String[] args) {

        boolean browsingStart = true;
        while (browsingStart) {
            Menu.printStartMenu();
            int startChoice = Menu.getMenuInput(3);

            switch (startChoice) {
                case 1:
                    //Login
                    Menu.printLoginMenu();
                    System.out.print("Enter username:");
                    String username = Menu.scanner.nextLine();
                    System.out.print("Enter password:");
                    String password = Menu.scanner.nextLine();

                    /**
                     * @TODO Validate login credentials from db
                     */

                    boolean loginSuccess = false;
                    if (!loginSuccess) {
                        Menu.printInvalidLogin();
                    }


                    break;
                case 2:
                    //Create account
                    System.out.println("Account Creation Menu");
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
