package com.gympos.model;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String id;
    private String name;
    private int quantity;
    private String location;

    public InventoryItem(){}
    public InventoryItem(String id, String name, int quantity, String location){
        this.id=id; this.name=name; this.quantity=quantity; this.location=location;
    }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public int getQuantity(){ return quantity; }
    public String getLocation(){ return location; }
    public void setLocation(String location) { this.location = location; }
    public void setQuantity(int q){ this.quantity=q; }
}
