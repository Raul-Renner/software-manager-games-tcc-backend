package com.br.service;

import com.br.entities.Activity;
import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.vavr.control.Option.ofOptional;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(Organization organization) {
        try {
            organizationRepository.save(organization);
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar sua organização" );

        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(Organization organization) {
        try {
            organizationRepository.save(organization);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados da organização");
        }
    }
    @Transactional(readOnly = true)
    public Organization findBy(Example<Organization> example){
        return ofOptional(organizationRepository.findOne(example)).getOrNull();
    }
    @Transactional(readOnly = true)
    public Organization findOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<Organization> example) {
        return organizationRepository.exists(example);
    }


}
