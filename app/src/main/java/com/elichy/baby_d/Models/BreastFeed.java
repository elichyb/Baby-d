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

package com.elichy.baby_d.Models;

import java.io.Serializable;
import java.util.UUID;

public class BreastFeed implements Serializable {
    UUID baby_id;
    String date;
    String time;
    String breast_side;
    int breast_feeding_time_length;
    String feed_type;

    public BreastFeed(UUID baby_id, String date, String time, String breast_side, int breast_feeding_time_length, String feed_type) {
        this.baby_id = baby_id;
        this.date = date;
        this.time = time;
        this.breast_side = breast_side;
        this.breast_feeding_time_length = breast_feeding_time_length;
        this.feed_type = feed_type;
    }
}
