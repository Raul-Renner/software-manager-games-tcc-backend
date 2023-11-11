package com.br.entities;

import com.fasterxml.jackson.annotation.*;
import com.br.enums.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

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

    @Column(name="sector_activity")
    @Enumerated(EnumType.STRING)
    private SectorActivityEnum sectorActivityEnum;

    @Column(name="status_activity")
    @Enumerated(EnumType.STRING)
    private StatusActivityEnum statusActivityEnum;

    @Column(name="status_priority")
    @Enumerated(EnumType.STRING)
    private StatusPriorityEnum statusPriorityEnum;

    @Column(name="tags")
    @Enumerated(EnumType.STRING)
    private TagsEnum tagsEnum;

    @Column(name="color")
    private String colorCard;

    @Column(name = "is_blocked")
    private Boolean isBlock;

    @OneToMany(mappedBy = "activityBranch",fetch = EAGER)
    private List<ActivityDependent> activityDependentList;

    public Activity() {
        this.identifier = "#" + counter++;
    }
}
