package com.br.repository;

import com.br.entities.ColumnBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnBoardRepository extends JpaRepository<ColumnBoard, Long> {

    @Query("SELECT DISTINCT cb FROM ColumnBoard cb " +
            "WHERE ((:projectId) IS NULL OR cb.project.id IN :projectId) ")
    List<ColumnBoard> findAll(@Param("projectId") Long projectId);

    @Query("SELECT DISTINCT cb FROM ColumnBoard cb " +
            "WHERE ((:projectId) IS NULL OR cb.project.id IN :projectId) " +
            "AND ((:columnId) IS NULL OR cb.id IN (:columnId)) " +
            "AND ((:sectorActivity) IS NULL OR cb.sectorActivity IN (:sectorActivity))")
    ColumnBoard findByColumnBoard(@Param("projectId") Long projectId,
                                  @Param("columnId") Long columnId,
                                  @Param("sectorActivity") String sectorActivity);
}
