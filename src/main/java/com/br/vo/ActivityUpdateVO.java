package com.br.vo;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.entities.ColumnBoard;
import com.br.enums.*;
import com.br.validation.ValidActivity;
import com.br.validation.ValidColumnBoard;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ActivityUpdateVO {

    private static final long serialVersionUID = 1L;

    @Id
    @ValidActivity
    private Long id;

    private String identifier;

    @NotBlank(message = "title is required")
    @NotNull(message = "Enter the title of the activity")
    private String title;

    @NotBlank(message = "description is required")
    @NotNull(message = "Enter the description of the activity")
    private String description;

    private String estimatedTime;

    private String usedTime;

    private String sectorActivity;

    private Boolean isFinished;

    private StatusPriorityEnum statusPriorityEnum;

    private TagsEnum tagsEnum;

    private Boolean isBlock;

    private List<Long> activityDependentIds;

    private UserSaveVO userSaveVO;

    @ValidColumnBoard
    private Long columnBoardId;

    public Activity toEntity(){
        var activity = Activity.builder()
                .id(id)
                .title(title)
                .identifier(identifier)
                .description(description)
                .estimatedTime(isNull(estimatedTime) ? "-" : estimatedTime)
                .isBlock(nonNull(isBlock) ? isBlock : false)
                .sectorActivity(nonNull(sectorActivity) ? sectorActivity : "TODO")
                .isFinished(nonNull(isFinished) ? isFinished : false)
                .activityDependentList(nonNull(activityDependentIds) ?
                        activityDependentIds.stream().map(id -> ActivityDependent.builder()
                                        .activitySource(id).build())
                                .collect(Collectors.toList()) :
                        new ArrayList<>())
                .tagsEnum(nonNull(tagsEnum) ? tagsEnum : INDEPENDENT)
                .statusPriorityEnum(nonNull(statusPriorityEnum) ? statusPriorityEnum : LOW)
                .user(nonNull(userSaveVO) ? userSaveVO.toEntity() : null)
                .usedTime(isNull(usedTime) ? "-" : usedTime)
                .columnBoard(nonNull(columnBoardId) ? ColumnBoard.builder().id(columnBoardId).build(): null)
                .build();

        return activity;
    }
}
