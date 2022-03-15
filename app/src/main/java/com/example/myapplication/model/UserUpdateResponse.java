package com.example.myapplication.model;

public class UserUpdateResponse {

    private String message;
    private Boolean success;
    private ApplicationUser data;

    public UserUpdateResponse(String message, Boolean success, ApplicationUser data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public UserUpdateResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ApplicationUser getUser() {
        return data;
    }

    public void setUser(ApplicationUser data) {
        this.data = data;
    }
}
