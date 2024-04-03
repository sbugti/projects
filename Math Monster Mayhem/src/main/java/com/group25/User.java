package com.group25;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a user in the system.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class User {

    private String username;
    private Outfit characterOutfit;
    private int level;
    private int score;

    /**
     * Constructs a new User object with the provided username, level, and character outfit.
     * 
     * @param username the username of the user
     * @param level the level of the user
     * @param score the score of the user
     * @param characterOutfit the character outfit of the user
     */
    public User(@JsonProperty("username") String username, 
    @JsonProperty("level") int level, 
    @JsonProperty("score") int score,
    @JsonProperty("outfit") Outfit characterOutfit) {
        this.username = username;
        this.level = level;
        this.score = score;
        this.characterOutfit = characterOutfit;
    }

    /**
     * Constructs a new User object with the provided username, level, and character outfit.
     * 
     * @param username the username of the user
     * @param level the level of the user
     * @param characterOutfit the character outfit of the user
     */
    public User(String username, int level, Outfit characterOutfit) {
        // Constructor implementation
        this(username, level, 0, characterOutfit );
    }
    


    // Getters and setters for the User class attributes

    /**
     * Sets the username of the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the outfit of the user.
     * 
     * @return the outfit of the user
     */
    public Outfit getOutfit() {
        return this.characterOutfit;
    }

    /**
     * Sets the outfit of the user.
     * 
     * @param characterOutfit the outfit to set
     */
    public void setOutfit(Outfit characterOutfit) {
        this.characterOutfit = characterOutfit;
    }

    /**
     * Gets the level of the user.
     * 
     * @return the level of the user
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Sets the level of the user.
     * 
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the score of the user.
     * 
     * @return the score of the user
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the score of the user.
     * 
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Saves the user object to a JSON file.
     */
    public void saveUser() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Check if the user JSON file already exists
            File userFile = new File(username + ".json");
            // Save the user to its own JSON file
            mapper.writeValue(userFile, this);
    
            // Create a JSON object for the current user and save it to another file
            CurrentUser currentUser = new CurrentUser(username);
            mapper.writeValue(new File("current_user.json"), currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a user object from a JSON file.
     * 
     * @param username the username of the user to load
     */
    public void loadUser(String username) {
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            User temp = mapper.readValue(new File(username + ".json"), User.class);

            this.level = temp.getLevel();
            this.score = temp.getScore();
            this.username = temp.getUsername();
            this.characterOutfit = temp.getOutfit();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets information of all players.
     * 
     * @return the list of user objects containing information of all players
     */
    public static List<User> getAllPlayersInfo() {
        // Get list of JSON files in current directory
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        // Extract user information from file names
        List<User> userList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                // Skip if filename is current_user.json
                if (!filename.equalsIgnoreCase("current_user.json")) {
                    // Assuming username is the part of the filename before ".json"
                    String username = filename.substring(0, filename.length() - 5); // Remove ".json"
                    // Load user data from file
                    User user = new User("", 0, 0, null);
                    user.loadUser(username);
                    // Add user object to the list
                    userList.add(user);
                }
            }
        }

        return userList;
    }



    
    public static void main(String[] args) {
        // Create a new outfit data for the example
        int[] outfitData = {2, 3, 4}; // Example outfit data: [hat, face, shirt]
    
        // Create a new outfit object and update it with the example data
        Outfit exampleOutfit = new Outfit();
        exampleOutfit.updateOutfit(outfitData);
    
        // Create a new user with the example outfit
        User user = new User("Bob", 9, 10, exampleOutfit);
    
        // Save the user to a JSON file
        user.saveUser();
    
        // Load the user from the JSON file
        User loadedUser = new User("", 0, 0, exampleOutfit);
        loadedUser.loadUser("Bob");
    
        System.out.println("Loaded User:");
        System.out.println("Username: " + loadedUser.getUsername());
        System.out.println("Level: " + loadedUser.getLevel());
        System.out.println("Score:" + loadedUser.getScore());
        // Print the loaded user's outfit information
        System.out.println("Outfit:");
        Outfit loadedOutfit = loadedUser.getOutfit();
        System.out.println("Hat: " + loadedOutfit.getHat());
        System.out.println("Face: " + loadedOutfit.getFace());
        System.out.println("Shirt: " + loadedOutfit.getShirt());
    }
    
    /**
     * Represents the current user.
     */
    public static class CurrentUser {
        private String currentUser;

        /**
         * Constructs a CurrentUser object.
         */
        public CurrentUser() {
            this.currentUser = null;
        }
    
        /**
         * Constructs a CurrentUser object with the provided username.
         * 
         * @param username the username of the current user
         */
        public CurrentUser(String username) {
            this.currentUser = username;
        }
    
        /**
         * Gets the username of the current user.
         * 
         * @return the username of the current user
         */
        @JsonProperty("currentUser")
        public String getCurrentUser() {
            return currentUser;
        }
    
        /**
         * Sets the username of the current user.
         * 
         * @param currentUser the username to set
         */
        @JsonProperty("currentUser")
        public void setCurrentUser(String currentUser) {
            this.currentUser = currentUser;
        }
    
        /**
         * Loads the current user from a JSON file and returns it as a string.
         * 
         * @return the username of the current user
         * @throws IOException if an I/O error occurs
         */
        public static String loadFromFile() throws IOException {
            String filePath = "current_user.json";
            ObjectMapper mapper = new ObjectMapper();
            CurrentUser currentUser = mapper.readValue(new File(filePath), CurrentUser.class);
            return currentUser != null ? currentUser.getCurrentUser() : null;
        }
    
        /**
         * Saves the current user to a JSON file with the provided username.
         * 
         * @param newUsername the new username to set
         * @return the updated username of the current user
         * @throws IOException if an I/O error occurs
         */
        public static String saveToFile(String newUsername) throws IOException {
            String filePath = "current_user.json";
            ObjectMapper mapper = new ObjectMapper();
            CurrentUser currentUser = new CurrentUser(newUsername);
            mapper.writeValue(new File(filePath), currentUser);
            return currentUser.getCurrentUser();
        }
    }
    
}
