package com.br.controller;

import com.br.dto.AuthenticatedResponseDTO;
import com.br.dto.AuthRequest;
import com.br.entities.User;
import com.br.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity auth(@RequestBody @Valid AuthRequest authRequest){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.login(), authRequest.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new AuthenticatedResponseDTO(token));
        } catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage() + " Usuário ou senha inválidos");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body((HttpStatus.BAD_REQUEST.value()));
        }

    }

//    @CrossOrigin
//    @PostMapping
//    public ResponseEntity auth(@RequestBody @Valid AuthRequest authRequest){
//        try {
//            return ResponseEntity.ok(authService.loadUserByUsername(authRequest.login()));
//        } catch (AuthenticationException e){
//            throw new BadCredentialsException("Usuário ou senha inválidos");
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.badRequest().body((HttpStatus.BAD_REQUEST.value()));
//        }
//
//    }
}
