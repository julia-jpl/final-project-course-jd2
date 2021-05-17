package com.gmail.portnova.julia.service.constant;

import java.time.format.DateTimeFormatter;

public class TimeFormatterConstant {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private TimeFormatterConstant() {
    }
}
