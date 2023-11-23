package com.br.service;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.enums.StatusPriorityEnum;
import com.br.repository.ActivityRepository;
import com.br.type.ActivityDependentFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.br.enums.SectorActivityEnum.DONE;
import static com.br.enums.SectorActivityEnum.PRIORITY;
import static com.br.enums.TagsEnum.DEPENDENT;
import static com.br.enums.TagsEnum.INDEPENDENT;
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
    public void save(Activity activity) {
        try {
            if(nonNull(activity.getTagsEnum())){
                switch (activity.getTagsEnum()){
                    case URGENT:
                        activity.setColorCard("#DB6262");
                        activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        activity.setSectorActivityEnum(PRIORITY);
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");
                        }else{
                            activity.setColorCard("#FFFFFF");
                            activity.setTagsEnum(INDEPENDENT);
                        }
                        break;
                    case INDEPENDENT:
                        activity.setColorCard("#FFFFFF");
                        break;
                    case IMPROVEMENT:
                        activity.setColorCard("#2F8BF5");
                        break;
                }
            }
            if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                if(activity.getSectorActivityEnum().equals(DONE)){
                    activity.setColorCard("#107351");
                    activity.setIsBlock(false);

                }else{
                    activity.setColorCard("#FFA500");
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
                    activityCopy.setColorCard("#FFFFFF");
                    activityCopy.setTagsEnum(INDEPENDENT);
                    activityRepository.save(activityCopy);
                }
            }else{
                if(activity.getSectorActivityEnum().equals(DONE)){
                    activity.setColorCard("#107351");
                }
                activity.setIsBlock(false);
                activityRepository.save(activity);
            }

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
                            activity.setColorCard("#FFA500");
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setColorCard("#DB6262");
                            activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        }
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");


                        }else{
                            var activiyOld = findById(activity.getId());
                            activity.setColorCard(activiyOld.getColorCard());
                            activity.setTagsEnum(activiyOld.getTagsEnum());
                        }
                        break;
                    case INDEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){

                            if(!activityRepository.allDependenciesCompleted(activity.getId())){
                                activity.setColorCard("#FFA500");
                                activity.setTagsEnum(DEPENDENT);
                            }else{
                                activity.setColorCard("#FFFFFF");
                            }
                        }else{
                            activity.setColorCard("#FFFFFF");
                        }
                        break;
                    case IMPROVEMENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setColorCard("#2F8BF5");
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
                            activity.setColorCard("#FFA500");
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            if(activity.getTagsEnum().equals(DEPENDENT)){
                                    activity.setColorCard("#FFFFFF");
                                    activity.setTagsEnum(INDEPENDENT);
                                    activity.setIsBlock(false);
                            }
                        }
                        var activities1 = activityDependentService.findAll(ActivityDependentFilterType
                                .builder().activityBranch(activity.getId()).build(), PageRequest.of(0, 9999, ASC, "id"));
                        if(isNull(activities1) || activities1.isEmpty()){
                            activity.setIsBlock(false);
                            activity.setColorCard("#2F8BF5");

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
                        activity.setColorCard("#FFA500");
                        activity.setTagsEnum(DEPENDENT);
                    }

                }else{
                    activities.forEach(activityDelete -> activityDependentService.processRemove(activityDelete.getId()));
                    activity.setIsBlock(false);
                    activity.setColorCard("#FFFFFF");
                    activity.setTagsEnum(INDEPENDENT);
                }

            }else{
                if(nonNull(activity.getActivityDependentList()) && !activity.getActivityDependentList().isEmpty()){
                    if(!activityRepository.allDependenciesCompleted(activity.getId())){
                        activity.setIsBlock(true);
                        activity.setColorCard("#FFA500");
                        activity.setTagsEnum(DEPENDENT);
                    }else{
                        //if(activity.getTagsEnum().equals(DEPENDENT)){
                            activity.setColorCard("#FFFFFF");
                            activity.setTagsEnum(INDEPENDENT);
                            activity.setIsBlock(false);
                    //    }
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
            if(activity.getSectorActivityEnum().equals(DONE)){
                activity.setColorCard("#107351");
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
        if(activity.getSectorActivityEnum().equals(DONE)){
            activity.setColorCard("#107351");
            activityRepository.save(activity);
            checkDependents(activity.getId());
        }else{
            var activityAux = findById(activity.getId());
            if(nonNull(activity.getTagsEnum())){
                switch (activity.getTagsEnum()){
                    case URGENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setColorCard("#DB6262");
                            activity.setStatusPriorityEnum(StatusPriorityEnum.HIGH);
                        }
                        break;
                    case DEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");
                        }else{
                            var activiyOld = findById(activity.getId());
                            activity.setColorCard(activiyOld.getColorCard());
                            activity.setTagsEnum(activiyOld.getTagsEnum());
                        }
                        break;
                    case INDEPENDENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            if(!activityRepository.allDependenciesCompleted(activity.getId())){
                                activity.setColorCard("#FFA500");
                                activity.setTagsEnum(DEPENDENT);
                                activity.setIsBlock(true);
                            }else{
                                activity.setColorCard("#FFFFFF");
                            }
                        }else{
                            activity.setColorCard("#FFFFFF");
                        }
                        break;
                    case IMPROVEMENT:
                        if(nonNull(activity.getActivityDependentList()) &&
                                !activity.getActivityDependentList().isEmpty()){
                            activity.setColorCard("#FFA500");
                            activity.setTagsEnum(DEPENDENT);
                        }else{
                            activity.setColorCard("#2F8BF5");
                        }
                        break;
                }
            }

            if(activityAux.getSectorActivityEnum().equals(DONE)){
                var activitySaved = activityRepository.save(activity);
                var activitiesDependents = activityDependentService.findAll(ActivityDependentFilterType.builder().activitySource(activitySaved.getId()).build(),
                        PageRequest.of(0, 9999, ASC, "id"));
                if(nonNull(activitiesDependents) && !activitiesDependents.isEmpty()){
                    activitiesDependents.forEach(activityDependent -> {
                        var activityUpdate = findById(activityDependent.getActivityBranch().getId());
                        if(!activityUpdate.getSectorActivityEnum().equals(DONE)){
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
                activity.setColorCard("#FFFFFF");
                activity.setTagsEnum(INDEPENDENT);
                activityRepository.save(activity);
            }
        });
    }
}
