package com.springboot.security.jwt.springsecurityjwt;

import com.springboot.security.jwt.springsecurityjwt.models.AuthenticationRequest;
import com.springboot.security.jwt.springsecurityjwt.models.AuthenticationResponse;
import com.springboot.security.jwt.springsecurityjwt.service.MyUserDetailsService;
import com.springboot.security.jwt.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sridharrajagopal on 5/29/22.
 */
@RestController
public class HelloResource {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password ", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtUtil.createToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}

