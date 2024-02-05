package com.br.enums;

public enum UserFunction {
    DEVELOPER(3, "DEVELOPER"),
    TESTER(4, "TESTER"),
    DESIGNER(5, "DESIGNER");

    private final Integer id;

    private final String userFunction;


    UserFunction(Integer id, String userFunction) {
        this.id = id;
        this.userFunction = userFunction;
    }

    public Integer getId() {
        return id;
    }

    public String getUserFunction() {
        return userFunction;
    }
}
