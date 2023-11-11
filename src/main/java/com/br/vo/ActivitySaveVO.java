package com.br.vo;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.enums.*;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.br.enums.SectorActivityEnum.TO_DO;
import static com.br.enums.StatusPriorityEnum.LOW;
import static com.br.enums.TagsEnum.INDEPENDENT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySaveVO {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private static Integer identifier = 1;

    @NotBlank(message = "title is required")
    @NotNull(message = "Enter the title of the activity")
    private String title;

    @NotBlank(message = "description is required")
    @NotNull(message = "Enter the description of the activity")
    private String description;

    private String estimatedTime;

    private SectorActivityEnum sectorActivityEnum;

    private StatusActivityEnum statusActivityEnum;

    private StatusPriorityEnum statusPriorityEnum;

    private TagsEnum tagsEnum;

    private Boolean isBlock;

    private List<Long> activityDependentIds;

    private String colorCard;

    public Activity toEntity(){
        var activity = Activity.builder()
                .id(id)
                .title(title)
                .identifier("#" + (identifier++))
                .description(description)
                .estimatedTime(isNull(estimatedTime) ? "-" : estimatedTime)
                .isBlock(isBlock)
                .sectorActivityEnum(nonNull(sectorActivityEnum) ? sectorActivityEnum : TO_DO)
                .statusActivityEnum(nonNull(statusActivityEnum) ? statusActivityEnum : StatusActivityEnum.TO_DO)
                .activityDependentList(nonNull(activityDependentIds) ?
                        activityDependentIds.stream().map(id -> ActivityDependent.builder()
                                        .activitySource(id).build())
                                .collect(Collectors.toList()) :
                        new ArrayList<>())
                .tagsEnum(nonNull(tagsEnum) ? tagsEnum : INDEPENDENT)
                .statusPriorityEnum(nonNull(statusPriorityEnum) ? statusPriorityEnum : LOW)
                .colorCard(nonNull(colorCard)  ? colorCard : "#FFFFFF")
                .build();

        return activity;
    }
}
