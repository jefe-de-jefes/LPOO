package com.gympos.service;

import java.util.ArrayList;
import java.util.List;

import com.gympos.model.InventoryItem;
import com.gympos.util.SerializationStore;

public class InventoryService {
    private static final String INVENTORY_FILE = "inventory.dat";
    private List<InventoryItem> items;

    public InventoryService() {
        this.items = SerializationStore.<List<InventoryItem>>load(INVENTORY_FILE);
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
    }

    public void addItem(InventoryItem item) {
        items.add(item);
        saveItems();
    }

    public void updateItem(InventoryItem updated) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updated.getId())) {
                items.set(i, updated);
                saveItems();
                return;
            }
        }
    }

    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
        saveItems();
    }

    public InventoryItem getItemById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<InventoryItem> getAllItems() {
        return new ArrayList<>(items);
    }

    private void saveItems() {
        SerializationStore.save(INVENTORY_FILE, items);
    }
}