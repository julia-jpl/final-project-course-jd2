package com.gmail.portnova.julia.web.constant;

public class ValidationConstant {
    public static final String EMAIL_PATTERN = "([A-z0-9]+)([_.-]?)([A-z0-9]+)@([A-z0-9_.-]+).([A-z]{2,8})";
    public static final String LAST_AND_MIDDLE_NAME_PATTERN = "[A-z]{2,40}";
    public static final String FIRST_NAME_PATTERN = "[A-z]{2,20}";

    private ValidationConstant() {
    }
}
