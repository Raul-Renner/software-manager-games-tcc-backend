package com.br.repository;

import com.br.entities.ActivityDependent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDependentRepository extends JpaRepository<ActivityDependent, Long> {

    @Query("SELECT DISTINCT ad FROM ActivityDependent ad " +
            "WHERE ((:idsNotIn) IS NULL OR ad.id NOT IN :idsNotIn) " +
            "AND (COALESCE(:activitySource) IS NULL OR ad.activitySource IN :activitySource) " +
            "AND (COALESCE(:activityBranch) IS NULL OR ad.activityBranch.id IN :activityBranch)")
    Page<ActivityDependent> findAll(@Param("idsNotIn") List<Long> idsNotIn,
                                    @Param("activitySource") Long activitySource,
                                    @Param("activityBranch") Long activityBranch,
                                    Pageable pageable);



}
