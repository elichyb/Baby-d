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

public class Weight {
    UUID baby_id;
    double weight;
    String date;

    public Weight(UUID baby_id, double weight, String date) {
        this.baby_id = baby_id;
        this.weight = weight;
        this.date = date;
    }

    public UUID getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(UUID baby_id) {
        this.baby_id = baby_id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
