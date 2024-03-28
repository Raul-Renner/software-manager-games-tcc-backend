package com.br.security;

import com.br.repository.UserRepository;
import com.br.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.rmi.RemoteException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);


            if(nonNull(token)){
                var login = tokenService.validateToken(token);
                var user = userRepository.findByLogin(login);
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }

    }

    private String recoverToken(HttpServletRequest request){
        try {
            var authHeader = request.getHeader("Authorization");
            if(isNull(authHeader)) return null;
            return authHeader.replace("Bearer ", "");
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }


}
