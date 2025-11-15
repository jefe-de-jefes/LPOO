package com.gympos.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Membership implements Serializable {
    public enum Type { BASIC, STANDARD, PREMIUM, ANUAL }
    private String clientId;
    private Type type;
    private LocalDate start;
    private LocalDate end;
    private double pricePaid;
    private double discountApplied;

    public Membership() {}
    public Membership(String clientId, Type type, LocalDate start, LocalDate end, double pricePaid, double discountApplied) {
        this.clientId = clientId; this.type = type; this.start = start; this.end = end;
        this.pricePaid = pricePaid; this.discountApplied = discountApplied;
    }
    public String getClientId() { return clientId; }
    public Type getType() { return type; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
    public double getPricePaid() { return pricePaid; }
    public double getDiscountApplied() { return discountApplied; }
    public void setEnd(LocalDate end){ this.end = end; }
}
