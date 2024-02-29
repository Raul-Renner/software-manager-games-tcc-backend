package com.br.entities;


import com.br.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(exclude = {"projects"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user_")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_userinformation")
    private UserInformation userInformation;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_organization")
    private Organization organization;

    @JsonIgnore
    @ManyToMany(mappedBy = "members", cascade = REMOVE, fetch = LAZY)
    private List<Project> projects;

    @Enumerated(STRING)
    @Column(name = "tp_profile")
    private ProfileEnum profile;

}
