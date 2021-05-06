package com.gmail.portnova.julia.service.constant;

public class EmailSendingConstant {
    public static final String FROM = "noreply.myproject@gmail.com";
    public static final String SUBJECT = "new password for your account";
    public static final String TEXT = "you've received this email, because your password was changed:" +
            "username: %s" +
            "  new password: %s";
}
