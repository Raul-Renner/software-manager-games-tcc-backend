package com.br.vo;

import com.br.entities.Board;
import com.br.entities.ColumnBoard;
import com.br.validation.ValidBoard;
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
public class ColumnBoardUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    private String sectorActivity;

    @ValidBoard
    private Long boardId;

    public ColumnBoard toEntity() {
        return ColumnBoard.builder()
                .board(Board.builder().id(boardId).build())
                .sectorActivity(sectorActivity)
                .name(name).build();
    }
}