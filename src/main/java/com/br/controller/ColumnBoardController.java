package com.br.controller;

import com.br.entities.ColumnBoard;
import com.br.service.ColumnBoardService;
import com.br.validation.ValidProject;
import com.br.vo.ColumnBoardSaveVO;
import com.br.vo.ColumnBoardUpdateVO;
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
@RequestMapping("/api/org/project/board")
@RequiredArgsConstructor
public class ColumnBoardController {

    private final ColumnBoardService columnBoardService;

    @CrossOrigin
    @PostMapping
    public Mono<?> save(@RequestBody @Valid ColumnBoardSaveVO columnBoardSaveVO){
        columnBoardService.save(columnBoardSaveVO.toEntity());
        return empty();
    }

    @CrossOrigin
    @PutMapping("{id}")
    public Mono<?> update(@PathVariable @ValidProject Long id, @RequestBody @Valid ColumnBoardUpdateVO columnBoardUpdateVO){

        if (!columnBoardUpdateVO.getId().equals(id)) {
            throw new RuntimeException("Os ids da column repassados nao conferem.");
        }
        ColumnBoard columnBoard = columnBoardUpdateVO.toEntity();
        columnBoard.setId(id);
        columnBoardService.update(columnBoard);
        return just(ok().build());
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public Mono<?> delete(@PathVariable("id") Long id) {
        try {
            columnBoardService.processRemove(id);
            return just(ok().build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar projeto.");
        }
    }


}
