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
