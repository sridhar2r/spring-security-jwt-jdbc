package com.springboot.security.jwt.springsecurityjwt.filters;

import com.springboot.security.jwt.springsecurityjwt.service.MyUserDetailsService;
import com.springboot.security.jwt.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sridharrajagopal on 5/29/22.
 */
@Service
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String jwt = null;
        String userName = null;
        if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
            userName = jwtUtil.extractUserName(jwt);
        }
        if(!StringUtils.isEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(jwtUtil.validateToken(jwt, userDetails) ) {
                UsernamePasswordAuthenticationToken userNamePwdToken =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                userNamePwdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userNamePwdToken);
            }
        }
        filterChain.doFilter(request, httpServletResponse);
    }
}
