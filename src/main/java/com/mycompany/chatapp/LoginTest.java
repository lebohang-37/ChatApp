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
    
    @Test
    public void testCheckUserNameCorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyle!!!!!!",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(false, user.checkUserName());
    }
    
}
