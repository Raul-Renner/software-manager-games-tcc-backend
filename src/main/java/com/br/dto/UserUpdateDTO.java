package com.br.dto;
import com.br.entities.Project;
import com.br.enums.ProfileEnum;
import com.br.validation.ValidUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserUpdateDTO {

    @ValidUser
    private Long id;

    private List<Long> projectsIds;

    private ProfileEnum profile;

}
