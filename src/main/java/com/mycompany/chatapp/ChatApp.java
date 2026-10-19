/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatApp {
    
    private final Scanner input;
    private final MessageStorage storage;
    private final List<Message> sentMessages;

    private int totalMessagesSent = 0;
    private int messageNumber = 0;

    public ChatApp() {
        input = new Scanner(System.in);
        storage = new MessageStorage();
        sentMessages = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        ChatApp chatApp = new ChatApp();
        chatApp.startChat();
    }
    
    public void startChat() {
        System.out.println("\nWelcome to QuickChat!");

        System.out.print("How many messages would you like to process? ");
        int numberOfMessages = readPositiveNumber();

        for (int i = 0; i < numberOfMessages; i++) {
            System.out.println("\nMessage " + (i + 1));

            System.out.print("Enter recip  ient number: ");
            String recipient = input.nextLine().trim();

            System.out.print("Enter your message: ");
            String text = input.nextLine();

            Message message = new Message(
                    messageNumber, recipient, text);

            if (!message.checkMessageID()) {
                System.out.println("Invalid message ID.");
                i--;
                continue;
            }

            if (!message.checkRecipientCell()) {
                System.out.println(
                        "Cell phone number is incorrectly formatted "
                        + "or does not contain an international code. "
                        + "Please correct the number and try again.");

                i--;
                continue;
            }

            String validation = message.validateMessageLength();

            if (!validation.equals("Message ready to send.")) {
                System.out.println(validation);
                i--;
                continue;
            }

            System.out.println("\nChoose an action:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Enter choice: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    message.setAction("Send");
                    sentMessages.add(message);
                    totalMessagesSent++;
                    messageNumber++;

                    System.out.println("Message successfully sent.");
                    displayMessage(message);
                    break;

                case "2":
                    message.setAction("Disregarded");
                    System.out.println(
                            "Press 0 to delete the message.");
                    System.out.print("Enter 0 to confirm: ");

                    String deleteChoice = input.nextLine().trim();

                    if (deleteChoice.equals("0")) {
                        System.out.println("Message disregarded.");
                    } else {
                        System.out.println(
                                "Message was not deleted.");
                    }
                    break;

                case "3":
                    message.setAction("Stored");

                    if (storage.storeMessage(message)) {
                        System.out.println(
                                "Message successfully stored.");
                    }
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Message not processed.");
                    i--;
                    continue;
            }
        }

        System.out.println("\n========== CHAT SUMMARY ==========");
        System.out.println(
                "Total messages sent: " + returnTotalMessages());

        printMessages();
    }

    private int readPositiveNumber() {
        while (true) {
            String value = input.nextLine().trim();

            try {
                int number = Integer.parseInt(value);

                if (number >= 0) {
                    return number;
                }

            } catch (NumberFormatException e) {
                // Ask again when the input is not a number.
            }

            System.out.print(
                    "Please enter a valid number (0 or greater): ");
        }
    }

    private void displayMessage(Message message) {
        System.out.println("\nMessage details:");
        System.out.println("Message ID: "
                + message.getMessageID());
        System.out.println("Message Hash: "
                + message.getMessageHash());
        System.out.println("Recipient: "
                + message.getRecipient());
        System.out.println("Message: "
                + message.getMessageText());
    }

    public String printMessages() {
        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {
            result.append("Message ID: ")
                    .append(message.getMessageID())
                    .append("\nMessage Hash: ")
                    .append(message.getMessageHash())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\nMessage: ")
                    .append(message.getMessageText())
                    .append("\n\n");
        }

        if (result.length() == 0) {
            result.append("No messages have been sent.");
        }

        System.out.println(result);
        return result.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }   
}
