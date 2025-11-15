package com.gympos.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.gympos.model.ClassSchedule;
import com.gympos.util.SerializationStore;

public class ScheduleService {
    private static final String SCHEDULES_FILE = "class_schedules.dat";
    private List<ClassSchedule> schedules;

    public ScheduleService() {
        this.schedules = SerializationStore.<List<ClassSchedule>>load(SCHEDULES_FILE);
        if (this.schedules == null) {
            this.schedules = new ArrayList<>();
        }
    }

    private boolean isOverlapping(ClassSchedule newItem, String excludeId) {
        return schedules.stream().anyMatch(existing -> {
            if (existing.getId().equals(excludeId)) return false;
            
            if (existing.getDay() != newItem.getDay()) return false;

            LocalTime startA = existing.getTime();
            LocalTime endA = existing.getEndTime();
            LocalTime startB = newItem.getTime();
            LocalTime endB = newItem.getEndTime();

            return startA.isBefore(endB) && endA.isAfter(startB);
        });
    }

    public void addClass(ClassSchedule classSchedule) {
        if (isOverlapping(classSchedule, null)) {
            throw new IllegalArgumentException("¡Conflicto! Ya existe una clase en ese horario (" + classSchedule.getDay() + ").");
        }
        schedules.add(classSchedule);
        saveSchedules();
    }

    public void updateClass(ClassSchedule updated) {
        if (isOverlapping(updated, updated.getId())) {
            throw new IllegalArgumentException("¡Conflicto al actualizar! El nuevo horario choca con otra clase.");
        }

        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getId().equals(updated.getId())) {
                schedules.set(i, updated);
                saveSchedules();
                return;
            }
        }
    }

    public void removeClass(String id) {
        schedules.removeIf(s -> s.getId().equals(id));
        saveSchedules();
    }

    public List<ClassSchedule> getAllClasses() {
        return new ArrayList<>(schedules);
    }

    private void saveSchedules() {
        SerializationStore.save(SCHEDULES_FILE, schedules);
    }
}