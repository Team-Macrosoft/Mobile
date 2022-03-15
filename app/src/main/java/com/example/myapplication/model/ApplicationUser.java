package com.example.myapplication.model;

public class ApplicationUser {


    private Long id;
    private String firstName;
    private String surName;
    private String userName;
    private String email;


    public ApplicationUser(Long id, String firstName, String surName, String userName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.userName = userName;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }








}