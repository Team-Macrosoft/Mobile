package com.example.myapplication.model;

public class LoginResponse {


    private String token;
    private ApplicationUser response;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ApplicationUser getResponse() {
        return response;
    }

    public void setResponse(ApplicationUser response) {
        this.response = response;
    }

    public LoginResponse(String token, ApplicationUser response) {
        this.token = token;
        this.response = response;
    }


}
