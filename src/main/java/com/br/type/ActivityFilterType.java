package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActivityFilterType {

    private Long organizationId;

    private List<Long> userIds;

    private Long projectId;
}
