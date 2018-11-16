package com.example.meditrack;

public abstract class AbstractUser implements ElasticsearchStorable{

    String id;

    // Maybe we will implement password later
    // String password;

    protected String getUserId() {
        return id;
    }

    public AbstractUser(String id) {
        this.id = id;
    }
}
