package com.br.controller;

import com.br.entities.Project;
import com.br.service.ProjectService;
import com.br.type.ProjectFilterType;
import com.br.validation.ValidProject;

import com.br.vo.ProjectSaveVO;
import com.br.vo.ProjectUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

@Slf4j
@RestController
@RequestMapping("/api/org/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @CrossOrigin
    @PostMapping
    public Mono<?> save(@RequestBody @Valid ProjectSaveVO projectSaveVO){
        projectService.save(projectSaveVO.toEntity());
        return empty();
    }

    @CrossOrigin
    @PutMapping("{id}")
    public Mono<?> update(@PathVariable @ValidProject Long id, @RequestBody @Valid ProjectUpdateVO projectUpdateVO){

            if (!projectUpdateVO.getId().equals(id)) {
                throw new RuntimeException("Os ids do projeto repassado nao conferem.");
            }
            Project project = projectUpdateVO.toEntity();
            project.setId(id);
            projectService.update(project);
            return just(ok().build());
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public Mono<?> delete(@PathVariable("id") Long id) {
        try {
            projectService.processRemove(id);
            return just(ok().build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar projeto.");
        }
    }

    @CrossOrigin
    @GetMapping("/findAllBy")
    public Mono<?> findAllBy(ProjectFilterType filter) {
        return just(projectService.findAll(filter, PageRequest.of(0, 9999, ASC, "id")));
    }

}
