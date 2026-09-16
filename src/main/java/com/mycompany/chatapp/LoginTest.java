/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    import java.util.Scanner;

public class Main {

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

        input.close();
    }
    
}
