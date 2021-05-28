package com.gmail.portnova.julia.service.constant;

public class ExceptionMessageConstant {

    public static final String IMPOSSIBLE_TO_DELETE_ITEM_EXCEPTION_MESSAGE = "Item with uuid %s can not be deleted. " +
            "It's still sold by users with uuid %s";
    public static final String ITEM_GROUP_NOT_FOUND_EXCEPTION_MESSAGE = "ItemGroup with name %s was not found";
    public static final String ENTITY_WITH_UUID_NOT_FOUND_EXCEPTION_MESSAGE = "%s with uuid %s was not found";
    public static final String ENTITY_WITH_NAME_NOT_FOUND_EXCEPTION_MESSAGE = "%s with name %s was not found";


    private ExceptionMessageConstant() {
    }
}
