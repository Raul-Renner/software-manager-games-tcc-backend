package com.br.vo;

import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.validation.ValidOrganization;
import com.br.validation.ValidProject;
import com.br.validation.ValidProjectUpdateVO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidProjectUpdateVO
public class ProjectUpdateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ValidProject
    private Long id;

    @NotNull(message = "O nome do projeto é obrigatório.")
    @NotBlank(message = "O nome do projeto é obrigatório.")
    private String name;

    @NotNull(message = "A descrição do projeto é obrigatório.")
    @NotBlank(message = "A descrição do projeto é obrigatório.")
    private String description;

    @ValidOrganization
    private Long organizationId;

    private List<@Valid Long> members;

    private List<@Valid ActivitySaveVO> activities;

    private Boolean isFinished;

    public Project toEntity() {
        return Project.builder()
                .id(id)
                .name(name)
                .isFinished(nonNull(isFinished) ? isFinished : false)
                .description(description)
                .organization(Organization.builder().id(organizationId).build())
                .members(nonNull(members) && !members.isEmpty() ?
                        members.stream().map(id -> User.builder().id(id).build())
                                .collect(Collectors.toList()) : new ArrayList<>()).build();

    }
}