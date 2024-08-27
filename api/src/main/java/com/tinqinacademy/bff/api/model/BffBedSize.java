package com.tinqinacademy.bff.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BffBedSize {
    KING_SIZED("kingSized"),
    QUEEN_SIZED("queenSized"),
    DOUBLE("double"),
    SMALL_DOUBLE("smallDouble"),
    SINGLE("single"),

    UNKNOWN("");

    private final String code;

    BffBedSize(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BffBedSize getByCode(String code){
        for (BffBedSize bffBedSize : BffBedSize.values()) {
            if (bffBedSize.code.equals(code)) {
                return bffBedSize;
            }
        }
        return UNKNOWN;
    }

    @JsonValue
    public String toString(){
        return this.code;
    }
}
