package com.ashypilov.taxiapp;

public class User {
    private String name;
    private String email;
    private String id;

    public User(String name, String email, String id, int avatarMockUpResource) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setName(String user) {
        this.name = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

}
