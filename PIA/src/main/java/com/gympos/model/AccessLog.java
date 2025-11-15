package com.gympos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccessLog implements Serializable {
    private String clientId;
    private String classScheduleId;
    private LocalDateTime timestamp;
    private String action; // ENTRADA / SALIDA

    public AccessLog(){}
    
    public AccessLog(String clientId, String action, String classScheduleId){
        this.clientId = clientId;
        this.action = action;
        this.classScheduleId = classScheduleId;
        this.timestamp = LocalDateTime.now();
    }

    public AccessLog(String clientId, String action){
        this(clientId, action, null);
    }

    public String getClientId(){ return clientId; }
    public LocalDateTime getTimestamp(){ return timestamp; }
    public String getAction(){ return action; }
    public String getClassScheduleId() { return classScheduleId; }
}