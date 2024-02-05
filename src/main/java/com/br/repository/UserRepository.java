package com.br.repository;

import com.br.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT us FROM User us "
//            + " LEFT JOIN us.projects p "
//            + " WHERE mb.id in (:idUser) and p.id IN (:projectId) ")
//    Page<User> existMemberInProject(@Param("idsUser") List<Long> idsUser,
//                                    @Param("projectId") Long projectId);


}
