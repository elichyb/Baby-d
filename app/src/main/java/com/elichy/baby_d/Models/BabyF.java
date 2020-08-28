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

import java.util.UUID;

public class BabyF {
    UUID baby_id;
    String date;

    public BabyF(UUID baby_id, String date) {
        this.baby_id = baby_id;
        this.date = date;
    }

}
