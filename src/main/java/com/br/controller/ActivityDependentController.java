package com.br.controller;

import com.br.service.ActivityDependentService;
import com.br.type.ActivityDependentFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Mono.just;

@Slf4j
@RestController
@RequestMapping("/api/activity-dependent")
@RequiredArgsConstructor
public class ActivityDependentController {
    public final ActivityDependentService activityDependentService;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public Mono<?> findAllBy(ActivityDependentFilterType filter) {
        return just(activityDependentService.findAll(filter, PageRequest.of(0, 9999, ASC, "id")));
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public Mono<?> delete(@PathVariable("id") Long id) {
        try {
            activityDependentService.processRemove(id);
            return just(ok().build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar atividade.");
        }
    }

}
