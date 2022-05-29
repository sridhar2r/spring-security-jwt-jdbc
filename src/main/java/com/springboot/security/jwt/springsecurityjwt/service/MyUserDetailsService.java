package com.springboot.security.jwt.springsecurityjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sridharrajagopal on 5/29/22.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String sql = "Select * from users where username=:userName";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userName", userName);
       /* return parameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
                String user = resultSet.getString("user");
                String password = resultSet.getString("password");
                return new User(user, password, new ArrayList<GrantedAuthority>());
            }
        });*/

        return parameterJdbcTemplate.queryForObject(sql, parameterSource, (ResultSet resultSet, int i) -> {
            String user = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new User(user, password, new ArrayList<>());
        });
    }
}
