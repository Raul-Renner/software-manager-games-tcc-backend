package com.br.vo;

import com.br.entities.Board;
import com.br.entities.ColumnBoard;
import com.br.validation.ValidBoard;
import com.br.validation.ValidColumnBoard;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnBoardUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ValidColumnBoard
    private Long id;

    @NotNull(message = "O nome da coluna é obrigatório.")
    @NotBlank(message = "O nome da coluna é obrigatório.")
    @Size(min = 3, max = 20, message = "O nome da coluna deve conter entre 3 e 20 caracteres.")
    private String name;

    @NotNull(message = "A descrição da coluna é obrigatório.")
    @NotBlank(message = "O descrição da coluna é obrigatório.")
    @Size(min = 3, max = 20, message = "A descrição da coluna deve conter entre 3 e 20 caracteres.")
    private String description;

    @ValidBoard
    private Long boardId;

    public ColumnBoard toEntity() {
        return ColumnBoard.builder()
                .board(Board.builder().id(boardId).build())
                .description(description)
                .name(name).build();
    }
}