package com.company.toDoList.security.jwt;

import com.company.toDoList.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private  Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public static JwtUser fromUserEntityToJwtUser(UserEntity userEntity){
        JwtUser user = new JwtUser();
        user.username = userEntity.getUsername();
        user.password = userEntity.getPassword();
        user.authorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRoles().toString()));
        return user;
    }

    @Override
    //Granted authority is for permissions
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
