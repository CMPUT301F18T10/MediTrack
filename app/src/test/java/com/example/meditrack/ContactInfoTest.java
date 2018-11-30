package com.example.meditrack;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class ContactInfoTest {
    String email = "testEmail@ualberta.ca";
    String phoneNumber = "7808001557";
    ContactInfo contactInfo = new ContactInfo(email,phoneNumber);
    @Test
    public void testContactInfo(){
        Assert.assertTrue("Email is not equal", email.equals(contactInfo.getEmail()));
        Assert.assertTrue("Phone number is not equal", phoneNumber.equals(contactInfo.getPhoneNumber()));
        String newEmail = "newEmail@ualberta.ca";
        String newPhoneNumber = "7806554773";
        contactInfo.setEmail(newEmail);
        contactInfo.setPhoneNumber(newPhoneNumber);
        Assert.assertTrue(" new Email is not equal", newEmail.equals(contactInfo.getEmail()));
        Assert.assertTrue(" new Phone number is not equal", newPhoneNumber.equals(contactInfo.getPhoneNumber()));
    }
}
