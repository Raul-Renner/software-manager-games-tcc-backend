package com.br.dto;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class ActivityDepedentUpdate {
    private List<ActivityDependent> activityDependentList;

    private Long activityIdOld;

    private Activity activityIdNew;
}
