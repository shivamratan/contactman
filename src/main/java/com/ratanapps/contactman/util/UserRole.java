package com.ratanapps.contactman.util;

public enum UserRole {

    USER_GENERAL("user_general"), USER_ADMIN("user_admin");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
