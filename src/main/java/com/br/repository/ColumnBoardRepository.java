package com.br.repository;

import com.br.entities.ColumnBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnBoardRepository extends JpaRepository<ColumnBoard, Long> {

    @Query("SELECT DISTINCT cb FROM ColumnBoard cb " +
            "WHERE ((:boardId) IS NULL OR cb.board.id IN :boardId) ")
    List<ColumnBoard> findAll(@Param("boardId") Long boardId);
}
