package com.br.controller;

import com.br.entities.Activity;
import com.br.vo.ActivitySaveVO;
import com.br.service.ActivityService;
import com.br.vo.ActivityUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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

    private final ActivityService activityService;

    @CrossOrigin
    @PostMapping
    public Mono<?> save(@RequestBody ActivitySaveVO activityVO){
        activityService.save(activityVO.toEntity());
        return empty();
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public Mono<?> delete(@PathVariable("id") Long id) {
        try {
            activityService.processRemove(id);
            return just(ok().build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar atividade.");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    public Mono<?> update(@PathVariable("id") Long id, @RequestBody ActivityUpdateVO activitySaveVO){
        try {
            Activity activity = activitySaveVO.toEntity();
            activity.setId(id);
            activityService.update(activity);
            return just(ok().build());
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao editar atividade.");
        }
    }

    @CrossOrigin
    @PutMapping("/sector-card/{id}")
    public Mono<?> updateSectorCard(@PathVariable("id") Long id, @RequestBody ActivityUpdateVO activitySaveVO){
        try {
            Activity activity = activitySaveVO.toEntity();
            activity.setId(id);
            activityService.updateSectorCard(activity);
            return just(ok().build());
        }catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao editar atividade.");
        }
    }


    @CrossOrigin
    @GetMapping
    public Mono<?> findAll(){
        try {
            return just(activityService.findAll(PageRequest.of(0, 9999, ASC, "id")));
        }catch (Exception e){
            throw new RuntimeException("Erro ao listar atividades.");
        }
    }
}
