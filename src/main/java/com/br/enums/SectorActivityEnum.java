package com.br.enums;

public enum SectorActivityEnum {

    PRIORITY(1, "PRIORITY"),
    TO_DO(2, "TO_DO"),
    SOFTWARE(3, "SOFTWARE"),
    ART(4, "ART"),
    DESIGN(5, "DESIGN"),
    SOUND(6, "SOUND"),
    INTEGRATION(7, "INTEGRATION"),
    TEST(8, "TEST"),
    DONE(9, "DONE");


    private final Integer id;

    private final String role;


    SectorActivityEnum(Integer id, String role) {
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
