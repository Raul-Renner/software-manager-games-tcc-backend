package com.br.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
