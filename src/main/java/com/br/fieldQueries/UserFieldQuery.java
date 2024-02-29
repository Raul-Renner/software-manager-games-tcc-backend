package com.br.fieldQueries;



import com.br.entities.Organization;
import com.br.entities.Project;
import com.br.entities.User;
import com.br.entities.UserInformation;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

public enum UserFieldQuery {

    LOGIN{
        @Override
        public Example<User> existBy(List<String> values) {
            var user = new User();
            user.setLogin(values.get(0));
            return of(user, matchingAny().withIgnoreCase("login"));
        }

        @Override
        public Example<User> findBy(List<String> values) {
            var user = new User();
            user.setLogin(values.get(0));
            return of(user, matchingAll().withIgnoreCase("login"));
        }
    },
    EMAIL{
        @Override
        public Example<User> existBy(List<String> values) {
            var user = new User();
            var userInformation = new UserInformation();
            userInformation.setEmail(values.get(0));
            user.setUserInformation(userInformation);
            return of(user, matchingAny().withIgnoreCase("email"));
        }

        @Override
        public Example<User> findBy(List<String> values) {
            var user = new User();
            var userInformation = new UserInformation();
            userInformation.setEmail(values.get(0));
            user.setUserInformation(userInformation);
            return of(user, matchingAny().withIgnoreCase("email"));
        }
    },

    ID_LOGIN {
        @Override
        public Example<User> existBy(List<String> values) {
            if (values.size() >= 2) {
                var user = new User();
                user.setId(parseLong(values.get(0)));
                user.setLogin(values.get(1));
                return of(user, matchingAll().withIgnoreCase("login"));
            }
            throw new RuntimeException();
        }

        @Override
        public Example<User> findBy(List<String> values) {
            return null;
        }


    },
    ID_EMAIL {
        @Override
        public Example<User> existBy(List<String> values) {
            if (values.size() >= 2) {
                var user = new User();
                user.setId(parseLong(values.get(0)));
                var userInformation = new UserInformation();
                userInformation.setEmail(values.get(1));
                user.setUserInformation(userInformation);
                return of(user, matchingAll().withIgnoreCase("userInformation.email"));
            }
            throw new RuntimeException();
        }

        @Override
        public Example<User> findBy(List<String> values) {
            return null;
        }


    },
    ID{
        @Override
        public Example<User> existBy(List<String> values) {
            var user = new User();
            user.setId(parseLong(values.get(0)));
            return of(user, matchingAny());
        }

        @Override
        public Example<User> findBy(List<String> values) {
            var user = User.builder().id(parseLong(values.get(0))).build();
            return of(user, matchingAny());
        }
    },
    USER_ID_ORGANIZATION_ID{
        @Override
        public Example<User> existBy(List<String> values) {
            if(values.size() > 1) {
                return of(User.builder()
                                .id(parseLong(values.get(0)))
                                .organization(Organization.builder()
                                        .id(parseLong(values.get(1))).build()).build(),
                        matchingAll().withIgnoreNullValues());
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public Example<User> findBy(List<String> values) {
            var user = new User();
            user.setId(parseLong(values.get(0)));
            return of(user, matchingAny());
        }
    };

    public abstract Example<User> existBy(List<String> values);

    public abstract Example<User> findBy(List<String>  values);
}
