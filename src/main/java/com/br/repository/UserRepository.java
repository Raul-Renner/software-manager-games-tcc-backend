package com.br.repository;

import com.br.entities.Activity;
import com.br.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.projects p ON p.organization.id = u.organization.id " +
            "WHERE ((:organizationId) IS NULL OR u.organization.id IN (:organizationId)) " +
            "AND ((:projectId) IS NULL OR p.id IN (:projectId)) " +
            "AND ((:userId) IS NULL OR u.id IN (:userId)) ")
    Page<User> findAll(@Param("organizationId") Long organizationId,
                       @Param("projectId") Long projectId,
                       @Param("userId") Long userId,
                       Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.projects p ON p.organization.id = u.organization.id " +
            "LEFT JOIN u.activities a ON a.project.id = p.id " +
            "WHERE ((:organizationId) IS NULL OR u.organization.id IN (:organizationId)) " +
            "AND ((:projectId) IS NULL OR p.id IN (:projectId)) " +
            "AND ((:activityId) IS NULL OR a.id IN (:activityId)) ")
    Page<User> findAllUserBy(@Param("organizationId") Long organizationId,
                       @Param("projectId") Long projectId,
                       @Param("activityId") Long activityId,
                       Pageable pageable);

    UserDetails findByLogin(String login);


}
