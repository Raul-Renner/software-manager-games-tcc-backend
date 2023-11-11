package com.br.enums;

public enum StatusPriorityEnum {

    LOW(1, "LOW"),
    MEDIUM(2, "MEDIUM"),
    HIGH(3, "HIGH");


    private final Integer id;

    private final String role;

    StatusPriorityEnum(Integer id, String role) {
        this.id = id;
        this.role = role;
    }


    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
