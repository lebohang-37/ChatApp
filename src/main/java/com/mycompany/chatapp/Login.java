/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author Student
 */

public class Login {

    // Variables
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Constructor
    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    } 

 // Check if username is correctly formatted
    public boolean checkUserName() {

        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    // Check if password meets complexity requirements
    public boolean checkPasswordComplexity() {

        boolean capitalLetter = false;
        boolean number = false;
        boolean specialCharacter = false;

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                capitalLetter = true;
            }

            if (Character.isDigit(character)) {
                number = true;
            }
            
            if (!Character.isLetterOrDigit(character)) {
                specialCharacter = true;
            }
        }

            if (password.length() >= 8
                && capitalLetter
                && number
                && specialCharacter) {

            return true;

            } else {
            return false;
        }
    }