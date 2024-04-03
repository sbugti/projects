package com.group25;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.io.File;

class UserTest {
    private User user; // default user creation
    private static final String USERNAME = "TestUser";
    private static final String USER_FILE = USERNAME + ".json";
    private static final String CURRENT_USER_FILE = "current_user.json";
    private static final int INITIAL_LEVEL = 1;
    private static final int INITIAL_SCORE = 0;
    private static final int INITIAL_HIGH_SCORE = 0;

    @BeforeEach
    void setUp() { // set up testing crap
        user = new User(USERNAME, INITIAL_LEVEL, new Outfit());
    }

    @AfterEach
    void tearDown() { // delete testing crap
        new File(USER_FILE).delete();
        new File(CURRENT_USER_FILE).delete();
    }

    @Test
    void testUsernameGetterSetter() {
        String newUsername = "NewUsername";
        user.setUsername(newUsername);
        assertEquals(newUsername, user.getUsername(), "username getter/setter wrong");
    }

    @Test
    void testOutfitGetterSetter() {
        Outfit newOutfit = new Outfit();
        user.setOutfit(newOutfit);
        assertEquals(newOutfit, user.getOutfit(), "outfit getter/setter wrong");
    }

    @Test
    void testLevelGetterSetter() {
        int newLevel = 5;
        user.setLevel(newLevel);
        assertEquals(newLevel, user.getLevel(), "level getter/setter wrong");
    }

    @Test
    void testScoreGetterSetter() {
        int newScore = 100;
        user.setScore(newScore);
        assertEquals(newScore, user.getScore(), "score getter/setter wrong");
    }

    @Test
    void testSaveAndLoadUser() {
        user.saveUser();
        User loadedUser = new User("", 0, new Outfit());
        loadedUser.loadUser(USERNAME);
        assertEquals(USERNAME, loadedUser.getUsername(), "loaded username incorrect");
        assertEquals(INITIAL_LEVEL, loadedUser.getLevel(), "loaded level incorrect");
        assertEquals(INITIAL_SCORE, loadedUser.getScore(), "loaded score incorrect");
    }

    @Test
    void testGetCurrentUser() throws IOException {
        User.CurrentUser.saveToFile(USERNAME);
        assertEquals(USERNAME, User.CurrentUser.loadFromFile(), "loaded user does not match saved user");
    }

    @Test
    void testGetAllPlayersInfo() { // testing multiple profiles
        User user1 = new User("User1", 1, new Outfit());
        User user2 = new User("User2", 2, new Outfit());
        user1.saveUser();
        user2.saveUser();

        List<User> users = User.getAllPlayersInfo();
        assertTrue(users.size() >= 2, "multiple users not functional");
        assertTrue(users.stream().anyMatch(u -> "User1".equals(u.getUsername())), "missing first user");
        assertTrue(users.stream().anyMatch(u -> "User2".equals(u.getUsername())), "missing 2nd user");
    }
}
