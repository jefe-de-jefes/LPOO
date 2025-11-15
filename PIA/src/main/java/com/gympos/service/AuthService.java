package com.gympos.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gympos.model.Employee;
import com.gympos.util.Exceptions.AuthenticationException;
import com.gympos.util.Exceptions.ValidationException;
import com.gympos.util.SerializationStore;

public class AuthService {
    private static final String EMPLOYEES_FILE = "employees.dat";
    private final List<Employee> employees;

    public AuthService(List<Employee> employees) {
        this.employees = employees;
    }

    //CRUD
    public void addEmployee(Employee employee) {
        if (getEmployeeByUsername(employee.getUsername()).isPresent()) {
            throw new ValidationException("El nombre de usuario ya existe.");
        }
        employee.setPasswordHash(hashPassword(employee.getPasswordHash()));
        employees.add(employee);
        saveEmployees(employees);
    }

    public void updateEmployee(Employee updated) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(updated.getId())) {
                employees.set(i, updated);
                saveEmployees(employees);
                return;
            }
        }
        throw new ValidationException("Empleado no encontrado.");
    }

    public void removeEmployee(String employeeId) {
        boolean removed = employees.removeIf(e -> e.getId().equals(employeeId));
        if (!removed) {
            throw new ValidationException("Empleado no encontrado.");
        }
        saveEmployees(employees);
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public Optional<Employee> getEmployeeByUsername(String username) {
        return employees.stream().filter(e -> e.getUsername().equals(username)).findFirst();
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Employee login(String username, String password) {
        String hash = hashPassword(password);
        return employees.stream()
                .filter(e -> e.getUsername().equals(username) && e.getPasswordHash().equals(hash))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Usuario o contraseña incorrectos."));
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No se pudo hashear la contraseña", e);
        }
    }

    public static List<Employee> loadEmployees() {
        List<Employee> loaded = SerializationStore.<List<Employee>>load(EMPLOYEES_FILE);
        return loaded != null ? loaded : new ArrayList<>();
    }

    public static void saveEmployees(List<Employee> employees) {
        SerializationStore.save(EMPLOYEES_FILE, employees);
    }
}