package com.gmail.portnova.julia.service.util;

import com.gmail.portnova.julia.service.model.ItemGroupNameEnumDTO;

public class ItemNumberGenerator {
    public static String getItemUniqueNumber(String itemGroup) {
        Long numberCode = System.nanoTime();
        String groupCode = null;
        ItemGroupNameEnumDTO itemGroupNameEnumDTO = ItemGroupNameEnumDTO.valueOf(itemGroup);
        switch (itemGroupNameEnumDTO) {
            case ELECTRONICS:
                groupCode = "EL";
                break;
            case FASHION:
                groupCode = "FA";
                break;
            case SPORTS:
                groupCode = "SP";
                break;
            case HEALTH_BEAUTY:
                groupCode = "HB";
                break;
            default:
                break;
        }
        return String.join("-", groupCode, numberCode.toString());
    }
}
