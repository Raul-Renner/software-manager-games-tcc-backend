package com.br.vo;

import com.br.entities.ColumnBoard;
import com.br.entities.Project;
import com.br.validation.ValidColumnBoard;
import com.br.validation.ValidProject;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnBoardUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ValidColumnBoard
    private Long id;

    @NotNull(message = "O nome da coluna é obrigatório.")
    @NotBlank(message = "O nome da coluna é obrigatório.")
    @Size(min = 3, max = 20, message = "O nome da coluna deve conter entre 3 e 20 caracteres.")
    private String name;

    private String sectorActivity;

    @ValidProject
    private Long projectId;

    public ColumnBoard toEntity() {
        return ColumnBoard.builder()
                .sectorActivity(sectorActivity)
                .project(Project.builder().id(projectId).build())
                .name(name).build();
    }
}