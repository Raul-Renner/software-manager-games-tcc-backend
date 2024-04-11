package com.br.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordRandom {

    public String generatePassword(){
        var chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ!@#$%&*";
        var password = "";
        for(int i = 0; i < 8; i++){
            var caracter = (int) (Math.random() * chars.length());
            password += chars.substring(caracter, caracter + 1);
        }

        return password;
    }
}
