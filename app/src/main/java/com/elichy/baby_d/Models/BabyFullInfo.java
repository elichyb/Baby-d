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

public class BabyFullInfo {
    private String measure_date;                // Will hold the date of today.
    private String measure_time;                // Will hold the hour in today.
    private double weight;                      // Baby weight
    private boolean wet_dipper;                  // hold if baby had a wet diaper
    private boolean dirty_dipper;                // hold if baby had a dirty diaper
    private int feed_amount;                    // Can be null if we are on breast feeding
    private String breast_side;                 // Last breast side the baby eat from
    private Integer breast_feeding_time_length; // The time the baby breast feed in minutes
    private Integer sleeping_time;              // Time in minutes the baby slept.
    private String feed_type;                   // Baby feed type for this current time

    public BabyFullInfo(String measure_date, String measure_time, double weight, boolean wet_dipper,
                        boolean dirty_dipper, int feed_amount, String breast_side,
                        Integer breast_feeding_time_length, Integer sleeping_time, String feed_type)
    {
        this.measure_date = measure_date;
        this.measure_time = measure_time;
        this.weight = weight;
        this.wet_dipper = wet_dipper;
        this.dirty_dipper = dirty_dipper;
        this.feed_amount = feed_amount;
        this.breast_side = breast_side;
        this.breast_feeding_time_length = breast_feeding_time_length;
        this.sleeping_time = sleeping_time;
        this.feed_type = feed_type;
    }

    public boolean getWet_dipper() {
        return wet_dipper;
    }

    public void setWet_dipper(boolean wet_dipper) {
        this.wet_dipper = wet_dipper;
    }

    public boolean getDirty_dipper() {
        return dirty_dipper;
    }

    public void setDirty_dipper(boolean dirty_dipper) {
        this.dirty_dipper = dirty_dipper;
    }

    public void setFeed_amount(int feed_amount) {
        this.feed_amount = feed_amount;
    }

    public Integer getFeed_amount() {
        return feed_amount;
    }

    public void setFeed_amount(Integer feed_amount) {
        this.feed_amount = feed_amount;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public String getMeasure_time() {
        return measure_time;
    }

    public void setMeasure_time(String measure_time) {
        this.measure_time = measure_time;
    }

    public String getMeasure_date() {
        return measure_date;
    }

    public void setMeasure_date(String measure_date) {
        this.measure_date = measure_date;
    }

    public String getBreast_side() {
        return breast_side;
    }

    public void setBreast_side(String breast_side) {
        this.breast_side = breast_side;
    }

    public Integer getBreast_feeding_time_length() {
        return breast_feeding_time_length;
    }

    public void setBreast_feeding_time_length(Integer breast_feeding_time_length) {
        this.breast_feeding_time_length = breast_feeding_time_length;
    }

    public Integer getSleeping_time() {
        return sleeping_time;
    }

    public void setSleeping_time(Integer sleeping_time) {
        this.sleeping_time = sleeping_time;
    }
}
