package com.br.enums;

public enum ProfileEnum {

    ADMINISTRADOR("ADMINISTRADOR"),
    DESENVOLVEDOR("DESENVOLVEDOR"),
    GERENTE("GERENTE"),
    LIDER_TECNICO("LIDER_TECNICO"),
    TESTADOR("TESTADOR"),
    DESIGNER("DESIGNER");

    private final String role;

    private final String description;


    ProfileEnum(String description) {
        this.role = "ROLE_" + this.name();
        this.description = description;
    }

    public String getRole() {
        return role;
    }
}
