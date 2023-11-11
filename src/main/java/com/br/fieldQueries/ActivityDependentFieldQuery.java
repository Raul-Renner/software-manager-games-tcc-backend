package com.br.fieldQueries;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Example.of;

public enum ActivityDependentFieldQuery {
    ID_ACTIVITY_BRANCH {
        @Override
        public Example<ActivityDependent> findBy(List<String> values) {
            return of(ActivityDependent.builder()
                    .activityBranch(Activity.builder()
                            .id(parseLong(values.get(0)))
                            .build()).build());
        }
    },
    ID_ACTIVITY_SOURCE {
        @Override
        public Example<ActivityDependent> findBy(List<String> values) {
            return of(ActivityDependent.builder().activitySource(parseLong(values.get(0))).build());
        }
    };

    public abstract Example<ActivityDependent> findBy(List<String> values);

}
