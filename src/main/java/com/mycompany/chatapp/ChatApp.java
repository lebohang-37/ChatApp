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
    private final String sender;

    private final List<Message> sentMessages;
    private final List<Message> disregardedMessages;
    private final List<Message> storedMessages;
    private final List<String> messageHashes;
    private final List<String> messageIDs;

    private int totalMessagesSent = 0;
    private int messageNumber = 0;

    public ChatApp() {
        this("QuickChat User");
    }

    public ChatApp(String sender) {

        input = new Scanner(System.in);
        storage = new MessageStorage();
        this.sender = sender;

        sentMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();

        // Load stored messages from the JSON file.
        storedMessages = new ArrayList<>(storage.loadMessages());

        messageHashes = new ArrayList<>();
        messageIDs = new ArrayList<>();

        // Populate the ID and hash collections.
        for (Message message : storedMessages) {
            messageHashes.add(message.getMessageHash());
            messageIDs.add(message.getMessageID());
        }

        // Continue numbering after previously stored messages.
        for (Message message : storedMessages) {
            if (message.getMessageNumber() >= messageNumber) {
                messageNumber = message.getMessageNumber() + 1;
            }
        }
    }

    public static void main(String[] args) {
        ChatApp chatApp = new ChatApp();
        chatApp.startChat();
    }

    // ================= MAIN MENU =================

    public void startChat() {

        boolean running = true;

        System.out.println("\nWelcome to QuickChat, " + sender + "!");

        while (running) {

            System.out.println("\n========== QUICKCHAT MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Display Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    sendMessages();
                    break;

                case "2":
                    printMessages();
                    break;

                case "3":
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;

                case "4":
                    storedMessagesMenu();
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ================= PROCESS MESSAGES =================

    private void sendMessages() {

        System.out.print("How many messages would you like to process? ");

        int numberOfMessages = readPositiveNumber();

        for (int i = 0; i < numberOfMessages; i++) {

            System.out.println("\nMessage " + (i + 1));

            System.out.print("Enter recipient number: ");
            String recipient = input.nextLine().trim();

            System.out.print("Enter your message: ");
            String text = input.nextLine();

            Message message = new Message(
                    messageNumber,
                    sender,
                    recipient,
                    text
            );

            if (!message.checkMessageID()) {
                System.out.println("Invalid message ID.");
                i--;
                continue;
            }

            if (!message.checkRecipientCell()) {
                System.out.println("Invalid cellphone number.");
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
                    message.setAction("Sent");
                    sentMessages.add(message);
                    addMessageIdentifiers(message);

                    totalMessagesSent++;
                    messageNumber++;

                    System.out.println("Message successfully sent.");
                    displayMessage(message);
                    break;

                case "2":
                    message.setAction("Disregarded");
                    disregardedMessages.add(message);
                    addMessageIdentifiers(message);

                    messageNumber++;

                    System.out.println("Message disregarded.");
                    break;

                case "3":
                    message.setAction("Stored");

                    if (storage.storeMessage(message)) {

                        storedMessages.add(message);
                        addMessageIdentifiers(message);

                        messageNumber++;

                        System.out.println("Message successfully stored.");

                    } else {
                        System.out.println("Message could not be stored.");
                        i--;
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    i--;
                    break;
            }
        }
    }

    // Add message IDs and hashes to their collections.
    private void addMessageIdentifiers(Message message) {

        messageHashes.add(message.getMessageHash());
        messageIDs.add(message.getMessageID());
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
                // Invalid number.
            }

            System.out.print("Enter a valid number (0 or greater): ");
        }
    }

    // ================= DISPLAY MESSAGES =================

    private void displayMessage(Message message) {

        System.out.println("\nMessage details:");

        System.out.println("Sender: " + message.getSender());
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessageText());
        System.out.println("Action: " + message.getAction());
    }

    public String printMessages() {

        StringBuilder result = new StringBuilder();

        if (sentMessages.isEmpty()) {

            result.append("No messages have been sent.");

        } else {

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
        }

        System.out.println(result);
        return result.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    private void storedMessagesMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n========== STORED MESSAGES ==========");
            System.out.println("1. Display sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message using its ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete a message using its hash");
            System.out.println("6. Display a full message report");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {

                case "1":
                    displayStoredSendersAndRecipients();
                    break;

                case "2":
                    displayLongestStoredMessage();
                    break;

                case "3":
                    System.out.print("Enter message ID or recipient number: ");
                    String searchValue = input.nextLine().trim();
                    System.out.println(searchMessage(searchValue));
                    break;

                case "4":
                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine().trim();
                    System.out.println(searchMessagesByRecipient(recipient));
                    break;

                case "5":
                    System.out.print("Enter message hash: ");
                    String hash = input.nextLine().trim();
                    System.out.println(deleteStoredMessageByHash(hash));
                    break;

                case "6":
                    System.out.println(generateMessageReport());
                    break;

                case "7":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //Display sender and recipient.
    public String displayStoredSendersAndRecipients() {

        StringBuilder result = new StringBuilder();

        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }

        for (Message message : storedMessages) {

            result.append("Sender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n-------------------------\n");
        }

        return result.toString();
    }

    // Display the longest stored message.
    public String displayLongestStoredMessage() {

        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }

        Message longest = storedMessages.get(0);

        for (Message message : storedMessages) {

            if (message.getMessageText().length()
                    > longest.getMessageText().length()) {

                longest = message;
            }
        }

        return longest.getMessageText();
    }

    //Search by message ID or recipient number.
    public String searchMessage(String searchValue) {

        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        for (Message message : allMessages) {

            if (message.getMessageID().equals(searchValue)
                    || message.getRecipient().equals(searchValue)) {

                return message.getMessageText();
            }
        }

        return "No matching message found.";
    }

    //Search sent and stored messages by recipient.
    public String searchMessagesByRecipient(String recipient) {

        StringBuilder result = new StringBuilder();

        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        for (Message message : allMessages) {

            if (message.getRecipient().equalsIgnoreCase(recipient)) {

                result.append(message.getMessageText()).append("\n");
            }
        }

        if (result.length() == 0) {
            return "No messages found for this recipient.";
        }

        return result.toString().trim();
    }

    // Delete a stored message using its hash.
    public String deleteStoredMessageByHash(String searchHash) {

        Message foundMessage = null;

        for (Message message : storedMessages) {

            if (message.getMessageHash().equalsIgnoreCase(searchHash)) {
                foundMessage = message;
                break;
            }
        }

        if (foundMessage == null) {
            return "No stored message found with that hash.";
        }

        String messageText = foundMessage.getMessageText();

        if (storage.deleteMessage(searchHash)) {

            storedMessages.remove(foundMessage);

            messageHashes.remove(foundMessage.getMessageHash());
            messageIDs.remove(foundMessage.getMessageID());

            return "Message: \"" + messageText
                    + "\" successfully deleted.";
        }

        return "Message could not be deleted.";
    }

    // Full report of sent and stored messages.
    public String generateMessageReport() {

        StringBuilder report = new StringBuilder();

        report.append("========== MESSAGE REPORT ==========\n");

        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            return "No messages are available for the report.";
        }

        for (Message message : allMessages) {

            report.append("\nMessage Hash: ")
                    .append(message.getMessageHash());

            report.append("\nRecipient: ")
                    .append(message.getRecipient());

            report.append("\nMessage: ")
                    .append(message.getMessageText());

            report.append("\nAction: ")
                    .append(message.getAction());

            report.append("\n-----------------------------------\n");
        }

        report.append("\nTotal messages in report: ")
                .append(allMessages.size());

        return report.toString();
    }
}
