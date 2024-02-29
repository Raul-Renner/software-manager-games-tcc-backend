package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserFilterType implements Serializable {
    private Long organizationId;

    private Long projectId;

    private Long userId;
}
