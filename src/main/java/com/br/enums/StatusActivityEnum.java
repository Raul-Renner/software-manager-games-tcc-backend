package com.br.enums;

public enum StatusActivityEnum {

    TO_DO(1, "TO DO"),
    IN_PROGRESS(2, "IN PROGRESS"),
    IN_REVIEW(3, "IN REVIEW"),
    DONE(4, "DONE");

    private final Integer id;

    private final String role;


    StatusActivityEnum(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

}
