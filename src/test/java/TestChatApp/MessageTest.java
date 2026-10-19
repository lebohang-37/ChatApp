/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestChatApp;

import com.mycompany.chatapp.Message;
import com.mycompany.chatapp.MessageStorage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    @Test
    public void testMessageIDIsCreated() {
        Message message = new Message(
                0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        assertNotNull(message.getMessageID());
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                0, "+27718693002", "Hello");

        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                0, "08575975889", "Hello");

        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageWithin250Characters() {
        Message message = new Message(
                0, "+27718693002", "Hello");

        assertEquals(
                "Message ready to send.",
                message.validateMessageLength());
    }

    @Test
    public void testMessageExceeds250Characters() {
        String longText = "A".repeat(251);

        Message message = new Message(
                0, "+27718693002", longText);

        assertEquals(
                "Message exceeds 250 characters by 1"
                + "; please reduce the size.",
                message.validateMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        message.setMessageID("0012345678");

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash());
    }

    @Test
    public void testSendMessageAction() {
        Message message = new Message(
                0, "+27718693002", "Hello");

        message.setAction("Send");

        assertEquals("Send", message.SentMessage());
    }

    @Test
    public void testDisregardMessageAction() {
        Message message = new Message(
                0, "+27718693002", "Hello");

        message.setAction("Disregarded");

        assertEquals("Disregarded", message.SentMessage());
    }

    @Test
    public void testStoreMessageAction() {
        Message message = new Message(
                0, "+27718693002", "Hello");

        message.setAction("Stored");

        assertEquals("Stored", message.SentMessage());
    }
    
@Test
public void testMessageCanBeStored() {
    MessageStorage storage = new MessageStorage();

    Message message = new Message(
            0, "+27718693002", "Message to store");

    message.setAction("Stored");

    assertTrue(storage.storeMessage(message));

    java.util.List<Message> storedMessages =
            storage.loadMessages();

    assertFalse(storedMessages.isEmpty());

    assertTrue(storedMessages.stream().anyMatch(
            saved -> saved.getMessageID().equals(
                    message.getMessageID())));
}   
}
