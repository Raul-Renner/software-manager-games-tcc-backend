package com.br.service;

import com.br.dto.ActivityDepedentUpdate;
import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.repository.ActivityDependentRepository;
import com.br.type.ActivityDependentFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityDependentService {

    private final ActivityDependentRepository activityDependentRepository;

    public void saveActivityDependent(ActivityDependent activityDependent){
        activityDependentRepository.save(activityDependent);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processRemove(Long id) {
        delete(id);
    }

    public void delete(Long id) {
        activityDependentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ActivityDependent> findAll(ActivityDependentFilterType filter, Pageable pageable) {
        return activityDependentRepository.findAll(
                filter.getIdsNotIn(),
                filter.getActivitySource(),
                filter.getActivityBranch(),
                pageable);
    }

    @Transactional(readOnly = true)
    public void updateActivityDependent(ActivityDepedentUpdate activityDependent){
            var activitiesBranchDependent = findAll(ActivityDependentFilterType.builder()
                    .activityBranch(activityDependent.getActivityIdOld()).build(),
                    PageRequest.of(0, 9999, ASC, "id"));

            var activitiesSourceDependent = findAll(ActivityDependentFilterType.builder()
                            .activitySource(activityDependent.getActivityIdOld()).build(),
                    PageRequest.of(0, 9999, ASC, "id"));

            if(nonNull(activitiesBranchDependent) && !activitiesBranchDependent.isEmpty()){
                activitiesBranchDependent.forEach(activityDependent1 -> {
                    activityDependentRepository.save(ActivityDependent.builder()
                            .activitySource(activityDependent1.getActivitySource())
                            .identifierSource(activityDependent1.getIdentifierSource())
                            .activityBranch(activityDependent.getActivityIdNew())
                            .identifierBranch(activityDependent.getActivityIdNew().getIdentifier())
                            .id(activityDependent1.getId()).build());
                });
            }

            if(nonNull(activitiesSourceDependent) && !activitiesSourceDependent.isEmpty()){
                activitiesSourceDependent.forEach(activityDependent1 -> {
                    activityDependentRepository.save(ActivityDependent.builder()
                            .activitySource(activityDependent.getActivityIdNew().getId())
                            .identifierSource(activityDependent.getActivityIdNew().getIdentifier())
                            .activityBranch(activityDependent1.getActivityBranch())
                            .identifierBranch(activityDependent1.getIdentifierSource())
                            .build());
                });
            }
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<ActivityDependent> example) {
        return activityDependentRepository.exists(example);
    }


}
