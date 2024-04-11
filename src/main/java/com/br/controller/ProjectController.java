package com.br.controller;

import com.br.dto.ProjectUpdateDTO;
import com.br.entities.Project;
import com.br.service.ProjectService;
import com.br.type.ProjectFilterType;
import com.br.validation.ValidColumnBoard;
import com.br.validation.ValidProject;

import com.br.vo.ProjectSaveVO;
import com.br.vo.ProjectUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


import static org.springframework.data.domain.Sort.Direction.ASC;
import static reactor.core.publisher.Mono.just;

@Slf4j
@RestController
@RequestMapping("/api/org/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity save(@RequestBody @Valid ProjectSaveVO projectSaveVO){
        try {
            projectService.save(projectSaveVO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar projeto.");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity update(@PathVariable @ValidProject Long id, @RequestBody @Valid ProjectUpdateVO projectUpdateVO){
        try {
            if (!projectUpdateVO.getId().equals(id)) {
                throw new RuntimeException("Os ids do projeto repassado nao conferem.");
            }
            Project project = projectUpdateVO.toEntity();
            project.setId(id);
            projectService.processUpdate(project);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar dados do projeto.");
        }

    }

    @CrossOrigin
    @PutMapping("update-titleAndDesc/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity updateDescription(@PathVariable @ValidProject Long id, @RequestBody @Valid ProjectUpdateDTO projectUpdateDTO){
        try {
            if (!projectUpdateDTO.getId().equals(id)) {
                throw new RuntimeException("Os ids do projeto repassado nao conferem.");
            }
        //    Project project = projectUpdateVO.toEntity();
         //   project.setId(id);
            projectService.processUpdateDesc(projectUpdateDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar dados do projeto.");
        }

    }


    @CrossOrigin
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity delete(@PathVariable @Valid @ValidProject Long id) {
        try {
            projectService.processRemove(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao DELETAR projeto.");
        }
    }

    @CrossOrigin
    @GetMapping("/findAllBy")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR','ROLE_DESENVOLVEDOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity findAllBy(ProjectFilterType filter) {
        try {
            return ResponseEntity.ok(projectService.findAll(filter, PageRequest.of(0, 9999, ASC, "id")));
        } catch (Exception e){
            return new ResponseEntity<>("Um erro inesperado ocorreu ao buscar projeto.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete-board/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity deleteBoard(@PathVariable("id") @Valid @ValidColumnBoard Long id) {
        try {
            projectService.processRemoveColumnBoard(id);
            return ResponseEntity.ok("Coluna removida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao deletar coluna no board.");
        }
    }

}
