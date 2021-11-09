package com.company.toDoList.security;

import com.company.toDoList.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration //Difference btw @ConfigurationProperties
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource; // for connection with db

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception { // method for authentication
        auth.jdbcAuthentication()//method for JDBC-authentication, because i use database
                .dataSource(dataSource) //method for
                .passwordEncoder(new BCryptPasswordEncoder()) //to encode the password
                .usersByUsernameQuery(//request to search a users by his username
                        "select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery(//request to search authorities by username
                        "select username, role from users where username=?");
    }


    public void configure(HttpSecurity http) throws Exception { // method for authorization
        http.authorizeRequests()
                .anyRequest().authenticated()//every request must be authenticated, user must be login
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/create").hasRole(String.valueOf(Roles.ADMIN))
                .antMatchers(HttpMethod.DELETE, "/users/delete").hasRole(String.valueOf(Roles.ADMIN))
                .antMatchers(HttpMethod.POST, "/todos/create").hasRole(String.valueOf(Roles.MANAGER))
                .antMatchers(HttpMethod.GET, "/users/{userId}/todos", "/id/start", "/id/finish").hasRole(String.valueOf(Roles.EMPLOYEE))
                .antMatchers("/id/start", "/id/finish").hasRole(String.valueOf(Roles.ACCOUNTANT))
                .and().formLogin();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}