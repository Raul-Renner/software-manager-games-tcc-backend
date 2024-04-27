package com.br.controller;

import com.br.service.ColumnBoardService;
import com.br.validation.ValidColumnBoard;
import com.br.vo.ColumnBoardSaveVO;
import com.br.vo.ColumnBoardUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/org/project/board")
@RequiredArgsConstructor
public class ColumnBoardController {

    private final ColumnBoardService columnBoardService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity save(@RequestBody @Valid ColumnBoardSaveVO columnBoardSaveVO){
        try{
            columnBoardService.save(columnBoardSaveVO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar cadastrar coluna no board.");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity update(@PathVariable @ValidColumnBoard Long id, @RequestBody @Valid ColumnBoardUpdateVO columnBoardUpdateVO){
        try {
            if (!columnBoardUpdateVO.getId().equals(id)) {
                throw new RuntimeException("Os ids da column repassados nao conferem.");
            }
            var columnBoard = columnBoardUpdateVO.toEntity();
            columnBoard.setId(id);
            columnBoardService.update(columnBoard);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar atualizar informações da coluna no board.");
        }

    }

    @CrossOrigin
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_GERENTE', 'ROLE_LIDER_TECNICO')")
    public ResponseEntity delete(@PathVariable("id") @Valid @ValidColumnBoard Long id) {
        try {
            columnBoardService.processRemove(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao deletar coluna no board.");
        }
    }


}
