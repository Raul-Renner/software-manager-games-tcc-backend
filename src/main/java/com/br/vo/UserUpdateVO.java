package com.br.vo;

import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.entities.UserInformation;
import com.br.enums.ProfileEnum;
import com.br.validation.ValidOrganization;
import com.br.validation.ValidProject;
import com.br.validation.ValidUser;
import com.br.validation.ValidUserToUpdateVO;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidUserToUpdateVO
public class UserUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ValidUser
    private Long id;

    @NotBlank(message = "O Login é obrigatório")
    @Size(min = 3, max = 20, message = "O nome de usuario deve conter entre 3 e 20 caracteres.")
    private String login;

//    @NotNull(message = "A senha é obrigatório.")
//    @NotBlank(message = "A senha é obrigatório.")
//    @Size(min = 8, max = 20, message = "A senha de usuario deve conter entre 8 e 20 caracteres.")
//    private String password;

    @NotNull(message = "O nome é obrigatório.")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 20, message = "O nome de usuario deve conter entre 3 e 20 caracteres.")
    private String name;

    @NotNull(message = "O sobrenome é obrigatório.")
    @NotBlank(message = "O sobrenome é obrigatório.")
    @Size(min = 3, max = 20, message = "O sobrenome de usuario deve conter entre 3 e 20 caracteres.")
    private String username;

    @NotBlank(message = "Email é obrigátorio")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email Invalido")
    private String email;

    private ProfileEnum profileEnum;

    @ValidOrganization
    private Long organizationId;

    private List<@ValidProject Long> projects;


    public User toEntity(){
        var user = User.builder()
                .id(id)
                .login(login)
                .profile(profileEnum)
                .userInformation(UserInformation.builder()
                        .name(name)
                        .username(username)
                        .email(email).build())
                .organization(Organization.builder().id(organizationId).build()).build();

                if(nonNull(projects)){
                    user.setProjects(projects.stream().map(id ->
                            Project.builder().id(id).build()).collect(Collectors.toList()));
                }
        return user;
    }

}
