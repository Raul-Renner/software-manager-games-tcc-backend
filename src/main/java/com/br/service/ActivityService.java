package com.br.service;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.enums.StatusPriorityEnum;
import com.br.repository.ActivityRepository;
import com.br.type.ActivityDependentFilterType;
import com.br.type.ActivityFilterType;
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

import static com.br.enums.TagsEnum.DEPENDENT;
import static com.br.enums.TagsEnum.INDEPENDENT;
import static com.br.fieldQueries.ActivityDependentFieldQuery.ID_ACTIVITY_BRANCH;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityDependentService activityDependentService;


    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Activity save(Activity activity) {
        try {
            if(nonNull(activity.getTagsEnum())){
                switch (activity.getTagsEnum()){
                    case URGENT:
                        activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                        }else{
                            activity.setTagsEnum(INDEPENDENT);
                        }
                        break;
                }
            }
            if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                if(activity.getSectorActivity().equals("FEITO")){
                    activity.setIsBlock(false);

                }else{
                    activity.setIsBlock(true);
                }
                activity.setTagsEnum(DEPENDENT);
                var activityCopy = activityRepository.save(activity);
                for(ActivityDependent activityDependent : activity.getActivityDependentList()){
                    var activitySource = activityRepository.findByActivityId(activityDependent.getActivitySource());
                        activityDependentService.saveActivityDependent(ActivityDependent.builder()
                                .activitySource(activitySource.getId())
                                .identifierSource(activitySource.getIdentifier())
                                .activityBranch(activityCopy)
                                .identifierBranch(activityCopy.getIdentifier())
                                .build());
                }

                if(activityRepository.allDependenciesCompleted(activityCopy.getId())){
                    activityCopy.setIsBlock(false);
                    activityCopy.setTagsEnum(INDEPENDENT);
                   return activityRepository.save(activityCopy);
                }
            }else{
                activity.setIsBlock(false);
            }
            return activityRepository.save(activity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processo de salvamento da atividade" );
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(Activity activity) {
        try {
            if(nonNull(activity.getTagsEnum())){
                switch (activity.getTagsEnum()){
                    case URGENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        }
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){


                        }else{
                            var activiyOld = findById(activity.getId());
                            activity.setTagsEnum(activiyOld.getTagsEnum());
                        }
                        break;
                    case INDEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){

                            if(!activityRepository.allDependenciesCompleted(activity.getId())){
                                activity.setTagsEnum(DEPENDENT);
                            }
                        }
                        break;
                    case IMPROVEMENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setTagsEnum(DEPENDENT);
                        }
                        break;
                }
            }
            List<Long> activitiesIdsDelete = new ArrayList<>();
            //pegar toda a lista de atividades que atividade é dependente
            var activities = activityDependentService.findAll(ActivityDependentFilterType
                    .builder().activityBranch(activity.getId()).build(), PageRequest.of(0, 9999, ASC, "id"));
            if(nonNull(activities) && !activities.isEmpty()){
                if(nonNull(activity.getActivityDependentList()) &&
                        !activity.getActivityDependentList().isEmpty()){
                    activity.getActivityDependentList().forEach(activityDependent -> {
                        if(nonNull(activityDependent.getId())){
                            activitiesIdsDelete.add(activityDependent.getId());
                        }
                    });
                    if(!activitiesIdsDelete.isEmpty()){
                        var activitiesDelete = activityDependentService.findAll(
                                ActivityDependentFilterType.builder()
                                        .activityBranch(activity.getId())
                                        .idsNotIn(activitiesIdsDelete).build(),
                                PageRequest.of(0, 9999, ASC, "id"));

                        if(nonNull(activitiesDelete) && !activitiesDelete.isEmpty()){
                            activitiesDelete.forEach(activityDependent -> {
                                activityDependentService.processRemove(activityDependent.getId());
                            });
                        }
                        activity.getActivityDependentList().forEach(activityDependent -> {
                            if(isNull(activityDependent.getId())){
                                var activitySource = activityRepository.findByActivityId(activityDependent.getActivitySource());
                                activityDependentService.saveActivityDependent(ActivityDependent.builder()
                                        .activitySource(activitySource.getId())
                                        .identifierSource(activitySource.getIdentifier())
                                        .activityBranch(activity)
                                        .identifierBranch(activity.getIdentifier())
                                        .build());
                            }
                        });
                        if(!activityRepository.allDependenciesCompleted(activity.getId())){
                            activity.setIsBlock(true);
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            if(activity.getTagsEnum().equals(DEPENDENT)){
                                    activity.setTagsEnum(INDEPENDENT);
                                    activity.setIsBlock(false);
                            }
                        }
                        var activities1 = activityDependentService.findAll(ActivityDependentFilterType
                                .builder().activityBranch(activity.getId()).build(), PageRequest.of(0, 9999, ASC, "id"));
                        if(isNull(activities1) || activities1.isEmpty()){
                            activity.setIsBlock(false);

                        }
                    }else{
                        if(!activities.isEmpty()){
                            activities.forEach(activityDelete -> activityDependentService.processRemove(activityDelete.getId()));
                        }
                        for(ActivityDependent activityDependent : activity.getActivityDependentList()){
                            var activitySource = activityRepository.findByActivityId(activityDependent.getActivitySource());
                            activityDependentService.saveActivityDependent(ActivityDependent.builder()
                                    .activitySource(activitySource.getId())
                                    .identifierSource(activitySource.getIdentifier())
                                    .activityBranch(activity)
                                    .identifierBranch(activity.getIdentifier())
                                    .build());
                        }
                        activity.setIsBlock(true);
                        activity.setTagsEnum(DEPENDENT);
                    }

                }else{
                    activities.forEach(activityDelete -> activityDependentService.processRemove(activityDelete.getId()));
                    activity.setIsBlock(false);
                    activity.setTagsEnum(INDEPENDENT);
                }

            }else{
                if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){

                    if(!activityDependentService.existBy(ID_ACTIVITY_BRANCH.existBy(asList(activity.getId().toString())))){
                        activity.setIsBlock(true);
                        activity.setTagsEnum(DEPENDENT);
                    }else{
                        if(!activityRepository.allDependenciesCompleted(activity.getId())){
                            activity.setIsBlock(true);
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setTagsEnum(INDEPENDENT);
                            activity.setIsBlock(false);

                        }
                    }

                    for(ActivityDependent activityDependent : activity.getActivityDependentList()){
                        var activitySource = activityRepository.findByActivityId(activityDependent.getActivitySource());
                        activityDependentService.saveActivityDependent(ActivityDependent.builder()
                                .activitySource(activitySource.getId())
                                .identifierSource(activitySource.getIdentifier())
                                .activityBranch(activity)
                                .identifierBranch(activity.getIdentifier())
                                .build());
                    }

                }
            }
            if(activity.getSectorActivity().equals("FEITO")){
                activityRepository.save(activity);
                checkDependents(activity.getId());
            }else{
                activityRepository.save(activity);
            }
        }catch(Exception e){
            throw new RuntimeException("Ocorreu um erro ao processo de salvamento da atividade");
      }
    }

    public Activity findById(Long id) {
        return activityRepository.findByActivityId(id);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Page<Activity> findAll(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        var activityDependentsList = activityDependentService.findAll(ActivityDependentFilterType
                .builder()
                .activityBranch(id).build(),
                PageRequest.of(0, 9999, ASC, "id"));

        if(nonNull(activityDependentsList) && !activityDependentsList.getContent().isEmpty()){
            activityDependentsList.forEach(activityDependent -> activityDependentService.delete(activityDependent.getId()));
        }

        activityDependentsList = activityDependentService.findAll(ActivityDependentFilterType.builder()
                        .activitySource(id).build(),
                        PageRequest.of(0, 9999, ASC, "id"));
        if(nonNull(activityDependentsList) && !activityDependentsList.getContent().isEmpty()){
            activityDependentsList.forEach(activityDependent -> {
                var activityBranch = findById(activityDependent.getActivityBranch().getId());
                if(!activityBranch.getActivityDependentList().isEmpty()){
                    if(activityBranch.getActivityDependentList().size() == 1){
                        activityBranch.setIsBlock(false);
                        activityBranch.setTagsEnum(INDEPENDENT);
                        activityBranch.getActivityDependentList().clear();
                    }
                }
                    activityBranch.getActivityDependentList().remove(activityDependent);

                    update(activityBranch);
            });
        }

        delete(id);


    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void updateSectorCard(Activity activity) {
        var activityAux = findById(activity.getId());
        if(nonNull(activityAux.getUser())){
            activity.setUser(activityAux.getUser());
        }
        if(activity.getSectorActivity().equals("FEITO")){
            activityRepository.save(activity);
            checkDependents(activity.getId());
        }else{
            if(nonNull(activity.getTagsEnum())){
                switch (activity.getTagsEnum()){
                    case URGENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        }
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                        }else{
                            var activiyOld = findById(activity.getId());
                            activity.setTagsEnum(activiyOld.getTagsEnum());
                        }
                        break;
                    case INDEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            if(!activityRepository.allDependenciesCompleted(activity.getId())){
                                activity.setTagsEnum(DEPENDENT);
                                activity.setIsBlock(true);
                            }
                        }
                        break;
                    case IMPROVEMENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setTagsEnum(DEPENDENT);
                        }
                        break;
                }
            }

            if(activityAux.getSectorActivity().equals("FEITO")){
                var activitySaved = activityRepository.save(activity);
                var activitiesDependents = activityDependentService.findAll(ActivityDependentFilterType.builder().activitySource(activitySaved.getId()).build(),
                        PageRequest.of(0, 9999, ASC, "id"));
                if(nonNull(activitiesDependents) && !activitiesDependents.isEmpty()){
                    activitiesDependents.forEach(activityDependent -> {
                        var activityUpdate = findById(activityDependent.getActivityBranch().getId());
                        if(!activityUpdate.getSectorActivity().equals("FEITO")){
                            activityUpdate.setTagsEnum(DEPENDENT);
                            activityUpdate.setIsBlock(true);
                        }
                        update(activityUpdate);
                    });
                }
            }else{
                activityRepository.save(activity);
            }
        }
    }

    public void delete(Long id){
        activityRepository.deleteById(id);
    }

    public void checkDependents(Long activityId){
        var activitiesDependents = activityDependentService.findAll(ActivityDependentFilterType.builder().activitySource(activityId).build(),
                PageRequest.of(0, 9999, ASC, "id"));
        activitiesDependents.forEach(activityDependent -> {
            if(activityRepository.allDependenciesCompleted(activityDependent.getActivityBranch().getId())){
                var activity = activityRepository.findByActivityId(activityDependent.getActivityBranch().getId());
                activity.setIsBlock(false);
                //activity.setTagsEnum(INDEPENDENT);
                activityRepository.save(activity);
            }
        });
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<Activity> example) {
        return activityRepository.exists(example);
    }



    @Transactional(readOnly = true)
    public Page<Activity> findAllByProjNotInUser(ActivityFilterType filter, Pageable pageable) {
        try {
            var test = activityRepository.findAllByProjNotInUser(
                    filter.getOrganizationId(),
                    filter.getUserIds(),
                    filter.getProjectId(),
                    pageable);
            return test;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public Page<Activity> findAllByProjAndUser(ActivityFilterType filter, Pageable pageable) {
        try {
            return activityRepository.findAllByProjAndUser(
                    filter.getOrganizationId(),
                    filter.getUserIds(),
                    filter.getProjectId(),
                    pageable);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public Page<Activity> findAllBy(ActivityFilterType filter, Pageable pageable) {
        return activityRepository.findAllBy(
                filter.getOrganizationId(),
                filter.getProjectId(),
                filter.getActivityId(),
                pageable);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Activity assignUserInActivity(Activity activity) {
        try {
            var activityOld = findById(activity.getId());
            activityOld.setUser(activity.getUser());

            return activityRepository.save(activityOld);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
