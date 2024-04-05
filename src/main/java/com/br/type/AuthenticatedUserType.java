package com.br.type;

import com.br.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@ToString
public class AuthenticatedUserType extends UsernamePasswordAuthenticationToken implements Serializable {

    private Long id;

    private String username;

    private User user;

    private String token;


    public AuthenticatedUserType(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AuthenticatedUserType(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
