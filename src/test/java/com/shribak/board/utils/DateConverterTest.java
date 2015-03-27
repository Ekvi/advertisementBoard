package com.shribak.board.utils;

import org.junit.Test;

import static org.junit.Assert.*;


public class DateConverterTest {

    @Test
    public void testConvertDate() {
        long time = 1427285986884L;
        String expected = "25/03/2015 14:19";

        assertEquals(expected, DateConverter.getInstance().convertDate(time));
    }
}
