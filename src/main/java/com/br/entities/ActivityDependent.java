package com.br.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "activity_dependent")
public class ActivityDependent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_activity_source")
    private Long activitySource;

    @Column(name = "identifier_source")
    private String identifierSource;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_activity")
    private Activity activityBranch;

    @Column(name = "identifier_branch_dependent")
    private String identifierBranch;
}
