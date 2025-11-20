//Nathanael Obrey & James McKean
//11-18-2025
//Lab: Linked List Card Game

//package linkedLists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class CardGame {
   
    private static LinkList cardList = new LinkList();  // make list
    private static int playerWins = 0;  // Track player wins
    private static int playerLosses = 0;  // Track player losses

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        System.out.println("=== Welcome to Card Game ===");

        while (playAgain) {
            // Reset the card list for a new round
            cardList = new LinkList();

            // File name to read from
            String fileName = "cards.txt"; // Ensure the file is in the working directory or specify the full path

            // Read the file and create Card objects (store temporarily in a List)
            List<Card> deck = new ArrayList<>();
            // Read the file and create Card objects
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Split the line into components
                    String[] details = line.split(","); // Assuming comma-separated values
                    if (details.length == 4) {
                        // Parse card details
                        String suit = details[0].trim();
                        String name = details[1].trim();
                        int value = Integer.parseInt(details[2].trim());
                        String pic = details[3].trim();

                        // Create a new Card object
                        Card card = new Card(suit, name, value, pic);

                        // Add the Card object to the temporary deck
                        deck.add(card);
                    } else {
                        System.err.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }

            // Shuffle the deck and populate the linked list in shuffled order
            Collections.shuffle(deck);
            // add to cardList so that deck.get(0) becomes the first element
            for (int i = deck.size() - 1; i >= 0; i--) {
                cardList.add(deck.get(i));
            }

            // Print the shuffled deck
            //System.out.println("Cards loaded (shuffled):");
            //cardList.displayList();
           
            Card[] playerHand = new Card[5];
            for (int i = 0; i < playerHand.length; i++)
                playerHand[i] = cardList.getFirst();

            // Create bot hand and draw 5 cards for bot
            Card[] botHand = new Card[5];
            for (int i = 0; i < botHand.length; i++)
                botHand[i] = cardList.getFirst();
           
            //System.out.println("players hand");
            //for(int i = 0; i < playerHand.length; i++)
            //  System.out.println(playerHand[i]);
           
            //System.out.println();
            //System.out.println("bot hand");
            //for (int i = 0; i < botHand.length; i++)
            //    System.out.println(botHand[i]);

            // Each draws the top card from their hand (index 0)
            Card playerTop = playerHand[0];
            Card botTop = botHand[0];

            System.out.println("\n--- Round Started ---");
            System.out.println("Top cards drawn:");
            System.out.println("Player draws: " + playerTop);
            System.out.println("Bot draws:    " + botTop);

            int playerValue = playerTop.getCardValue();
            int botValue = botTop.getCardValue();

            if (playerValue > botValue) {
                System.out.println("Result: Player wins!");
                playerWins++;
            } else if (botValue > playerValue) {
                System.out.println("Result: Bot wins!");
                playerLosses++;
            } else {
                System.out.println("Result: It's a tie!");
            }

            // Display current tally
            System.out.println("\n--- Tally ---");
            System.out.println("Wins: " + playerWins);
            System.out.println("Losses: " + playerLosses);

            // Ask if player wants to play again
            System.out.print("\nDo you want to play again? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("yes") || response.equals("y")) {
                playAgain = true;
            } else {
                playAgain = false;
            }
        }

        // Game ended
        System.out.println("\n=== Game Over ===");
        System.out.println("Final Tally:");
        System.out.println("Total Wins: " + playerWins);
        System.out.println("Total Losses: " + playerLosses);
        scanner.close();

    }//end main

}//end class