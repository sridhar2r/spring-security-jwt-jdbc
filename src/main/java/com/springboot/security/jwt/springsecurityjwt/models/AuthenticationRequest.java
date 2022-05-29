package com.springboot.security.jwt.springsecurityjwt.models;

/**
 * Created by sridharrajagopal on 5/29/22.
 */
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
