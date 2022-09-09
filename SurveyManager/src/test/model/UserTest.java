package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


// Test for User class
public class UserTest {

    private User user1;
    private User user2;

    @BeforeEach
    void runBeforeEach() {
        user1 = new User("X", "123");
        user2 = new User("", "555");
    }

    @Test
    void testUserConstructor(){
        assertEquals("X", user1.getUserName());
        assertEquals("123", user1.getPassword());

        assertEquals("anonymous", user2.getUserName());
    }
}
