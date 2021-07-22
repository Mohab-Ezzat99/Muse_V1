package com.example.musev1.model;

import com.google.gson.annotations.SerializedName;

public class AuthModel {
    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public AuthModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AuthModel(String email, String password) {
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
