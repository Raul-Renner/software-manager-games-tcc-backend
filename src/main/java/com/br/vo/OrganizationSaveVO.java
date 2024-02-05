package com.br.vo;

import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.validation.ValidOrganizationSaveVO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private String login;

    private String password;

    public Organization toEntity(){

        return Organization.builder()
                .id(id)
                .name(name)
                .description(description)
                .login(login)
                .password(password)
                .owners(nonNull(userSaveVOList) && !userSaveVOList.isEmpty() ? (List<User>) userSaveVOList.stream()
                        .map(UserSaveVO::toEntity).collect(Collectors.toSet()) : null)
                .projects(nonNull(projectSaveVOList) && !projectSaveVOList.isEmpty() ? (List<Project>) projectSaveVOList.stream()
                        .map(ProjectSaveVO::toEntity).collect(Collectors.toSet()) : null).build();
    }

}
