package com.br.service;


import com.br.entities.Project;
import com.br.repository.ProjectRepository;
import com.br.type.ProjectFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(Project project) {
        try {
            projectRepository.save(project);
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar novo projeto");
        }
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
                filter.getProjectId(),
                filter.getOrganizationId(),
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


}
