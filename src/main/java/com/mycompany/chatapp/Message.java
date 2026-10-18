/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.UUID;
/**
 *
 * @author Student
 */
public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String action;

    public Message() {
        // Required for JSON conversion.
    }

    public Message(int messageNumber, String recipient,
                   String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.action = "Pending";
    }

    private String generateMessageID() {
        // Generate a unique 10-digit message ID.
        long id = 1_000_000_000L
                + (long) (Math.random() * 9_000_000_000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{10}");
    }

    public boolean checkRecipientCell() {
        // Require an international number beginning with +.
        return recipient != null
                && recipient.matches("\\+[1-9]\\d{9,14}");
    }

    public String validateMessageLength() {
        if (messageText == null) {
            return "Message cannot be empty.";
        }

        if (messageText.length() > 250) {
            int excess = messageText.length() - 250;

            return "Message exceeds 250 characters by "
                    + excess
                    + "; please reduce the size.";
        }

        return "Message ready to send.";
    }

    public String createMessageHash() {
        if (messageID == null || messageText == null
                || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll(
                "^[^\\p{L}\\p{N}]+|[^\\p{L}\\p{N}]+$", "");

        String lastWord = words[words.length - 1].replaceAll(
                "^[^\\p{L}\\p{N}]+|[^\\p{L}\\p{N}]+$", "");

        String firstTwoDigits = messageID.substring(0, 2);

        return (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public String getAction() {
        return action;
    }
    
}
