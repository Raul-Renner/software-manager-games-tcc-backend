package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserFilterPerActivityType implements Serializable {
    private Long organizationId;

    private Long projectId;

    private Long activityId;
}
