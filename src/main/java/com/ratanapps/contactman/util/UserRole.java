package com.ratanapps.contactman.util;

public enum UserRole {

    USER_GENERAL("ROLE_GENERAL","GENERAL"), USER_ADMIN("ROLE_ADMIN","ADMIN");

    private String value;
    private String dbValue;

    UserRole(String dbValue, String value) {
        this.dbValue = dbValue;
        this.value = value;
    }

    public String getDbValue() {
        return dbValue;
    }

    public String getValue() {
        return value;
    }
}
