package com.br.controller;

import com.br.type.ActivityFilterType;
import com.br.validation.ValidActivity;
import com.br.vo.ActivitySaveVO;
import com.br.service.ActivityService;
import com.br.vo.ActivityUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;



@Slf4j
@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {


    private final ActivityService activityService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity save(@RequestBody @Valid ActivitySaveVO activityVO){
        try {
            activityService.save(activityVO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity update(@PathVariable @ValidActivity Long id, @RequestBody @Valid ActivityUpdateVO activitySaveVO){
        try {
            if (!activitySaveVO.getId().equals(id)) {
                throw new RuntimeException("Os ids informados nao são iguais.");
            }
            var activity = activitySaveVO.toEntity();
            activity.setId(id);
            activityService.update(activity);
            return ResponseEntity.ok(HttpStatus.OK);
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
            var activity = activitySaveVO.toEntity();
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

    @CrossOrigin
    @PutMapping("/assign-user-activity/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO', 'ROLE_DESENVOLVEDOR')")
    public ResponseEntity assignUserActivity(@PathVariable("id") Long id, @RequestBody ActivityUpdateVO activitySaveVO){
        try {
            var activity = activitySaveVO.toEntity();
            activity.setId(id);

            return new ResponseEntity (activityService.assignUserInActivity(activity), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar atualizar da atividade");
        }
    }
}
