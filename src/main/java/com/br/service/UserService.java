package com.br.service;

import com.br.entities.User;
import com.br.repository.UserRepository;
import com.br.type.UserFilterType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

import static com.br.fieldQueries.ProjectFieldQuery.ORGANIZATION_ID_PROJECT_ID;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final ProjectService projectService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(User user) {
        try {
            if(nonNull(user.getProjects()) && !user.getProjects().isEmpty()){
                user.getProjects().forEach(project -> {
                    if(!projectService.existBy(
                            ORGANIZATION_ID_PROJECT_ID.existBy(of(project.getId().toString(), user.getOrganization().getId().toString())))){
                        throw new RuntimeException("Um dos projetos informados não existe ou não faz parte dessa organização");
                    }
                });
            }

          var userCopy = userRepository.save(user);
          if(nonNull(userCopy.getProjects()) && !userCopy.getProjects().isEmpty()){
              userCopy.getProjects().forEach(projectAux -> {
                  var project = projectService.findProjectById(projectAux.getId());
                  project.getMembers().add(userCopy);
                  projectService.update(project);
              });
          }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        try {
            delete(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover usuário");
        }
    }
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void delete(Long id){
        try {
            userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao remover usuário");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processUpdate(User user){
        try {
            var projectFilterIdsDelete = new ArrayList<Long>();

            var userUpdate = userRepository.findById(user.getId()).orElse(null);
            if(isNull(userUpdate)){
                throw new RuntimeException("Erro ao ao editar usuário");
            }
            user.getUserInformation().setId(userUpdate.getUserInformation().getId());
            if(nonNull(user.getProjects()) && !user.getProjects().isEmpty()){
                user.getProjects().forEach(projectAux -> {
                    var project = projectService.findProjectById(projectAux.getId());
                    if(!projectService.getMembersProject(user.getId(), projectAux.getId())){
                        project.getMembers().add(user);
                        projectService.update(project);
                    }
                    projectFilterIdsDelete.add(projectAux.getId());
                });
            }

              projectService.processUpdateProjectsUser(user.getId(), user.getOrganization().getId(), projectFilterIdsDelete);


          userRepository.save(user);

        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados do usuário");
        }
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<User> example){
        return userRepository.exists(example);
    }

    @Transactional(readOnly = true)
    public User findBy(Example<User> example){
        return userRepository.findOne(example).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<User> findAllBy(UserFilterType filter){
        return userRepository.findAll(
                filter.getOrganizationId(),
                filter.getProjectId(),
                filter.getUserId(),
                PageRequest.of(0, 9999, ASC, "id"));
    }
}
