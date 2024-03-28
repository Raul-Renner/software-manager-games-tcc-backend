package com.br.fieldQueries;

import com.br.entities.Organization;
import com.br.entities.User;
import com.br.entities.UserInformation;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

public enum OrganizationFieldQuery {
    ORGANIZATION_ID {
        @Override
        public Example<Organization> existBy(List<String> values) {
            if(values.size() == 1) {
                return of(Organization.builder()
                        .id(parseLong(values.get(0))).build(), matchingAll().withIgnoreNullValues());
            } else {
                throw new RuntimeException();
            }
        }
        @Override
        public Example<Organization> findBy(String value) {
            var organization = Organization.builder().id(parseLong(value)).build();
            return of(organization, matchingAny());
        }


    },
    EMAIL{
        @Override
        public Example<Organization> existBy(List<String> values) {
            var organization = new Organization();
            organization.setEmail(values.get(0));
            return of(organization, matchingAny().withIgnoreCase("email"));
        }

        @Override
        public Example<Organization> findBy(String value) {
            return null;
        }

    };
//    ORGANIZATION_ID_PROJECTS{
//        @Override
//        public Example<Organization> existBy(List<String> values) {
//            return null;
//        }
//
//        @Override
//        public Example<Organization> findBy(String value) {
//            return null;
//        }
//
//        @Override
//        public Example<Organization> existProjectsOrg(List<String> values) {
//            if(values.size() >= 1) {
//                return of(Organization.builder()
//                        .projects((List<Project>) Project.builder().id(parseLong(values.get(0))).build())
//                        .id(parseLong(values.get(1))).build(), matchingAll().withIgnoreNullValues());
//            } else {
//                throw new RuntimeException();
//            }
//        }
//    }

    public abstract Example<Organization> existBy(List<String> values);
    public abstract Example<Organization> findBy(String value);

//    public abstract Example<Organization> existProjectsOrg(List<String> values);


}
