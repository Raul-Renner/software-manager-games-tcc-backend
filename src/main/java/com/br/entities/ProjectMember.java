package com.br.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "project_members")
public class ProjectMember implements Serializable {
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JoinColumn(name = "id_project")
    Project project;
}
