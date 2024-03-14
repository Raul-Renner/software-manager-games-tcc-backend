package com.br.fieldQueries;

import com.br.entities.Board;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

public enum BoardFieldQuery {
    ID {
        @Override
        public Example<Board> existBy(List<String> values) {
            return Example.of(Board.builder()
                    .id(parseLong(values.get(0)))
                    .build(),matchingAll().withIgnoreNullValues());
        };
    };

    public abstract Example<Board> existBy(List<String> values);

    }
