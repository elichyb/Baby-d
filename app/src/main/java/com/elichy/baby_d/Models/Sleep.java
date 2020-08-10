package com.elichy.baby_d.Models;

import java.util.UUID;


// This module will represent the data we will send to the server :)
public class Sleep {
    private UUID baby_id;
    private String date;
    private String time;
    private int sleeping_time;

    public Sleep(UUID baby_id, String date, String time, int sleeping_time) {
        this.baby_id = baby_id;
        this.date = date;
        this.time = time;
        this.sleeping_time = sleeping_time;
    }

    public UUID getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(UUID baby_id) {
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

    public int getSleeping_time() {
        return sleeping_time;
    }

    public void setSleeping_time(int sleeping_time) {
        this.sleeping_time = sleeping_time;
    }
}
