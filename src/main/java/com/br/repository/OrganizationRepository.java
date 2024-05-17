package com.br.repository;

import com.br.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {


    @Query("SELECT o FROM Organization o WHERE o.id = :orgId")
    Organization findOrganizationById(@Param("orgId") Long orgId);}
