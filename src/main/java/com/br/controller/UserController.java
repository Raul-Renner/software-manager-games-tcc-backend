package com.br.controller;

import com.br.service.UserService;

import com.br.validation.ValidUser;
import com.br.vo.UserSaveVO;
import com.br.vo.UserUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

@Slf4j
@RestController
@RequestMapping("/api/org/project/colaborator")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @PostMapping
    public Mono<?> save(@RequestBody @Valid UserSaveVO userSaveVO){
        userService.save(userSaveVO.toEntity());
        return empty();
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public Mono<?> delete(@PathVariable("id") Long id) {
        try {
            userService.processRemove(id);
            return just(ok().build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário.");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    public Mono<?> update(@PathVariable @ValidUser Long id, @RequestBody @Valid UserUpdateVO userUpdateVO){

        if (!userUpdateVO.getId().equals(id)) {
            throw new RuntimeException("Os ids do usuário repassado nao conferem.");
        }
        var user = userUpdateVO.toEntity();
        user.setId(id);
        userService.processUpdate(user);
        return just(ok().build());
    }

}
