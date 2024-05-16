package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse von der die JSON Datei geladen und gespeichert werden kann.
 * */
public class DataHandler {
    private final ObjectMapper objectMapper;
    private Map<String, Map<String, List<Object>>> jsonData = new HashMap<>();

    public DataHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Speichert die übergebene {@code Map} als JSON Datei. Bei einem Fehler wird ein Fehlerfenster angezeigt.
     *
     * @param data Die {@code Map} mit den Daten die gespeichert werden sollen.
     * */
    public void saveDataToJson(Map<String, Map<String, List<Object>>> data) {
        try {
            objectMapper.writeValue(new File("data.json"), data);
            jsonData = data;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    new JOptionPane(),
                    "Fehler beim Speichern der Daten in JSON:\n" + e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Lädt die JSON Datei und formatiert diese in eine {@code Map}.
     * */
    public void loadDataFromJson() {
        Path path = Path.of("data.json");
        boolean fileExists = path.toFile().exists();

        // Falls die Datei nicht existiert, wird eine neue erstellt
        if (fileExists) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                jsonData = objectMapper.readValue(bytes, new TypeReference<HashMap<String, Map<String, List<Object>>>>() {
                });
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        new JOptionPane(),
                        "Fehler beim Laden der Daten aus JSON:\n" + e.getMessage(),
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            try {
                objectMapper.writeValue(new File("data.json"), jsonData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Wenn die jsonData leer ist, wird die JSON Datei geladen und im Anschluss ausgegeben.
     * */
    public Map<String, Map<String, List<Object>>> getData() {
        if (jsonData.isEmpty()) {
            loadDataFromJson();
        }

        return jsonData;
    }
}
