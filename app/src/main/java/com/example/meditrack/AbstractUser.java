package com.example.meditrack;

import java.util.ArrayList;

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
