package com.springboot.security.jwt.springsecurityjwt.models;

/**
 * Created by sridharrajagopal on 5/29/22.
 */
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
