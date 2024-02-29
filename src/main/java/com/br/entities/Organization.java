package com.br.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"owners","projects"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "organization")
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organization")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "organization",fetch = LAZY)
    private List<User> owners;

    @OneToMany(mappedBy = "organization",fetch = EAGER)
    private List<Project> projects;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;


}
