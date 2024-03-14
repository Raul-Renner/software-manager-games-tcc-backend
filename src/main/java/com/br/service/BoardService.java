package com.br.service;

import com.br.entities.Board;
import com.br.entities.ColumnBoard;
import com.br.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final ColumnBoardService columnBoardService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Board createBoard(){
        try {
            var board = boardRepository.save(Board.builder().columnsBoard(null).activities(null).build());
            columnBoardService.createColumnStandard(ColumnBoard.builder().board(board).build());
            board.setColumnsBoard(columnBoardService.findAll(board.getId()));
            return update(board);
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar o board do projeto");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Board update(Board board){
       return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<Board> example) {
        return boardRepository.exists(example);
    }

}
