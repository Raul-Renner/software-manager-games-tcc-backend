package com.br.type;

import lombok.*;

@Data
@Builder
public class ColumnBoardFilterType {
    private Long projectId;

    private Long columnId;

    private String sectorActivity;

}
