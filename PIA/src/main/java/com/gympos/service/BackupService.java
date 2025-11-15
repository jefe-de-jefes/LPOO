package com.gympos.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private static final String DATA_DIR = "./data"; 
    private static final String BACKUP_DIR = "backups";

    public void scheduleNightlyBackup() {
        System.out.println("Respaldo nocturno programado.");
    }

    public void manualBackup() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File backupFolder = new File(BACKUP_DIR, "backup_" + timestamp);
            
            if (!backupFolder.exists()) {
                backupFolder.mkdirs();
            }

            File dataFolder = new File(DATA_DIR);
            if (dataFolder.exists() && dataFolder.isDirectory()) {
                File[] files = dataFolder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Files.copy(file.toPath(),
                                new File(backupFolder, file.getName()).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
                System.out.println("Backup realizado en: " + backupFolder.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar backup: " + e.getMessage());
        }
    }
}