package com.gympos.service;

import java.util.ArrayList;
import java.util.List;

import com.gympos.model.Payment;
import com.gympos.util.Exceptions.PaymentException;
import com.gympos.util.SerializationStore;

public class PaymentService {
    private static final String PAYMENTS_FILE = "payments.dat";
    private List<Payment> payments;

    public PaymentService() {
        this.payments = SerializationStore.<List<Payment>>load(PAYMENTS_FILE);
        if (this.payments == null) {
            this.payments = new ArrayList<>();
        }
    }

    public void processPayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new PaymentException("El monto debe ser mayor a cero");
        }
        payments.add(payment);
        savePayments();
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }

    private void savePayments() {
        SerializationStore.save(PAYMENTS_FILE, payments);
    }
}