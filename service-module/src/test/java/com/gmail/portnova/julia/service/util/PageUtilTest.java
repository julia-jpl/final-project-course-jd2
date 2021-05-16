package com.gmail.portnova.julia.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageUtilTest {

    @Test
    void shouldGetNumberOfPages() {
        Long numberOfRows = 25L;
        int maxResult = 2;
        assertEquals(13, PageUtil.getNumberOfPages(numberOfRows, maxResult));

        numberOfRows = 2L;
        maxResult = 3;
        assertEquals(1, PageUtil.getNumberOfPages(numberOfRows, maxResult));
    }

    @Test
    void shouldGetNumberOfPagesWhenNumberOfRowsIsZero() {
        Long numberOfRows = 0L;
        int maxResult = 2;
        assertEquals(1, PageUtil.getNumberOfPages(numberOfRows, maxResult));
    }

    @Test
    void ShouldGetStartPosition() {
        int page = 1;
        int maxResult = 10;
        assertEquals(0, PageUtil.getStartPosition(page, maxResult));
    }
}