package com.tinqinacademy.bff.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BffBathroomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");

    private final String code;

    BffBathroomType(String code) {
        this.code = code;
    }

    @JsonValue
    public String toString() {
        return this.code;
    }

    @JsonCreator
    public static BffBathroomType getByCode(String code){
        for (BffBathroomType bffBathroomType : BffBathroomType.values()) {
            if (bffBathroomType.code.equals(code)) {
                return bffBathroomType;
            }
        }
        return UNKNOWN;
    }
}
