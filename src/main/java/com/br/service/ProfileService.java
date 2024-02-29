package com.br.service;

import com.br.entities.Profile;
import com.br.entities.Project;
import com.br.repository.ProfileRepository;
import com.br.type.ProjectFilterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }
}
