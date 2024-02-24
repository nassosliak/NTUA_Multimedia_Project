package com.example;
public class SessionManager {
    private static SessionManager instance;
    private String storedUsername;

    private SessionManager() {
        // private constructor to enforce singleton pattern
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setStoredUsername(String username) {
        this.storedUsername = username;
    }

    public String getStoredUsername() {
        return storedUsername;
    }
}
