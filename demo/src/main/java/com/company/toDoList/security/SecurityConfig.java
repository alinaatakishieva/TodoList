package com.company.toDoList.security;

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

@Configuration //Difference btw @ConfigurationProperties?
@EnableWebSecurity // класс является классом настроек Spring Security.
public class SecurityConfig extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter - данный класс позволяет настроить всю систему секюрити и авторизации под свои нужды.

    @Autowired
    private DataSource dataSource; // for connection with db

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception { // method for authentication
        auth.jdbcAuthentication()//method for JDBC-authentication, because i use database
                .passwordEncoder(passwordEncoder()) //to encode the password
                .usersByUsernameQuery(//request to search a users by his username
                        "select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery(//request to search authorities by username
                        "select username, role from users where username=?");
    }

    protected void configure(HttpSecurity http) throws Exception { // method for authorization
        http
                .csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/create").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/delete").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/todos/create").hasRole("MANAGER")
                .antMatchers(HttpMethod.GET, "/users/{userId}/todos", "/id/start", "/id/finish").hasRole("EMPLOYEE")
                .antMatchers("/id/start", "/id/finish").hasRole("ACCOUNTANT")
                .and().formLogin();
    }
}