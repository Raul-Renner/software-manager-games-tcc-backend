package com.br.service;

import com.br.entities.*;
import com.br.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.br.enums.ProfileEnum.ADMINISTRADOR;
import static io.vavr.control.Option.ofOptional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final UserService userService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(Organization organization) {
        try {
           var organizationCopy = organizationRepository.save(organization);

            userService.save(User.builder()
                            .userInformation(UserInformation.builder()
                                    .email(organizationCopy.getEmail())
                                    .name(organizationCopy.getName())
                                    .username(organizationCopy.getName())
                                    .build())
                            .login("username011" + organizationCopy.getId())
                            .password("password011" + organizationCopy.getId())
                            .organization(organizationCopy)
                            .profile(ADMINISTRADOR)

                    .build());

        }catch (Exception e){
            throw new RuntimeException("Erro ao criar sua organização" );

        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(Organization organization) {
        try {
            var organizationOld = findOrganizationById(organization.getId());

            organization.getProjects().clear();
            organization.getOwners().clear();

            organization.setOwners(organizationOld.getOwners());
            organization.setProjects(organization.getProjects());

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
