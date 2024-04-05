package com.br.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "columnBoard")
public class ColumnBoard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_column")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="sector_description")
    private String sectorActivity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_project")
    private Project project;

    @OneToMany(mappedBy = "columnBoard", cascade = CascadeType.REMOVE)
    private List<Activity> activities;

}
