package com.rundgrun.eco.data;

public enum StatusPak {
    NONE("----"), ERROR("НЕИСПРАВЕН"), NEWERROR("ПРОПАЛО"), NORMAL("В РАБОТЕ"), NEWNORMAL("ПОЯВИЛОСЬ");

    final String name;

    StatusPak(String name) {
        this.name = name;
    }

    public static StatusPak getStatus(String number) {
        switch (number) {
            case "0":

            case "1":
            case "3":
                return NONE;

            case "4":
                return ERROR;

            case "5":
                return NEWERROR;

            case "6":
                return NORMAL;

            case "7":
                return NEWNORMAL;

            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
