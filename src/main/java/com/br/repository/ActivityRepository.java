package com.br.repository;

import com.br.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.id = :activityId")
    Activity findByActivityId(@Param("activityId") Long activityId);

    Page<Activity> findAll(Pageable page);


    @Query("SELECT COUNT(a) = 0 FROM Activity a WHERE EXISTS( " +
            " SELECT ad FROM ActivityDependent ad WHERE ad.activityBranch.id = :activityBranchId " +
            " AND ad.activitySource = a.id AND a.sectorActivityEnum != 'DONE')")
    Boolean allDependenciesCompleted(Long activityBranchId);
}
