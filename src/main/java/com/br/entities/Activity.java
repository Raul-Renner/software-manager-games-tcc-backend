package com.br.entities;

import com.fasterxml.jackson.annotation.*;
import com.br.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Builder
@AllArgsConstructor
@ToString(exclude = {"classCourses"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "activity")
public class Activity implements Serializable {
    private static int counter = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activity")
    private Long id;
    //, unique = true
    @Column(name = "identifier")
    private String identifier;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "time_estimated")
    private String estimatedTime;

    @Column(name = "time_used")
    private String usedTime;

    @Column(name="sector_activity")
    private String sectorActivity;

    @Column(name="status_activity")
    private Boolean isFinished;

    @Column(name="status_priority")
    @Enumerated(EnumType.STRING)
    private StatusPriorityEnum statusPriorityEnum;

    @Column(name="tags")
    @Enumerated(EnumType.STRING)
    private TagsEnum tagsEnum;

    @Column(name = "is_blocked")
    private Boolean isBlock;

    @OneToMany(mappedBy = "activityBranch",cascade = CascadeType.REMOVE)
    private List<ActivityDependent> activityDependentList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_column")
    private ColumnBoard columnBoard;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_project")
    private Project project;

    public Activity() {
        this.identifier = "#" + counter++;
    }
}
