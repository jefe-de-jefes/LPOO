package com.gympos.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class ClassSchedule implements Serializable {
    private String id;
    private String className;
    private DayOfWeek day;
    private LocalTime time;
    private int duration;
    private int capacity;

    public ClassSchedule(){}
    
    public ClassSchedule(String id, String className, DayOfWeek day, LocalTime time, int duration, int capacity){
        this.id = id; 
        this.className = className; 
        this.day = day; 
        this.time = time; 
        this.duration = duration;
        this.capacity = capacity;
    }

    public String getId(){ return id; }
    public String getClassName(){ return className; }
    public DayOfWeek getDay(){ return day; }
    public LocalTime getTime(){ return time; }
    public int getDuration() { return duration; }
    public int getCapacity(){ return capacity; }
    
    public void setClassName(String n) { this.className = n; }
    public void setDay(DayOfWeek d) { this.day = d; }
    public void setTime(LocalTime t) { this.time = t; }
    public void setDuration(int d) { this.duration = d; }
    public void setCapacity(int c) { this.capacity = c; }

    public LocalTime getEndTime() {
        return time.plusMinutes(duration);
    }
    
    public String getTimeRange() {
        return time + " - " + getEndTime();
    }
}