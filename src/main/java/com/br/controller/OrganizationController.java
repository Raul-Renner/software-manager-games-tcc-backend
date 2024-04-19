package com.br.controller;

import com.br.entities.Organization;
import com.br.service.OrganizationService;

import com.br.validation.ValidOrganization;
import com.br.vo.OrganizationSaveVO;
import com.br.vo.OrganizationUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.br.fieldQueries.OrganizationFieldQuery.valueOf;
@Slf4j
@RestController
@RequestMapping("/api/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid OrganizationSaveVO organizationSaveVO){
        try {
            organizationService.save(organizationSaveVO.toEntity());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Error ao tentar criar organização");
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity update(@PathVariable @ValidOrganization Long id, @Valid @RequestBody OrganizationUpdateVO organizationUpdateVO){
            try{
                if (!organizationUpdateVO.getId().equals(id)) {
                    throw new RuntimeException("Os ids da organizacao nao conferem.");
                }
                Organization organization = organizationUpdateVO.toEntity();
                organization.setId(id);
                organizationService.update(organization);

                return ResponseEntity.ok("Atualização nos dados realizados com sucesso!");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body( "Error ao tentar atualizar informações da organização");
            }


    }

    @CrossOrigin
    @GetMapping("/find-by")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity findBy(@RequestParam String field, @RequestParam String value) {
        log.info("Searching organization by field: {} and value {}.", field, value);
        return ResponseEntity.ok(organizationService.findBy(valueOf(field).findBy(value)));
    }

}
