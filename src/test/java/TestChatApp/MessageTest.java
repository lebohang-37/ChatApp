/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestChatApp;

import com.mycompany.chatapp.Message;
import com.mycompany.chatapp.MessageStorage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Student
 */
public class MessageTest {

    @Test
    public void testMessageIDIsCreated() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertNotNull(message.getMessageID());
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testValidRecipientNumber() {

        Message message = new Message(
                0, "+27718693002", "Hello"
        );

        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientNumber() {

        Message message = new Message(
                0, "08575975889", "Hello"
        );

        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageWithin250Characters() {

        Message message = new Message(
                0, "+27718693002", "Hello"
        );

        assertEquals(
                "Message ready to send.",
                message.validateMessageLength()
        );
    }

    @Test
    public void testMessageExceeds250Characters() {

        String longText = "A".repeat(251);

        Message message = new Message(
                0, "+27718693002", longText
        );

        assertEquals(
                "Message exceeds 250 characters by 1"
                + "; please reduce the size.",
                message.validateMessageLength()
        );
    }

    @Test
    public void testMessageHash() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        message.setMessageID("0012345678");

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash()
        );
    }

    @Test
    public void testSendMessageAction() {

        Message message = new Message(
                0, "+27718693002", "Hello"
        );

        message.setAction("Send");

        assertEquals("Send", message.SentMessage());
    }

    @Test
    public void testDisregardMessageAction() {

        Message message = new Message(
                0, "+27718693002", "Hello"
        );

        message.setAction("Disregarded");

        assertEquals("Disregarded", message.SentMessage());
    }

    @Test
    public void testStoreMessageAction() {

        Message message = new Message(
                0, "+27718693002", "Hello"
        );

        message.setAction("Stored");

        assertEquals("Stored", message.SentMessage());
    }

    @Test
    public void testMessageCanBeStored() {

        MessageStorage storage = new MessageStorage();

        Message message = new Message(
                0, "+27718693002", "Message to store"
        );

        message.setAction("Stored");

        assertTrue(storage.storeMessage(message));

        List<Message> storedMessages = storage.loadMessages();

        assertFalse(storedMessages.isEmpty());

        assertTrue(storedMessages.stream().anyMatch(
                saved -> saved.getMessageID().equals(
                        message.getMessageID()
                )
        ));
    }

    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> disregardedMessages = new ArrayList<>();
    private final List<Message> storedMessages = new ArrayList<>();

    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIDs = new ArrayList<>();

    // Populate the collections using the assignment test data.
    private void populateTestData() {

        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        // Test Message 1: Sent
        Message message1 = new Message(
                0,
                "+27834557896",
                "Did you get the cake?"
        );

        message1.setAction("Sent");
        sentMessages.add(message1);

        // Test Message 2: Stored
        Message message2 = new Message(
                1,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        message2.setAction("Stored");
        storedMessages.add(message2);

        // Test Message 3: Disregarded
        Message message3 = new Message(
                2,
                "+27834484567",
                "Yohoooo, I am at your gate."
        );

        message3.setAction("Disregarded");
        disregardedMessages.add(message3);

        // Test Message 4: Sent
        Message message4 = new Message(
                3,
                "0838884567",
                "It is dinner time!"
        );

        message4.setAction("Sent");
        sentMessages.add(message4);

        // Test Message 5: Stored
        Message message5 = new Message(
                4,
                "+27838884567",
                "Ok, I am leaving without you."
        );

        message5.setAction("Stored");
        storedMessages.add(message5);

        // Populate the message hash and ID collections.
        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(disregardedMessages);
        allMessages.addAll(storedMessages);

        for (Message message : allMessages) {

            messageHashes.add(message.getMessageHash());
            messageIDs.add(message.getMessageID());
        }
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {

        populateTestData();

        assertEquals(2, sentMessages.size());

        assertEquals(
                "Did you get the cake?",
                sentMessages.get(0).getMessageText()
        );

        assertEquals(
                "It is dinner time!",
                sentMessages.get(1).getMessageText()
        );
    }

    @Test
    public void testDisregardedMessagesArrayCorrectlyPopulated() {

        populateTestData();

        assertEquals(1, disregardedMessages.size());

        assertEquals(
                "Yohoooo, I am at your gate.",
                disregardedMessages.get(0).getMessageText()
        );
    }

    @Test
    public void testStoredMessagesArrayCorrectlyPopulated() {

        populateTestData();

        assertEquals(2, storedMessages.size());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                storedMessages.get(0).getMessageText()
        );

        assertEquals(
                "Ok, I am leaving without you.",
                storedMessages.get(1).getMessageText()
        );
    }

    @Test
    public void testMessageHashesArrayCorrectlyPopulated() {

        populateTestData();

        assertEquals(5, messageHashes.size());

        for (String hash : messageHashes) {
            assertNotNull(hash);
            assertFalse(hash.isEmpty());
        }
    }

    @Test
    public void testMessageIDsArrayCorrectlyPopulated() {

        populateTestData();

        assertEquals(5, messageIDs.size());

        for (String id : messageIDs) {
            assertNotNull(id);
            assertEquals(10, id.length());
        }
    }

    @Test
    public void testDisplayStoredSendersAndRecipients() {

        populateTestData();

        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {

            result.append("Sender: QuickChat User")
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n");
        }

        assertTrue(result.toString().contains("+27838884567"));
        assertTrue(result.toString().contains("Sender: QuickChat User"));
    }

    @Test
    public void testDisplayLongestStoredMessage() {

        populateTestData();

        Message longest = storedMessages.get(0);

        for (Message message : storedMessages) {

            if (message.getMessageText().length()
                    > longest.getMessageText().length()) {

                longest = message;
            }
        }

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longest.getMessageText()
        );
    }

    @Test
    public void testSearchMessageFour() {

        populateTestData();

        String searchValue = "0838884567";
        String result = "";

        for (Message message : sentMessages) {

            if (message.getRecipient().equals(searchValue)) {

                result = message.getMessageText();
                break;
            }
        }

        assertEquals("It is dinner time!", result);
    }

    @Test
    public void testSearchMessagesByRecipient() {

        populateTestData();

        String recipient = "+27838884567";

        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        List<String> results = new ArrayList<>();

        for (Message message : allMessages) {

            if (message.getRecipient().equals(recipient)) {

                results.add(message.getMessageText());
            }
        }

        assertEquals(2, results.size());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                results.get(0)
        );

        assertEquals(
                "Ok, I am leaving without you.",
                results.get(1)
        );
    }

    @Test
    public void testDeleteMessageUsingHash() {

        populateTestData();

        Message messageToDelete = storedMessages.get(0);

        String hash = messageToDelete.getMessageHash();

        boolean deleted = storedMessages.removeIf(
                message -> message.getMessageHash().equals(hash)
        );

        assertTrue(deleted);

        assertEquals(1, storedMessages.size());

        assertEquals(
                "Ok, I am leaving without you.",
                storedMessages.get(0).getMessageText()
        );
    }

    @Test
    public void testDisplayMessageReport() {

        populateTestData();

        StringBuilder report = new StringBuilder();

        for (Message message : sentMessages) {

            report.append("Message Hash: ")
                    .append(message.getMessageHash());

            report.append("\nRecipient: ")
                    .append(message.getRecipient());

            report.append("\nMessage: ")
                    .append(message.getMessageText());

            report.append("\n\n");
        }

        assertTrue(report.toString().contains("Message Hash:"));
        assertTrue(report.toString().contains("Recipient:"));
        assertTrue(report.toString().contains("Did you get the cake?"));
        assertTrue(report.toString().contains("It is dinner time!"));
    }
}
