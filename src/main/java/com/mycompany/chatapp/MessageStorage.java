/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Student
 */
public class MessageStorage {
    
    private final File file = new File("messages.json");

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public List<Message> loadMessages() {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Message>>() {
            }.getType();

            List<Message> messages = gson.fromJson(
                    reader, listType);

            return messages == null
                    ? new ArrayList<>()
                    : messages;

        } catch (IOException e) {
            System.out.println(
                    "Could not read stored messages: "
                    + e.getMessage());

            return new ArrayList<>();
        }
    }

    public boolean storeMessage(Message message) {
        List<Message> messages = loadMessages();
        messages.add(message);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(messages, writer);
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Could not store message: "
                    + e.getMessage());

            return false;
        }
    }  
}
