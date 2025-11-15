package com.gympos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gympos.model.Membership;
import com.gympos.util.Exceptions.MembershipException;
import com.gympos.util.SerializationStore;
import com.gympos.util.Threading;

public class MembershipService {
    private static final String MEMBERSHIPS_FILE = "memberships.dat";
    private List<Membership> memberships;

    // Precios base (Personalizables con tu matrícula si lo piden las reglas)
    private static final double FLORES_PRICE = 6033; 
    private static final double ZACATENCO_PRICE = 7791;
    private static final double DAVILA_PRICE = 4122;
    private static final double SEGOBIA_PRICE = 7528;

    public MembershipService() {
        this.memberships = SerializationStore.<List<Membership>>load(MEMBERSHIPS_FILE);
        if (this.memberships == null) {
            this.memberships = new ArrayList<>();
        }
    }

    public List<Membership> getAllMemberships() {
        return new ArrayList<>(memberships);
    }

    public Membership createMembership(String clientId, Membership.Type type, double discount) {
        if (hasActiveMembership(clientId)) {
            throw new MembershipException("El cliente ya tiene una membresía activa. Use la opción de renovar.");
        }
        return registerNewMembership(clientId, type, discount);
    }

    // MÉTODO MEJORADO: Unifica renovación y extensión
    public void renewMembership(String clientId, Membership.Type type, double pricePaid) {
        Membership current = getCurrentMembership(clientId);
        
        if (current != null) {
            // Opción A: Extender la vigente
            LocalDate newEnd = calculateEndDate(current.getEnd(), type);
            current.setEnd(newEnd);
            saveMemberships();
        } else {
            // Opción B: Crear nueva (la anterior ya venció)
            // Asumimos 0 descuento en renovación automática o pásalo como argumento si lo tienes
            registerNewMembership(clientId, type, 0.0);
        }
    }

    // Método auxiliar privado para evitar duplicar código de creación
    private Membership registerNewMembership(String clientId, Membership.Type type, double discount) {
        LocalDate start = LocalDate.now();
        LocalDate end = calculateEndDate(start, type);
        double basePrice = getBasePrice(type);
        double finalPrice = basePrice * (1 - discount);

        Membership membership = new Membership(clientId, type, start, end, finalPrice, discount);
        memberships.add(membership);
        saveMemberships();
        return membership;
    }

    public void cancelMembership(String clientId) {
        Membership current = getCurrentMembership(clientId);
        if (current != null) {
            current.setEnd(LocalDate.now().minusDays(1));
            saveMemberships();
        }
    }

    public boolean hasActiveMembership(String clientId) {
        return memberships.stream()
            .filter(m -> m.getClientId().equals(clientId))
            .anyMatch(m -> m.getEnd().isAfter(LocalDate.now()));
    }

    public Membership getCurrentMembership(String clientId) {
        return memberships.stream()
            .filter(m -> m.getClientId().equals(clientId))
            .filter(m -> m.getEnd().isAfter(LocalDate.now()))
            .findFirst()
            .orElse(null);
    }

    public List<Membership> getExpiringMemberships(int daysThreshold) {
        LocalDate threshold = LocalDate.now().plusDays(daysThreshold);
        return memberships.stream()
            .filter(m -> m.getEnd().isBefore(threshold) && m.getEnd().isAfter(LocalDate.now()))
            .collect(Collectors.toList());
    }

    public void scheduleExpiringNotifications() {
        Threading.submitTask(() -> {
            while (true) {
                try {
                    Thread.sleep(3600000); 
                    List<Membership> expiring = getExpiringMemberships(7);
                    
                    if (!expiring.isEmpty()) {
                        javafx.application.Platform.runLater(() -> {
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                            alert.setTitle("Alertas de Vencimiento");
                            alert.setHeaderText("Membresías por vencer pronto");
                            alert.setContentText("Se detectaron " + expiring.size() + " membresías próximas a vencer.");
                            alert.show();
                        });
                    }
                } catch (InterruptedException e) { break; }
            }
        });
    }

    public LocalDate calculateEndDate(LocalDate start, Membership.Type type) {
        return switch (type) {
            case BASIC, STANDARD, PREMIUM -> start.plusMonths(1);
            case ANUAL -> start.plusYears(1);
        };
    }

    public double getBasePrice(Membership.Type type) {
        return switch (type) {
            case BASIC -> DAVILA_PRICE;
            case STANDARD -> FLORES_PRICE;
            case PREMIUM -> SEGOBIA_PRICE;
            case ANUAL -> ZACATENCO_PRICE;
        };
    }

    private void saveMemberships() {
        SerializationStore.save(MEMBERSHIPS_FILE, memberships);
    }
}