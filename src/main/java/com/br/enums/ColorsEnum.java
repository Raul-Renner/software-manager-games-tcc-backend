package com.br.enums;

public enum ColorsEnum {

    RED(1,"#DB6262"),
    GREEN(2, "#91D686"),
    ORANGE(3,"#E1AF64"),
    WHITE(4, "#FFFFFF"),
    BLUE(5,"#2F8BF5");

    private final Integer id;

    private final String color;


    ColorsEnum(Integer id, String color) {
        this.id = id;
        this.color = color;
    }
}
