package com.example;

public class UserWrapper {
    private static User user;

    public User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }
}

