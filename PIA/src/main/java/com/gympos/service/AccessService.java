package com.gympos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.gympos.app.AppContext;
import com.gympos.model.AccessLog;
import com.gympos.model.ClassSchedule;
import com.gympos.model.Client;
import com.gympos.util.Exceptions.AccessDeniedException;
import com.gympos.util.SerializationStore;

public class AccessService {
    private static final String ACCESS_LOGS_FILE = "access_logs.dat";
    private List<AccessLog> accessLogs;

    public AccessService() {
        this.accessLogs = SerializationStore.<List<AccessLog>>load(ACCESS_LOGS_FILE);
        if (this.accessLogs == null) this.accessLogs = new ArrayList<>();
    }

    public List<AccessLog> getAllAccessLogs() {
        return new ArrayList<>(accessLogs);
    }


    public void registerClassEntry(Client client, ClassSchedule schedule) throws AccessDeniedException {
        if (!AppContext.membershipService.hasActiveMembership(client.getId())) {
            throw new AccessDeniedException("Membresía vencida o inexistente.");
        }

        if (schedule.getDay() != LocalDate.now().getDayOfWeek()) {
            throw new AccessDeniedException("Esta clase no está programada para hoy.");
        }

        LocalTime now = LocalTime.now();
        LocalTime classTime = schedule.getTime();
        
        if (now.isBefore(classTime.minusMinutes(5))) {
            long minutesToWait = java.time.temporal.ChronoUnit.MINUTES.between(now, classTime.minusMinutes(5));
            throw new AccessDeniedException("Demasiado temprano. Por favor espere " + (minutesToWait + 1) + " minutos.");
        }

        if (now.isAfter(classTime.plusMinutes(15))) {
            throw new AccessDeniedException("La clase ya comenzó hace mucho. Acceso cerrado.");
        }

        long currentOccupancy = accessLogs.stream()
            .filter(log -> "ENTRADA".equals(log.getAction()))
            .filter(log -> schedule.getId().equals(log.getClassScheduleId()))
            .filter(log -> log.getTimestamp().toLocalDate().equals(LocalDate.now()))
            .count();

        if (currentOccupancy >= schedule.getCapacity()) {
            throw new AccessDeniedException("CUPO LLENO (" + currentOccupancy + "/" + schedule.getCapacity() + "). No puede ingresar.");
        }

        boolean alreadyIn = accessLogs.stream()
            .filter(log -> log.getClientId().equals(client.getId()))
            .filter(log -> schedule.getId().equals(log.getClassScheduleId()))
            .filter(log -> log.getTimestamp().toLocalDate().equals(LocalDate.now()))
            .anyMatch(log -> "ENTRADA".equals(log.getAction()));
            
        if (alreadyIn) {
            throw new AccessDeniedException("El cliente ya registró su entrada a esta clase.");
        }

        AccessLog log = new AccessLog(client.getId(), "ENTRADA", schedule.getId());
        accessLogs.add(log);
        saveAccessLogs();
    }

    public boolean isClientInside(String clientId) {
        List<AccessLog> history = getClientAccessHistory(clientId);
        if (history.isEmpty()) return false;
        AccessLog lastLog = history.get(history.size() - 1);
        return "ENTRADA".equals(lastLog.getAction());
    }

    public void registerEntry(Client client) throws AccessDeniedException {
        if (!AppContext.membershipService.hasActiveMembership(client.getId())) {
            throw new AccessDeniedException("Membresía vencida.");
        }
        if (isClientInside(client.getId())) {
            throw new AccessDeniedException("El cliente ya está dentro.");
        }
        AccessLog log = new AccessLog(client.getId(), "ENTRADA");
        accessLogs.add(log);
        saveAccessLogs();
    }

    public void registerExit(Client client) throws AccessDeniedException {
        if (!isClientInside(client.getId())) {
            throw new AccessDeniedException("El cliente NO ha registrado entrada.");
        }
        AccessLog log = new AccessLog(client.getId(), "SALIDA");
        accessLogs.add(log);
        saveAccessLogs();
    }

    public List<AccessLog> getClientAccessHistory(String clientId) {
        List<AccessLog> history = new ArrayList<>();
        for(AccessLog log : accessLogs) {
            if(log.getClientId().equals(clientId)) history.add(log);
        }
        return history;
    }
    
    private void saveAccessLogs() { SerializationStore.save(ACCESS_LOGS_FILE, accessLogs); }
}