package com.br.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(exclude = {"members","activities"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_organization")
    private Organization organization;

    @ManyToMany(fetch = LAZY, cascade = PERSIST)
    @JoinTable(name = "project_members", joinColumns = {
            @JoinColumn(name = "id_project", nullable = false, unique = true)},
            inverseJoinColumns = {@JoinColumn(name = "id_user", nullable = false, unique = true)})
    private List<User> members;

    @OneToMany(mappedBy = "project")
    private List<Activity> activities;
}