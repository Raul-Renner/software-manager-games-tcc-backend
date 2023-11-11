package com.br.type;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
public class ActivityDependentFilterType implements Serializable {

    private List<Long> idsNotIn;
    private Long activitySource;
    private Long activityBranch;

    public List<Long> getIdsNotIn() {
        return isEmpty(idsNotIn) ? null : idsNotIn;
    }

}
