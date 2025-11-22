package com.dellasse.backend.util;

public enum StatusUtils {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusUtils(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}