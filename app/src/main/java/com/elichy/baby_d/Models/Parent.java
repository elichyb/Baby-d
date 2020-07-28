package com.elichy.baby_d.Models;

public class Parent {
    private String token;

    public Parent(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "token='" + token + '\'' +
                '}';
    }
}
