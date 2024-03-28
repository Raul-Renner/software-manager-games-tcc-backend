package com.br.service;
import com.br.entities.ColumnBoard;
import com.br.repository.ColumnBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColumnBoardService {

    private final ColumnBoardRepository columnBoardRepository;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(ColumnBoard columnBoard){
        columnBoardRepository.save(columnBoard);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(ColumnBoard columnBoard) {
        try {
            columnBoardRepository.save(columnBoard);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados do projeto");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        try {
            delete(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover column board");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void delete(Long id){
        try {
            columnBoardRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover projeto");
        }
    }

    @Transactional(readOnly = true)
    public List<ColumnBoard> findAll(Long boardId) {
        return columnBoardRepository.findAll(boardId);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void createColumnStandard(ColumnBoard columnBoard){
        save(ColumnBoard.builder()
                        .board(columnBoard.getBoard())
                        .name("ToDo")
                        .description("TO_DO")
                        .build());

        save(ColumnBoard.builder()
                .board(columnBoard.getBoard())
                .name("Preparação")
                .description("PREPARATION")
                .build());

        save(ColumnBoard.builder()
                .board(columnBoard.getBoard())
                .name("Integração")
                .description("INTEGRATION")
                .build());

        save(ColumnBoard.builder()
                .board(columnBoard.getBoard())
                .name("Teste")
                .description("TEST")
                .build());

        save(ColumnBoard.builder()
                .board(columnBoard.getBoard())
                .name("Feito")
                .description("DONE")
                .build());
    }

}
