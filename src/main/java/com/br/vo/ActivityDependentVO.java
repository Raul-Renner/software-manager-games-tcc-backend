package com.br.vo;

import com.br.entities.ActivityDependent;
import jakarta.persistence.Id;
import lombok.*;


import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDependentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long activitySource;

    private String identifierSource;

    private ActivityVO activityBranch;

    private String identifierBranch;

    public ActivityDependent toEntity(){
        var activityDependent = ActivityDependent.builder()
                .id(id)
                .activitySource(activitySource)
                .identifierSource(identifierSource)
                .activityBranch(activityBranch.toEntity())
                .identifierBranch(identifierBranch).build();

        return activityDependent;

    }
}

