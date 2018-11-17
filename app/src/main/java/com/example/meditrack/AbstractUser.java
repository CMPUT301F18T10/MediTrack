package com.example.meditrack;

public abstract class AbstractUser {

    String id;

    // Maybe we will implement password later
    // String password;

    protected String getId() {
        return id;
    }

    public AbstractUser(String id) {
        this.id = id;
    }
}
