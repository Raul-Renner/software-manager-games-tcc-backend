package com.br.enums;

public enum ProfileEnum {

    ADMINISTRADOR(1, "ADMINISTRADOR",""),
    DESENVOLVEDOR(2, "DESENVOLVEDOR",""),
    GERENTE(3, "GERENTE", ""),
    LIDER_TECNICO(4, "LIDER_TECNICO",""),
    TESTADOR(5, "TESTADOR",""),
    DESIGNER(6, "DESIGNER", "");

    private final Integer id;

    private final String profile;

    private final String description;


    ProfileEnum(Integer id, String profile, String description) {
        this.id = id;
        this.profile = profile;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }
}
