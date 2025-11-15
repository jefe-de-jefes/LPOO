package com.gympos.model;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
    private String id;
    private String name;
    private String username;
    private String passwordHash;

    public Employee() {}
    public Employee(String id, String name, String username, String passwordHash) {
        this.id = id; this.name = name; this.username = username; this.passwordHash = passwordHash;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee e = (Employee) o; return Objects.equals(id, e.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
