package com.touchstone.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhuwenhong on 2016/12/27.
 */
public class DateTimeUtils {
    private static final Date utilDate = new Date();

    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(utilDate.getTime());
    }

    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(utilDate.getTime());
    }


}
