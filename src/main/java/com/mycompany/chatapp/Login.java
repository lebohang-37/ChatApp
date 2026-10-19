/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.regex.Pattern;

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

        return username.contains("_") && username.length() <= 5;
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
    }// Check South African cellphone number
    public boolean checkCellPhoneNumber() {

        String phonePattern = "^\\+27\\d{9}$";

        return Pattern.matches(phonePattern, cellPhoneNumber);
    }

    // Register user
    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber()) {

            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";

        } else {

            return "User registered successfully.";
        }
    }

    // Check login details
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        if (enteredUsername.equals(username)
                && enteredPassword.equals(password)) {

            return true;

        } else {

            return false;
        }
    }

    // Return login status
    public String returnLoginStatus(String enteredUsername,
                                    String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
}

