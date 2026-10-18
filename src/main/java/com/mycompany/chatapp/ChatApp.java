/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.util.Scanner;


/**
 *
 * @author Student
 */
public class ChatApp {

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
        }
        boolean running = true;

while (running) {
    System.out.println("Welcome to QuickChat.");
    System.out.println("1. Send Messages");
    System.out.println("2. Show Recently Sent Messages");
    System.out.println("3. Quit");

    int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice) {
        case 1:
            // Send messages
            break;

        case 2:
            System.out.println("Coming Soon.");
            break;

        case 3:
            running = false;
            System.out.println("Goodbye!");
            break;

        default:
            System.out.println("Invalid option. Please try again.");
    }

        input.close();
   
}
