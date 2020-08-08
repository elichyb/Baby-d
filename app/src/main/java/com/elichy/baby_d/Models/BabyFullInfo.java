package com.elichy.baby_d.Models;

public class BabyFullInfo {
    private String dipper;                      // Can be wet or dry
    private Integer feed_amount;                // Can be null if we are on breast feeding
    private double weight;                      // Baby weight
    private String feed_type;                   // Baby feed type for this current time
    private String measure_time;                // Will hold the last time the baby eat
    private String measure_date;                // Will hold the date of today.
    private String breast_side;                 // Last breast side the baby eat from
    private Integer breast_feeding_time_length; // The time the baby breast feed in minutes
    private Integer sleeping_time;              // Time in minutes the baby slept.

    public BabyFullInfo(String dipper, Integer feed_amount, double weight, String feed_type,
                        String measure_time, String measure_date, String breast_side,
                        Integer breast_feeding_time_length, Integer sleeping_time) {
        this.dipper = dipper;
        this.feed_amount = feed_amount;
        this.weight = weight;
        this.feed_type = feed_type;
        this.measure_time = measure_time;
        this.measure_date = measure_date;
        this.breast_side = breast_side;
        this.breast_feeding_time_length = breast_feeding_time_length;
        this.sleeping_time = sleeping_time;
    }

    public String getDipper() {
        return dipper;
    }

    public void setDipper(String dipper) {
        this.dipper = dipper;
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