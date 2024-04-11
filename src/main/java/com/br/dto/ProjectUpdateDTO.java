package com.br.dto;

import com.br.enums.ProfileEnum;
import com.br.validation.ValidProject;
import com.br.validation.ValidUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProjectUpdateDTO {

    @ValidProject
    private Long id;

    private String name;

    private String description;

}