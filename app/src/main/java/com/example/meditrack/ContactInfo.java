package com.example.meditrack;

public class ContactInfo {

    private String email;
    private String phoneNumber;

    public ContactInfo(String email,String phoneNumber){
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
