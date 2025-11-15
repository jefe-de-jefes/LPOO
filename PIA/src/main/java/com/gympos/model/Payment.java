package com.gympos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Payment implements Serializable {
    private String id;
    private String clientId;
    private double amount;
    private LocalDateTime timestamp;
    private String method; // CASH, CARD

    public Payment() {}
    public Payment(String id, String clientId, double amount, String method) {
        this.id = id; this.clientId = clientId; this.amount = amount; this.method = method;
        this.timestamp = LocalDateTime.now();
    }
    public String getId(){ return id; }
    public String getClientId(){ return clientId; }
    public double getAmount(){ return amount; }
    public LocalDateTime getTimestamp(){ return timestamp; }
    public String getMethod(){ return method; }
}
