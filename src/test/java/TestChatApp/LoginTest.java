/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestChatApp;

import com.mycompany.chatapp.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testPasswordCorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkPasswordComplexity());
    }
    
     @Test
    public void testPasswordIncorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertEquals(false, user.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrect() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "08966553"
        );

        assertEquals(false, user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                true,
                user.loginUser("kyl_1", "Ch&6sec@ke99!")
        );
    }

    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "Lebohang",
                "Letsoenyo",
                "kyl_1",
                "Ch&6sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                false,
                user.loginUser("wrong", "wrong")
        );
    }
    
}
