package com.br.service;

import com.br.entities.ColumnBoard;
import com.br.entities.Project;
import com.br.repository.ProjectRepository;
import com.br.type.ActivityFilterType;
import com.br.type.ColumnBoardFilterType;
import com.br.type.ProjectFilterType;
import com.br.type.ProjectUserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ColumnBoardService columnBoardService;

    private final ActivityService activityService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(Project project) {
        try {
            var projectCopy = projectRepository.save(project);
            columnBoardService.createColumnStandard(ColumnBoard.builder().project(projectCopy).build());
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar novo projeto");
        }
    }
    public void processUpdate(Project project){
        var userIds = new ArrayList<Long>();
        if(nonNull(project.getMembers())){
            project.getMembers().forEach(mb -> userIds.add(mb.getId()));
        }
        var projectOld = findProjectById(project.getId());
        if(nonNull(projectOld.getMembers()) && !projectOld.getMembers().isEmpty()){
            var activities = activityService.findAllByProj(ActivityFilterType.builder()
                    .userIds(userIds)
                    .organizationId(project.getOrganization().getId())
                    .projectId(project.getId())
                    .build(), PageRequest.of(0, 9999, ASC, "id"));
            if(nonNull(activities)){
                activities.forEach(activity -> {
                    activity.setUser(null);
                    activityService.update(activity);
                });
            }
        }
        update(project);
    }
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(Project project) {
        try {
            projectRepository.save(project);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados do projeto");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        try {
            delete(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover projeto");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void delete(Long id){
        try {
            projectRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover projeto");
        }
    }

    @Transactional(readOnly = true)
    public Page<Project> findAll(ProjectFilterType filter, Pageable pageable) {
        return projectRepository.findAll(
                filter.getOrganizationId(),
                filter.getProjectIds(),
                filter.getIsFinished(),
                filter.getUserId(),
                pageable);
    }

    @Transactional(readOnly = true)
    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<Project> example) {
        return projectRepository.exists(example);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public  Boolean getMembersProject(Long userId, Long projectId) {
           return projectRepository.existMemberInProject(userId, projectId);
    }

    @Transactional(readOnly = true)
    public Page<Project> findProjectsByUserId(ProjectUserFilter filter, Pageable pageable) {
        return projectRepository.findProjectsByUserId(
                filter.getOrganizationId(),
                filter.getProjectIds(),
                filter.getUserId(),
                pageable);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processUpdateProjectsUser(Long userId, Long orgId, List<Long> projectIds) {
        try {
            var projectsDeleteUser = findProjectsByUserId(ProjectUserFilter.builder()
                            .userId(userId)
                            .projectIds(projectIds)
                            .organizationId(orgId).build(),
                            PageRequest.of(0, 9999, ASC, "id"));
            if(!projectsDeleteUser.isEmpty()){
                projectsDeleteUser.getContent().forEach(project -> {
                    projectRepository.deleteUserProject(userId, project.getId());
                });
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar projectos usuário: " + userId);
        }

    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemoveColumnBoard(Long id) {
        try {
            var columnBoard = columnBoardService.findById(id);
            if(nonNull(columnBoard.getActivities())){
                var columnTodo = columnBoardService.findByColumnBoard(ColumnBoardFilterType.builder()
                        .sectorActivity("TODO")
                        .projectId(columnBoard.getProject().getId()).build());

                columnBoard.getActivities().forEach(activity -> {
                    activity.setColumnBoard(columnTodo);
                    activity.setSectorActivity(columnTodo.getSectorActivity());
                    columnTodo.getActivities().add(activity);
                });
                columnBoardService.update(columnTodo);
                columnBoardService.processRemove(id);
            }else{
                columnBoardService.processRemove(id);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover projeto");
        }
    }


}
