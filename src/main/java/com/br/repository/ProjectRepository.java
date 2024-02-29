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

    @Query("SELECT DISTINCT p FROM Project p " +
            "LEFT JOIN p.members mb ON mb.organization.id = p.organization.id " +
            "WHERE ((:organizationId) IS NULL OR p.organization.id IN :organizationId) " +
            "AND ((:projectIds) IS NULL OR p.id IN :projectIds) " +
            "AND ((:userId) IS NULL OR mb.id IN (:userId)) " +
            "AND ((:isFinished) IS NULL OR p.isFinished IN :isFinished) ")
    Page<Project> findAll(@Param("organizationId") Long organizationId,
                          @Param("projectIds") List<Long> projectIds,
                          @Param("isFinished") Boolean isFinished,
                          @Param("userId") Long userId,
                          Pageable pageable);

    @Query("SELECT COUNT(*) > 0 FROM Project p "
            + "LEFT JOIN p.members mb "
            + "WHERE mb.id in (:idUser) and p.id IN (:projectId) ")
            Boolean existMemberInProject(@Param("idUser") Long idUser,
                        @Param("projectId") Long projectId);

    @Query("SELECT p FROM Project p "
            + "LEFT JOIN p.members mb "
            + "WHERE (COALESCE(:projectIds) IS NULL OR p.id NOT IN (:projectIds)) "
            + "AND (COALESCE(:idUser) IS NULL OR mb.id IN (:idUser)) "
            + "AND (COALESCE(:organizationId) IS NULL OR p.organization.id IN (:organizationId)) ")
    Page<Project> findProjectsByUserId(@Param("organizationId") Long organizationId,
                          @Param("projectIds") List<Long> projectIds,
                          @Param("idUser") Long idUser,
                          Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM project_members " +
            "WHERE id_user =:userId AND id_project=:projectId", nativeQuery = true)
    void deleteUserProject(@Param("userId") Long userId,@Param("projectId") Long projectId);

    @Query("SELECT p FROM Project p " +
            "LEFT JOIN p.members mb ON p.organization.id = mb.organization.id " +
            "WHERE ((:organizationId) IS NULL OR p.organization.id IN :organizationId) " +
            "AND ((:userId) IS NULL OR p.id IN :userId) ")
    Page<Project> findProjectIncludeUser(@Param("organizationId") Long organizationId,
                          @Param("userId") Boolean userId,
                          Pageable pageable);
}
