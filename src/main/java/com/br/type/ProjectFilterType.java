package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ProjectFilterType implements Serializable {
    private Long organizationId;

    private List<Long> projectIds;

    private Boolean isFinished;

    private Long userId;


}
