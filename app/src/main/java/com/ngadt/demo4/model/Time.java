package com.ngadt.demo4.model;

import java.io.Serializable;

public class Time implements Serializable {
    private int id;
    private String hour;
    private String minutes;
    private String name;
    private int checked;

    public Time(int anInt, String string, String string1, String string2, String string3) {
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public Time(String hour, String minutes, String name, int checked) {
        this.hour = hour;
        this.minutes = minutes;
        this.name = name;
        this.checked = checked;
    }

    public Time(String hour, String minutes, int checked) {
        this.hour = hour;
        this.minutes = minutes;
        this.checked = checked;
    }

    public Time(int id, String hour, String minutes, String name, int checked) {
        this.id = id;
        this.hour = hour;
        this.minutes = minutes;
        this.name = name;
        this.checked = checked;
    }

    public Time(int id, String hour, String minutes, String name) {
        this.id = id;
        this.hour = hour;
        this.minutes = minutes;
        this.name = name;
    }

    public Time(String hour, String minutes, String name) {
        this.hour = hour;
        this.minutes = minutes;
        this.name = name;
    }


    public Time(String hour, String minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Time() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", hour='" + hour + '\'' +
                ", minutes='" + minutes + '\'' +
                ", name='" + name + '\'' +
                ", checked=" + checked +
                '}';
    }
}
