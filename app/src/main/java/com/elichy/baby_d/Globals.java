/*
 * //----------------------------------------------------------------------------
 * // (C) Copyright Elichy Barak 2020
 * //
 * // The source code for this program is not published or other-
 * // wise divested of its trade secrets, irrespective of what has
 * // been deposited with the U.S. Copyright Office.
 * //
 * //----------------------------------------------------------------------------
 */

package com.elichy.baby_d;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Globals {
    public static final String SERVER_IP = "http://192.168.1.6:8080";
    public static final String TOKEN = "Baby_d_token";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String BABY_ID = "Baby_id";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FOMULA = "formula";
    public static final String BREASTFEED = "breast";


    public static String GET_DATE() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Globals.DATE_FORMAT);
        return LocalDateTime.now().toLocalDate().toString();
    }

    public static String GET_TIME() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Globals.TIME_FORMAT);
        return LocalDateTime.now().toLocalTime().toString();
    }
}
