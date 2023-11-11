package com.br.service;

import com.br.entities.ActivityDependent;
import com.br.repository.ActivityDependentRepository;
import com.br.type.ActivityDependentFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
