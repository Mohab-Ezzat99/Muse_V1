package com.example.muse.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("token")
    private String token;
    @SerializedName("expiration")
    private String expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
