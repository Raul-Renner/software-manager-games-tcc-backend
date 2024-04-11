package com.br.vo;

import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.validation.ValidOrganizationSaveVO;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ValidOrganizationSaveVO
public class OrganizationSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull(message = "O nome da organização é obrigatório.")
    @NotBlank(message = "O nome da organização é obrigatório.")
    private String name;

    @NotNull(message = "A descrição da organização é obrigatório.")
    @NotBlank(message = "A descrição da organização é obrigatório.")
    private String description;

    private List<ProjectSaveVO> projectSaveVOList;

    private List<UserSaveVO> userSaveVOList;


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
                        (List<User>) userSaveVOList.stream()
                        .map(UserSaveVO::toEntity).collect(Collectors.toSet()) : null)
                .projects(nonNull(projectSaveVOList) && !projectSaveVOList.isEmpty() ? (List<Project>) projectSaveVOList.stream()
                        .map(ProjectSaveVO::toEntity).collect(Collectors.toSet()) : null).build();
    }

}
