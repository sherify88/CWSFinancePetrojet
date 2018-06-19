package com.example.kesho.cwsfinancepetrojet.Model;

/**
 * Created by kesho on 2/16/2018.
 */

public class User {
    private String Name;
    private long Password;

    public User() {
    }

    public String getName() {
        return Name;
    }

    public long getPassword() {
        return Password;
    }

    public User(long password) {
        Password = password;
    }
}
