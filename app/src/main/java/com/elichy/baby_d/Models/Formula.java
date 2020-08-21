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

public class Formula implements Serializable {
    UUID baby_id;
    String date;
    String time;
    int amount;
    String feed_type;

    public Formula(UUID baby_id, String date, String time, int amount, String feed_type) {
        this.baby_id = baby_id;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.feed_type = feed_type;
    }
}
