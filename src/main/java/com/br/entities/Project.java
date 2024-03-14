package com.br.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


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
    @JoinTable(name = "project_members", joinColumns = {
            @JoinColumn(name = "id_project", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_user", nullable = false)})
    private List<User> members;

//    @OneToMany(mappedBy = "project")
//    private List<Activity> activities;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_board")
    private Board board;
}
