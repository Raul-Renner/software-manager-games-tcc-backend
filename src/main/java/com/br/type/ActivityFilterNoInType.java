package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ActivityFilterNoInType {

    private Long organizationId;

    private List<Long> idsActivity;

    private Long columnId;
}
