package com.gympos.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationStore {
    private static String basePath;

    public static void initBasePaths(String path) {
        basePath = path;
    }

    public static void save(String filename, Object object) {
        String filePath = basePath + "/" + filename;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en " + filename, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T load(String filename) {
        String filePath = basePath + "/" + filename;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
