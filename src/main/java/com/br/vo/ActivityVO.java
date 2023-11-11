package com.br.vo;

import com.br.entities.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    public Activity toEntity(){
        return Activity.builder().id(id).build();
    }
}
