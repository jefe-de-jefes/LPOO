package com.gympos.util;

import java.util.Random;

import com.gympos.app.AppContext;
import com.gympos.model.Client;
import com.gympos.model.Membership;
import com.gympos.model.Payment;

public class DataSeeder {
    
    private static final String[] NOMBRES = {
        "Juan Pérez", "María López", "Carlos Ruiz", "Ana Torres", "Pedro Sánchez", 
        "Laura Díaz", "Jorge Mina", "Sofía Vargas", "Miguel Ángel", "Lucía Méndez",
        "Ricardo Arjona", "Valentina Vega", "Daniela Romo", "Andrés Iniesta", "Camila Sodi",
        "Fernando Colunga", "Gabriela Spanic", "Héctor Herrera", "Isabel Pantoja", "Javier Bardem"
    };

    public static void seed() {
        if (!AppContext.clientService.getAllClients().isEmpty()) return;

        System.out.println("Generando datos semilla (Clientes, Membresías y Pagos)...");
        Random random = new Random();

        for (int i = 0; i < NOMBRES.length; i++) {
            String id = "C-" + String.format("%03d", i + 1);
            String name = NOMBRES[i];
            String email = name.toLowerCase().replace(" ", ".") + "@gym.com";
            String phone = "555-01" + String.format("%02d", i);
            
            Client c = new Client(id, name, email, phone);
            
            if ((i + 1) % 5 == 0) {
                c.setActive(false);
            }
            
            AppContext.clientService.addClient(c);

            if (c.isActive()) {
                Membership.Type[] types = Membership.Type.values();
                Membership.Type randomType = types[random.nextInt(types.length)];
                
                AppContext.membershipService.createMembership(id, randomType, 0.0);
                
                double monto = AppContext.membershipService.getBasePrice(randomType);
                String paymentId = "P-" + System.currentTimeMillis() + "-" + i;
                String metodo = (i % 2 == 0) ? "CASH" : "CARD";
                
                Payment p = new Payment(paymentId, id, monto, metodo);
                AppContext.paymentService.processPayment(p);
            }
        }
        System.out.println("Datos inicializados correctamente.");
    }
}