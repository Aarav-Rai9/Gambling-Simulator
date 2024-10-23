package com.org;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static int balance = 0;
    private static final String BALANCE_FILE = "balance.txt";

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Gambling Simulator!");
            System.out.println("Select an option:");
            System.out.println("1) Start a new game");
            System.out.println("2) Load a game");
            System.out.println("3) Save the game");
            System.out.println("4) Delete the game");
            System.out.println("5) Exit");

            System.out.print("Option (1 - 5): ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    startNewGame();
                    playMenu();
                    break;
                case 2:
                    loadGame();
                    playMenu();
                    break;
                case 3:
                    saveGame();
                    break;
                case 4:
                    deleteGame();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public static void startNewGame() {
        balance = 0;
        System.out.println("New game started. Balance is " + balance);
    }

    public static void loadGame() {
        balance = readBalance();
        System.out.println("Game loaded. Balance is " + balance);
    }

    public static void saveGame() {
        saveBalance(balance);
        System.out.println("Game saved. Balance is " + balance);
    }

    public static void deleteGame() {
        File file = new File(BALANCE_FILE);
        if (file.delete()) {
            balance = 0;
            System.out.println("Game deleted. Balance reset to " + balance);
        } else {
            System.out.println("Error deleting the game.");
        }
    }

    public static void playMenu() {
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1) Play");
            System.out.println("2) Check balance");
            System.out.println("3) Exit");

            System.out.print("Option (1 - 3): ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    play();
                    break;
                case 2:
                    System.out.println("Balance: " + balance);
                    break;
                case 3:
                    saveGame();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public static void play() {
        System.out.print("Enter the amount you want to bet: ");
        int bet = scanner.nextInt();

        while (true) {
            if (bet < 0) {
                System.out.println("You can't bet a negative amount!");
                System.out.print("Enter the amount you want to bet: ");
                bet = scanner.nextInt();
            } else {
                break;
            }
        }

        while (true) {
            if (bet > balance) {
                System.out.println("You don't have enough money to bet! If continue and lose, you will be in debt.");

                System.out.print("Do you want to continue? (y/n): ");
                String answer = scanner.next();

                if (Objects.equals(answer, "n")) {
                    return;
                } else if (Objects.equals(answer, "y")) {
                    break;
                } else {
                    System.out.println("Invalid option!");
                }
            }
        }

        int number = (int) (Math.random() * 10);

        System.out.print("Enter your guess (0 - 4): ");
        int guess = scanner.nextInt();

        while (true) {
            if (guess < 0 || guess > 4) {
                System.out.println("Invalid guess!");
                System.out.print("Enter your guess (0 - 4): ");
                guess = scanner.nextInt();
            } else {
                break;
            }
        }

        System.out.println("Number: " + number);

        if (guess == number) {
            balance += bet * 10;
            System.out.println("You won! Your balance is now " + balance);
        } else {
            balance -= bet;
            System.out.println("You lost! Your balance is now " + balance);
        }
    }

    private static int readBalance() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BALANCE_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // Default balance if file not found or invalid
        }
    }

    private static void saveBalance(int balance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCE_FILE))) {
            writer.write(String.valueOf(balance));
        } catch (IOException e) {
            System.out.println("Error saving balance: " + e.getMessage());
        }
    }
}