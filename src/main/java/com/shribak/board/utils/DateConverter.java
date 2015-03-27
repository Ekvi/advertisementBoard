package com.shribak.board.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code DateConverter} class converts date storing as a unix time
 * to dd/MM/yyyy HH:mm format.
 *
 * @author Dmitrii Shribak
 */
public class DateConverter {
    private static DateConverter instance;

    private DateConverter(){}

    public static DateConverter getInstance() {
        if(instance == null) {
            instance = new DateConverter();
        }
        return instance;
    }

    /**
     * Receives milliseconds and converts it to 'dd/MM/yyyy HH:mm' format.
     *
     * @param date  milliseconds
     * @return date in 'dd/MM/yyyy HH:mm' format
     */
    public String convertDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date resultDate = new Date(date);

        return sdf.format(resultDate);
    }
}
