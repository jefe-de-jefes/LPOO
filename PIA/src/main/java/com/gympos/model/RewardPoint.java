package com.gympos.model;

import java.io.Serializable;

public class RewardPoint implements Serializable {
    private String clientId;
    private int points;
    public RewardPoint(){}
    public RewardPoint(String clientId, int points){ this.clientId = clientId; this.points = points; }
    public String getClientId(){ return clientId; }
    public int getPoints(){ return points; }
    public void add(int p){ points += p; }
    public void redeem(int p){ points = Math.max(0, points - p); }
}
