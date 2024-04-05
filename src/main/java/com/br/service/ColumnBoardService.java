package com.br.service;
import com.br.dto.ActivityDepedentUpdate;
import com.br.entities.Activity;
import com.br.entities.ColumnBoard;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.fieldQueries.ActivityFieldQuery;
import com.br.repository.ColumnBoardRepository;
import com.br.type.ActivityFilterNoInType;
import com.br.type.ColumnBoardFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColumnBoardService {

    private final ColumnBoardRepository columnBoardRepository;

    private final ActivityService activityService;

    private final ActivityDependentService activityDependentService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(ColumnBoard columnBoard){
        columnBoard.setSectorActivity(columnBoard.getName().toUpperCase());
        columnBoardRepository.save(columnBoard);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(ColumnBoard columnBoard) {
        try {

            var columnBoardOld = findById(columnBoard.getId());
            if (nonNull(columnBoardOld.getActivities()) && !columnBoardOld.getActivities().isEmpty()){
                if (nonNull(columnBoard.getActivities()) && !columnBoard.getActivities().isEmpty()){
                    columnBoard.getActivities().forEach(activity -> {
                        if(!activityService.existBy(ActivityFieldQuery
                                .ID_ACTIVITY_ID_COLUMN.existBy(asList(activity.getId().toString(),
                                        activity.getColumnBoard().getId().toString())))){
                            activityService.update(activity);
                        }
                    });
                }
            }

            columnBoard.setSectorActivity(columnBoard.getName().toUpperCase());
            columnBoardRepository.save(columnBoard);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados do projeto" + e.getMessage());
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        try {
            delete(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover column board");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void delete(Long id){
        try {
            var columnBoard = findById(id);
            if(nonNull(columnBoard.getActivities()) && !columnBoard.getActivities().isEmpty()){
                var columnTodo = findByColumnBoard(ColumnBoardFilterType.builder()
                        .sectorActivity("TODO")
                        .projectId(columnBoard.getProject().getId()).build());
                columnBoard.getActivities().forEach(activity -> {
                    var activityNew = activityService.save(Activity.builder()
                            .title(activity.getTitle())
                            .description(activity.getDescription())
                            .identifier(activity.getIdentifier())
                            .tagsEnum(activity.getTagsEnum())
                            .statusPriorityEnum(activity.getStatusPriorityEnum())
                            .columnBoard(columnTodo)
                            .user(nonNull(activity.getUser()) ? activity.getUser() : null)
                            .usedTime(nonNull(activity.getUsedTime()) ? activity.getUsedTime() : "-")
                            .isFinished(activity.getIsFinished())
                            .isBlock(activity.getIsBlock())
                            .sectorActivity(columnTodo.getName().toUpperCase())
                            .estimatedTime(activity.getEstimatedTime())
                            .build());
                    if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                        activityDependentService.updateActivityDependent(ActivityDepedentUpdate.builder()
                                        .activityIdNew(activityNew)
                                        .activityIdOld(activity.getId())
                                        .activityDependentList(activity.getActivityDependentList())
                                        .build());
                    }

//                activityService.updateSectorCard(activity);
                });
            }
            columnBoardRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover projeto");
        }
    }

    @Transactional(readOnly = true)
    public List<ColumnBoard> findAll(Long projectId) {
        return columnBoardRepository.findAll(projectId);
    }


    @Transactional(readOnly = true)
    public ColumnBoard findById(Long projectId) {
        return columnBoardRepository.findById(projectId).orElse(null);
    }

    @Transactional(readOnly = true)
    public ColumnBoard findByColumnBoard(ColumnBoardFilterType columnBoardFilterType) {
        return columnBoardRepository.findByColumnBoard(
                columnBoardFilterType.getProjectId(),
                columnBoardFilterType.getColumnId(),
                columnBoardFilterType.getSectorActivity());
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void createColumnStandard(ColumnBoard columnBoard){
        save(ColumnBoard.builder()
                        .project(columnBoard.getProject())
                        .name("ToDo")
                        .sectorActivity("TO_DO")
                        .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Preparação")
                .sectorActivity("PREPARATION")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Integração")
                .sectorActivity("INTEGRATION")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Teste")
                .sectorActivity("TEST")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Feito")
                .sectorActivity("DONE")
                .build());
    }

}
