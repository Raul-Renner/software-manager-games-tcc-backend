package com.br.fieldQueries;

import com.br.entities.Activity;
import com.br.entities.ColumnBoard;
import org.springframework.data.domain.Example;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

public enum ActivityFieldQuery {

    ID{
        @Override
        public Example<Activity> existBy(List<String> values){
            return Example.of(Activity.builder()
                    .id(parseLong(values.get(0)))
                    .build(),matchingAll().withIgnoreNullValues());
        }
    },
    ID_ACTIVITY_ID_COLUMN{
        @Override
        public Example<Activity> existBy(List<String> values){
            return Example.of(Activity.builder()
                    .id(parseLong(values.get(0)))
                    .columnBoard(ColumnBoard.builder()
                            .id(parseLong(values.get(1))).build())
                    .build(),matchingAll().withIgnoreNullValues());
        }
    };


    public abstract Example<Activity> existBy(List<String> values);
}
