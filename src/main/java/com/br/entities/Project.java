package com.br.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.CascadeType.*;


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

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "project_members", joinColumns = {
            @JoinColumn(name = "id_project", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_user", nullable = false)})
    private List<User> members;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @OneToMany(mappedBy = "project",cascade = CascadeType.REMOVE)
    private List<ColumnBoard> columnsBoard;

    @OneToMany(mappedBy = "project",cascade = CascadeType.REMOVE)
    private List<Activity> activities;
}
