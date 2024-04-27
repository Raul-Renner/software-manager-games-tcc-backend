package com.br.controller;

import com.br.dto.UserUpdateDTO;
import com.br.service.UserService;

import com.br.type.UserFilterPerActivityType;
import com.br.type.UserFilterType;
import com.br.validation.ValidUser;
import com.br.vo.UserSaveVO;
import com.br.vo.UserUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.br.fieldQueries.UserFieldQuery.valueOf;
@Slf4j
@RestController
@RequestMapping("/api/org/colaborator")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity save(@RequestBody @Valid UserSaveVO userSaveVO) {
        try {
            userService.save(userSaveVO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao cadastrar novo usuário.");
        }
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity delete(@PathVariable @Valid @ValidUser Long id) {
        try {
            //"Usuário com o id " + id + " removido com sucesso!"
            userService.processRemove(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity update(@PathVariable("id") @Valid @ValidUser Long id, @RequestBody @Valid UserUpdateVO userUpdateVO){
        try {
            if (!userUpdateVO.getId().equals(id)) {
                throw new RuntimeException("Os ids do usuário repassado nao conferem.");
            }
            var user = userUpdateVO.toEntity();
            user.setId(id);
            userService.processUpdate(user);
            return ResponseEntity.ok(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PutMapping("update-user-function/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity updateProjectAndFunctionUser(@PathVariable @ValidUser Long id, @RequestBody @Valid UserUpdateDTO userUpdateDTO){
        try {
            if (!userUpdateDTO.getId().equals(id)) {
                throw new RuntimeException("Os ids do usuário repassado nao conferem.");
            }
            userService.processUpdateProjectAndFunctionUser(userUpdateDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity findAllBy(UserFilterType filter){
        try{
            log.info("Searching all profiles");
            return ResponseEntity.ok(userService.findAllBy(filter));
        } catch (Exception e) {
            return new ResponseEntity<>("Um erro inesperado ocorreu ao buscar todos os usuários.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @GetMapping("/find-by")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity findBy(@RequestParam String field, @RequestParam List<String> values) {
        try {
            log.info("Searching user by field: {} and value {}.", field, values);
            return ResponseEntity.ok(userService.findBy(valueOf(field).findBy(values)));
        } catch (Exception e){
            return new ResponseEntity<>("Um erro inesperado ocorreu ao buscar o usuário.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @GetMapping("/find-by-activity")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity findAllUserByActivity(UserFilterPerActivityType filter){
        try{
            log.info("Searching all profiles");
            return ResponseEntity.ok(userService.findAllUserByActivity(filter));
        } catch (Exception e) {
            return new ResponseEntity<>("Um erro inesperado ocorreu ao buscar todos os usuários.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
