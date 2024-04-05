package com.br.service;

import com.br.dto.AuthenticatedResponse;
import com.br.entities.User;
import com.br.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var user = userRepository.findByLogin(username);

            return user;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public AuthenticatedResponse authenticationUser(User auth, String token) {


        if (auth.getProfile().getRole().equals("ROLE_ADMINISTRADOR")) {
            var user = User.builder()
                    .id(auth.getId())
                    .userInformation(auth.getUserInformation())
                    .login(auth.getLogin())
                    .organization(auth.getOrganization())
                    .profile(auth.getProfile()).build();

            return new AuthenticatedResponse(token, user);
        }

        var user = User.builder()
                .id(auth.getId())
                .userInformation(auth.getUserInformation())
                .login(auth.getLogin())
                .organization(auth.getOrganization())
                .projects(auth.getProjects())
                .profile(auth.getProfile())
                .activities(auth.getActivities()).build();

        return new AuthenticatedResponse(token, user);
    }
}
