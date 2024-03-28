package com.br.type;

import com.br.validation.ValidOrganization;
import com.br.validation.ValidProject;
import com.br.validation.ValidUser;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class UserFilterType implements Serializable {
    private Long organizationId;

    private Long projectId;

    private Long userId;
}
