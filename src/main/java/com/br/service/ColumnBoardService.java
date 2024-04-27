package com.br.service;

import com.br.entities.ColumnBoard;

import com.br.fieldQueries.ActivityFieldQuery;
import com.br.repository.ColumnBoardRepository;
import com.br.type.ColumnBoardFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColumnBoardService {

    private final ColumnBoardRepository columnBoardRepository;

    private final ActivityService activityService;

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
                    activity.setColumnBoard(columnTodo);
                    activity.setSectorActivity(columnTodo.getName().toUpperCase());
                    activityService.updateSectorCard(activity);
                    columnBoard.setActivities(null);
                    update(columnBoard);
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
                        .phase("INITIAL")
                        .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Preparação")
                .sectorActivity("PREPARATION")
                .phase("INITIAL")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Integração")
                .sectorActivity("INTEGRATION")
                .phase("FINALIZATION")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Teste")
                .sectorActivity("TEST")
                .phase("FINALIZATION")
                .build());

        save(ColumnBoard.builder()
                .project(columnBoard.getProject())
                .name("Feito")
                .sectorActivity("DONE")
                .phase("FINALIZATION")
                .build());
    }

}
