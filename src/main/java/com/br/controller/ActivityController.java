package com.br.controller;

import com.br.entities.Activity;
import com.br.type.ActivityFilterType;
import com.br.type.ProjectFilterType;
import com.br.validation.ValidActivity;
import com.br.vo.ActivitySaveVO;
import com.br.service.ActivityService;
import com.br.vo.ActivityUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;


@Slf4j
@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {


    @Autowired
    private final ActivityService activityService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity save(@RequestBody @Valid ActivitySaveVO activityVO){
        try {
            activityService.save(activityVO.toEntity());
            return ResponseEntity.ok("Atividade cadastrada com sucesso!");
        } catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar salvar atividade");
        }
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity delete(@PathVariable("id") @ValidActivity Long id) {
        try {
            activityService.processRemove(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar remover atividade");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity update(@PathVariable("id") @ValidActivity Long id, @RequestBody @Valid ActivityUpdateVO activitySaveVO){
        try {
            Activity activity = activitySaveVO.toEntity();
            activity.setId(id);
            activityService.update(activity);
            return ResponseEntity.ok("Atividade atualizada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar atualizar informações da atividade");
        }
    }

    @CrossOrigin
    @PutMapping("/sector-card/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity updateSectorCard(@PathVariable("id") Long id, @RequestBody ActivityUpdateVO activitySaveVO){
        try {
            Activity activity = activitySaveVO.toEntity();
            activity.setId(id);
            activityService.updateSectorCard(activity);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar atualizar da atividade");
        }
    }


    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity findAll(){
        try {
            return ResponseEntity.ok(activityService.findAll(PageRequest.of(0, 9999, ASC, "id")));
        }catch (Exception e){
            throw new RuntimeException("Erro ao listar atividades.");
        }
    }

    @CrossOrigin
    @GetMapping("/findAllBy")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity findAllBy(ActivityFilterType filter) {
        try {
            return ResponseEntity.ok(activityService.findAllBy(filter, PageRequest.of(0, 9999, ASC, "id")));
        } catch (Exception e){
            return new ResponseEntity<>("Um erro inesperado ocorreu listar atividades.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
