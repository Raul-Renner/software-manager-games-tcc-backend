package com.br.enums;

public enum TagsEnum {

    URGENT(1, "URGENT"),
    DEPENDENT(2, "DEPENDENT"),
    INDEPENDENT(3, "INDEPENDENT"),
    IMPROVEMENT(4, "IMPROVEMENT");

    private final Integer id;
    private final String role;

    TagsEnum(Integer id, String role) {
        this.id = id;
        this.role = role;
    }


}
