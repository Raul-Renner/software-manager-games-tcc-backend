package com.br.repository;

import com.br.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.id = :activityId")
    Activity findByActivityId(@Param("activityId") Long activityId);

    Page<Activity> findAll(Pageable page);


    @Query("SELECT COUNT(a) = 0 FROM Activity a WHERE EXISTS( " +
            " SELECT ad FROM ActivityDependent ad WHERE ad.activityBranch.id = :activityBranchId " +
            " AND ad.activitySource = a.id AND a.sectorActivity != 'DONE') ")
    Boolean allDependenciesCompleted(Long activityBranchId);

    @Query("SELECT DISTINCT a FROM Activity a " +
            "WHERE ((:projectId) IS NULL OR a.columnBoard.project.id IN :projectId) " +
            "AND ((:userIds) IS NULL OR a.user.id NOT IN (:userIds)) " +
            "AND ((:organizationId) IS NULL OR a.user.organization.id IN :organizationId) " +
            "AND (a.user.id IS NOT NULL)")
    Page<Activity> findAllByProj(@Param("organizationId") Long organizationId,
                          @Param("userIds") List<Long> userIds,
                          @Param("projectId") Long projectId,
                          Pageable pageable);

    @Query("SELECT DISTINCT a FROM Activity a " +
            "WHERE ((:columnId) IS NULL OR a.columnBoard.id IN :columnId) " +
            "AND ((:idsActivity) IS NULL OR a.id NOT IN (:idsActivity)) " +
            "AND ((:organizationId) IS NULL OR a.columnBoard.project.organization.id IN :organizationId) " )
    Page<Activity> findAllByNotInUsers(@Param("organizationId") Long organizationId,
                                       @Param("idsActivity") List<Long> idsActivity,
                                       @Param("columnId") Long columnId,
                                       Pageable pageable);

    @Query("SELECT DISTINCT a FROM Activity a " +
            "WHERE ((:activityId) IS NULL OR a.id IN :activityId) " +
            "AND ((:projectId) IS NULL OR a.columnBoard.project.id IN :projectId) " +
            "AND ((:organizationId) IS NULL OR a.columnBoard.project.organization.id IN :organizationId) ")
    Page<Activity> findAllBy(@Param("organizationId") Long organizationId,
                             @Param("projectId") Long projectId,
                             @Param("activityId") Long activityId,
                                 Pageable pageable);
}
