package com.br.vo;

import com.br.entities.ActivityDependent;
import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.validation.ValidOrganization;
import com.br.validation.ValidOrganizationUpdateVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidOrganizationUpdateVO
public class OrganizationUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "O id da organização é obrigatório.")
    @ValidOrganization
    private Long id;

    @NotNull(message = "O nome da organização é obrigatório.")
    @NotBlank(message = "O nome da organização é obrigatório.")
    private String name;

    @NotNull(message = "A descrição da organização é obrigatório.")
    @NotBlank(message = "A descrição da organização é obrigatório.")
    private String description;

//    private List<ProjectSaveVO> projectSaveVOList;

    private List<Long> projectSaveVOList;


//    private List<UserSaveVO> userSaveVOList;

    private List<Long> userSaveVOList;


    @NotBlank(message = "Email é obrigátorio")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email Invalido")
    private String email;

    public Organization toEntity(){

        return Organization.builder()
                .id(id)
                .name(name)
                .description(description)
                .email(email)
                .owners(nonNull(userSaveVOList) && !userSaveVOList.isEmpty() ?
                        userSaveVOList.stream().map(id -> User.builder()
                                        .id(id).build())
                                .collect(Collectors.toList()) : new ArrayList<>())
                .projects(nonNull(projectSaveVOList) && !projectSaveVOList.isEmpty() ?
                        projectSaveVOList.stream().map(id -> Project.builder()
                                .id(id).build())
                                .collect(Collectors.toList()) : new ArrayList<>()).build();
    }

}
