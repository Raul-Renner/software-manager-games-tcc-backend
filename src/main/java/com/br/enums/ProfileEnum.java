package com.br.enums;

public enum ProfileEnum {

    ADMIN(1, "ADMIN"),
    DEVELOPER(2, "DEVELOPER"),
    MANAGER(3, "MANAGER");

    private final Integer id;

    private final String profile;


    ProfileEnum(Integer id, String profile) {
        this.id = id;
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }
}
