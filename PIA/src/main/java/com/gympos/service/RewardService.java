package com.gympos.service;

import java.util.ArrayList;
import java.util.List;

import com.gympos.model.RewardPoint;
import com.gympos.util.SerializationStore;

public class RewardService {
    private static final String REWARDS_FILE = "rewards.dat";
    private List<RewardPoint> rewards;

    public RewardService() {
        this.rewards = SerializationStore.<List<RewardPoint>>load(REWARDS_FILE);
        if (this.rewards == null) {
            this.rewards = new ArrayList<>();
        }
    }

    public RewardPoint getPoints(String clientId) {
        return rewards.stream()
                .filter(r -> r.getClientId().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    public void addPoints(String clientId, int points) {
        RewardPoint rp = getPoints(clientId);
        if (rp == null) {
            rp = new RewardPoint(clientId, 0);
            rewards.add(rp);
        }
        rp.add(points);
        save();
    }

    public void redeemPoints(String clientId, int pointsToRedeem) {
        RewardPoint rp = getPoints(clientId);
        if (rp != null) {
            rp.redeem(pointsToRedeem);
            save();
            System.out.println("Puntos canjeados para " + clientId + ": " + pointsToRedeem);
        }
    }

    private void save() {
        SerializationStore.save(REWARDS_FILE, rewards);
    }
}