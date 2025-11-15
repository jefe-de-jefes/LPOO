package com.gympos.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Client implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private boolean active;
    private LocalDate createdAt;

    public Client() {}
    public Client(String id, String fullName, String email, String phone) {
        this.id = id; this.fullName = fullName; this.email = email; this.phone = phone; this.active = true;
        this.createdAt = LocalDate.now();
    }
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getCreatedAt() { return createdAt; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override public boolean equals(Object o){ 
        return o instanceof Client && Objects.equals(id, ((Client)o).id); 
    }
    @Override public int hashCode(){ return Objects.hash(id); }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public String getStatusString() { return active ? "Activo" : "Inactivo"; }
}
