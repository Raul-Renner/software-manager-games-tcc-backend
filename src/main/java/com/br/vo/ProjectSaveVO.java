package com.br.vo;

import com.br.entities.*;
import com.br.validation.ValidOrganization;
import com.br.validation.ValidProjectSaveVO;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@ValidProjectSaveVO
public class ProjectSaveVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull(message = "O nome do projeto é obrigatório.")
    @NotBlank(message = "O nome do projeto é obrigatório.")
    private String name;

    @NotNull(message = "A descrição do projeto é obrigatório.")
    @NotBlank(message = "A descrição do projeto é obrigatório.")
    private String description;

    @ValidOrganization
    private Long organizationId;

    private List<Long> members;

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