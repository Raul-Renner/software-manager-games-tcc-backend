package com.br.controller;

import com.br.entities.Organization;
import com.br.service.OrganizationService;

import com.br.validation.ValidOrganization;
import com.br.vo.OrganizationSaveVO;
import com.br.vo.OrganizationUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import static org.springframework.http.ResponseEntity.ok;
import static reactor.core.publisher.Mono.*;
import static com.br.fieldQueries.OrganizationFieldQuery.valueOf;
@Slf4j
@RestController
@RequestMapping("/api/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @CrossOrigin
    @PostMapping
    public Mono<?> save(@RequestBody OrganizationSaveVO organizationSaveVO){
        organizationService.save(organizationSaveVO.toEntity());
        return empty();
    }

    @CrossOrigin
    @PutMapping("{id}")
    public Mono<?> update(@PathVariable @ValidOrganization Long id, @Valid @RequestBody OrganizationUpdateVO organizationUpdateVO){

            if (!organizationUpdateVO.getId().equals(id)) {
                throw new RuntimeException("Os ids da organizacao nao conferem.");
            }
            Organization organization = organizationUpdateVO.toEntity();
            organization.setId(id);
            organizationService.update(organization);
            return just(ok().build());
    }

    @CrossOrigin
    @GetMapping("/find-by")
    public Mono<?> findBy(@RequestParam String field, @RequestParam String value) {
        log.info("Searching organization by field: {} and value {}.", field, value);
        return justOrEmpty(organizationService.findBy(valueOf(field).findBy(value)));
    }

}
