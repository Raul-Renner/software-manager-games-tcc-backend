package com.br.fieldQueries;

import com.br.entities.Organization;
import com.br.entities.Project;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

public enum ProjectFieldQuery {

    PROJECT_ID {
        @Override
        public Example<Project> existBy(List<String> values) {
            if(values.size() >= 1) {
                return of(Project.builder()
                        .id(parseLong(values.get(0))).build(), matchingAll().withIgnoreNullValues());
            } else {
                throw new RuntimeException();
            }
        }
    },
    ORGANIZATION_ID_PROJECT_ID {
        @Override
        public Example<Project> existBy(List<String> values) {
            if(values.size() > 1) {
                return of(Project.builder()
                        .id(parseLong(values.get(0)))
                        .organization(Organization.builder()
                                .id(parseLong(values.get(1))).build()).build(),
                        matchingAll().withIgnoreNullValues());
            } else {
                throw new RuntimeException("Um dos projetos informados não existe ou não faz parte dessa organização");
            }
        }
    },
    ORGANIZATION_ID_PROJECTS_IN {
        @Override
        public Example<Project> existBy(List<String> values) {
            if(values.size() > 1) {
                return of(Project.builder()
                                .id(parseLong(values.get(0)))
                                .organization(Organization.builder()
                                        .id(parseLong(values.get(1))).build()).build(),
                        matchingAll().withIgnoreNullValues());
            } else {
                throw new RuntimeException();
            }
        }
    };

    public abstract Example<Project> existBy(List<String> values);
}
