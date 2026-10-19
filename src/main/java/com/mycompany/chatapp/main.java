/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.util.Scanner;


/**
 *
 * @author Student
 */
public class main {

    public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("          CHAT APP");
        System.out.println("================================");

        // Registration
        System.out.println("\n--- REGISTER ---");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
 
        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        System.out.print("Enter your cell phone number: ");
        String cellPhoneNumber = input.nextLine();

        // Create Login object
        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        // Display registration result
        System.out.println("\n" + user.registerUser());

        // Check if registration was successful
        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            // Login
            System.out.println("\n--- LOGIN ---");

            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            System.out.println(
                    user.returnLoginStatus(
                            loginUsername,
                            loginPassword
                    )
            );
            // Start the chat only after successful login.
            if (loginUsername.equals(username)
                    && loginPassword.equals(password)) {

                System.out.println("\nLogin successful!");

                ChatApp chatApp = new ChatApp();
                chatApp.startChat();

            } else {
                System.out.println(
                        "Login failed. You cannot access the chat."
                );
            }

        } else {
            System.out.println(
                    "\nRegistration failed. Please correct your details."
            );
        }

        input.close();
    }
}
