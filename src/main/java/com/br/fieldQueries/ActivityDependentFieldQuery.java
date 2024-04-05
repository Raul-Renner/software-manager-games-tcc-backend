package com.br.fieldQueries;

import com.br.entities.Activity;
import com.br.entities.ActivityDependent;
import com.br.entities.Project;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

public enum ActivityDependentFieldQuery {
    ID_ACTIVITY_BRANCH {
        @Override
        public Example<ActivityDependent> findBy(List<String> values) {
            return of(ActivityDependent.builder()
                    .activityBranch(Activity.builder()
                            .id(parseLong(values.get(0)))
                            .build()).build());
        }

        @Override
        public Example<ActivityDependent> existBy(List<String> values) {
            if(values.size() >= 1) {
                return of(ActivityDependent.builder()
                        .activityBranch(Activity.builder()
                                .id(parseLong(values.get(0)))
                                .build()).build());
            } else {
                throw new RuntimeException();
            }

        }
    },
    ID_ACTIVITY_SOURCE {
        @Override
        public Example<ActivityDependent> findBy(List<String> values) {
            return of(ActivityDependent.builder().activitySource(parseLong(values.get(0))).build());
        }

        @Override
        public Example<ActivityDependent> existBy(List<String> values) {
            return null;
        }
    };

    public abstract Example<ActivityDependent> findBy(List<String> values);

    public abstract Example<ActivityDependent> existBy(List<String> values);


}
