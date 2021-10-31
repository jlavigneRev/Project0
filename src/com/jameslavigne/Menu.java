package com.jameslavigne;

import java.util.Scanner;

/**
 * @author James Lavigne
 */
public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    //console colors
    public static String normalText = "\033[0m";
    public static String redText = "\033[0;31m";

    private Menu() {
    }

    /**
     * Gets user input for menu selection
     * @param max Number of choices listed in menu
     * @return number selected by user
     */
    public static int getMenuInput(int max) {
        while (true) {
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input >= 1 && input <= max) {
                //valid number
                return input;
            } else {
                System.out.println("Please, Enter a valid selection from (1-" + max + ")");
            }
        }
    }

    public static void printStartMenu() {
        System.out.println("*********************************************");
        System.out.println("Welcome!");
        System.out.println("Please choose an option listed below (1-3):");
        System.out.println("Enter 1. Login");
        System.out.println("Enter 2. Create New Account");
        System.out.println("Enter 3. Exit");
        System.out.println("********************************************");
    }

    public static void printLoginMenu(){
        System.out.println("********************************************");
        System.out.println("Please enter your login credentials below.");
    }

    public static void printInvalidLogin(){
        System.out.println(redText + "Invalid login credentials" + normalText);
    }
}
