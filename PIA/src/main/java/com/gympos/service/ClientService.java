package com.gympos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gympos.model.Client;
import com.gympos.util.SerializationStore;

public class ClientService {
    private static final String CLIENTS_FILE = "clients.dat";
    private List<Client> clients;

    public ClientService() {
        this.clients = SerializationStore.<List<Client>>load(CLIENTS_FILE);
        if (this.clients == null) {
            this.clients = new ArrayList<>();
        }
    }

    public void addClient(Client client) {
        clients.add(client);
        saveClients();
    }

    public void updateClient(Client updated) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(updated.getId())) {
                clients.set(i, updated);
                saveClients();
                return;
            }
        }
    }

    public void removeClient(String clientId) {
        clients.removeIf(c -> c.getId().equals(clientId));
        saveClients();
    }

    public Client getClientById(String clientId) {
        return clients.stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    private void saveClients() {
        SerializationStore.save(CLIENTS_FILE, clients);
    }
    public String generateNextId() {
        int nextId = clients.size() + 1;
        return "C-" + String.format("%03d", nextId);
    }

    public void deactivateClient(String clientId) {
        Client c = getClientById(clientId);
        if (c != null) {
            c.setActive(false);
            saveClients();
        }
    }

    public List<Client> getActiveClients() {
        return clients.stream().filter(Client::isActive).collect(Collectors.toList());
    }
}