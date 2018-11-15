package com.example.meditrack;

public abstract class AbstractUser implements ElasticsearchStorable{

    String userId;

    // Maybe we will implement password later
    // String password;

    protected String getUserId() {
        return userId;
    }

    public AbstractUser(String userId) {
        this.userId = userId;
    }
}
