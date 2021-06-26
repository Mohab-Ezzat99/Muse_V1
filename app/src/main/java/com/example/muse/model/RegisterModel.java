package com.example.muse.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public RegisterModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
