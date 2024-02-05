package com.br.repository;

import com.br.entities.ActivityDependent;
import com.br.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p " +
            "WHERE (COALESCE(:organizationId) IS NULL OR p.organization.id IN :organizationId) " +
            "OR (COALESCE(:projectId) IS NULL OR p.id IN :projectId)")
    Page<Project> findAll(@Param("organizationId") Long organizationId,
                                    @Param("projectId") Long projectId,
                                    Pageable pageable);

    @Query("SELECT COUNT(*) > 0 FROM Project p "
            + "LEFT JOIN p.members mb "
            + "WHERE mb.id in (:idUser) and p.id IN (:projectId) ")
            Boolean existMemberInProject(@Param("idUser") Long idUser,
                        @Param("projectId") Long projectId);


}
