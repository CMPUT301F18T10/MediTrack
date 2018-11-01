package com.example.meditrack;

public abstract class AbstractUser {

    String userId;

    // Maybe we will implement password later
    // String password;

    public String getUserId() {
        return userId;
    }

    public AbstractUser(String userId) {
        this.userId = userId;
    }
}
