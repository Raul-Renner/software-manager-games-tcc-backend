package com.br.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionID = 1L;

    @Id
    @Column(name = "id_profile")
    private Long id;

    @Column(name = "type_profile")
    private String type;

    @Column(name = "decription")
    private String description;
}
