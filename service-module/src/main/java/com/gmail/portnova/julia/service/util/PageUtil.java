package com.gmail.portnova.julia.service.util;

public class PageUtil {

    public static Long getNumberOfPages(Long numberOfRows, int maxResult) {
        if (numberOfRows % maxResult == 0 && numberOfRows != 0) {
            return numberOfRows / maxResult;
        } else {
            return numberOfRows / maxResult + 1;
        }
    }

    public static int getStartPosition(int page, int maxResult) {
        return maxResult * (page - 1);
    }
}
