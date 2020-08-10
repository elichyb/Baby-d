package com.elichy.baby_d.Models;

import java.util.UUID;

public class Diaper {
    private UUID baby_id;
    private String date;
    private String time;
    private boolean wet;
    private boolean dirty;

    public Diaper(UUID baby_id, String date, String time, boolean wet, boolean dirty) {
        this.baby_id = baby_id;
        this.date = date;
        this.time = time;
        this.wet = wet;
        this.dirty = dirty;
    }

    public UUID getBabyid() {
        return baby_id;
    }

    public void setBabyid(UUID baby_id) {
        this.baby_id = baby_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isWet() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
