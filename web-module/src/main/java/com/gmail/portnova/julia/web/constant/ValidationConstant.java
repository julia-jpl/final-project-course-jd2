package com.gmail.portnova.julia.web.constant;

public class ValidationConstant {
    public static final String EMAIL_PATTERN = "([A-z0-9]+)([_.-]?)([A-z0-9]+)@([A-z0-9_.-]+).([A-z]{2,8})";
    public static final String LAST_AND_MIDDLE_NAME_PATTERN = "[A-z]{2,40}";
    public static final String FIRST_NAME_PATTERN = "[A-z]{2,20}";
    public static final String TELEPHONE_PATTERN = "[+][0-9]{1,3}-[0-9]{2,3}-[0-9]{3}-[0-9]{2}-[0-9]{2}";
    public static final int ADDRESS_MAX_LENGTH_VALUE = 100;
    public static final int ADDRESS_MIN_LENGTH_VALUE = 5;
    public static final String BIG_DECIMAL_PATTERN = "^[0-9]{1,10}([.]{1}[0-9]{1,})?$";

    private ValidationConstant() {
    }
}
