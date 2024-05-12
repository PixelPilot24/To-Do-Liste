package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * In dieser Klasse wird die GUI für die Erstellung einer neuen Liste erstellt mit den Funktionen.
 * */
public class NewTodo {
    private JFrame mainFrame;
    private JPanel tasksPanel;
    private final JPanel getTasksPanel;
    private JTextArea titelTextArea;
    private int tasksHeight = 150;

    /**
     * Initialisiert das Panel vom Hauptfenster und erstellt das Fenster.
     *
     * @param panel Das {@code JPanel} vom Hauptfenster für das Hinzufügen der neuen Liste.
     * */
    public NewTodo(JPanel panel) {
        getTasksPanel = panel;
        initWindow();
    }

    /**
     * Erstellt das Fenster und im Anschluss die benötigten Widgets.
     * */
    private void initWindow() {
        mainFrame = new JFrame("Neue To-do Liste");
        mainFrame.setSize(new Dimension(300,500));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        createWidgets();
    }

    /**
     * Erstellt die Widgets und fügt diese zum mainFrame hinzu.
     * */
    private void createWidgets() {
        // Erstellung der Panels und der Widgets
        JPanel mainPanel = new JPanel();
        JPanel listPanel = new JPanel();
        JPanel titelPanel = new JPanel();
        tasksPanel = new JPanel(new GridLayout(0,1)); // Panel für Aufgaben
        titelTextArea = new JTextArea(); // Textfeld für den Titel
        JButton addTask = addTaskButton(listPanel); // Button zum Hinzufügen von Aufgaben
        JButton saveButton = saveButton(); // Button zum Speichern
        JScrollPane scrollPane = new JScrollPane(
                mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // Einstellungen für das Textfeld
        titelTextArea.setPreferredSize(new Dimension(180,20));
        titelTextArea.setFont(new Font("Serif", Font.PLAIN, 16));

        // Einstellung für das Listen-Panel
        listPanel.setPreferredSize(new Dimension(200,tasksHeight));

        // Gestaltung des Titel-Panels
        titelPanel.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 2), "Titel"
                ));
        titelPanel.add(titelTextArea);

        // Gestaltung des Aufgaben-Panels
        tasksPanel.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 2), "Aufgaben"
                ));
        tasksPanel.add(addTask);

        // Hinzufügen der Komponenten zum Listen-Panel
        listPanel.add(titelPanel);
        listPanel.add(tasksPanel);
        listPanel.add(saveButton);

        // Hinzufügen des Listen-Panels zum Haupt-Panel und des Haupt-Panels zum Haupt-Frame
        mainPanel.add(listPanel);
        mainFrame.add(scrollPane);
    }

    /**
     * Erstellt einen Button zum Hinzufügen von neuen Aufgaben.
     *
     * @param listPanel Das {@code JPanel}, das nach dem Hinzufügen oder Entfernen der Aufgabe aktualisiert wird.
     * @return Der erstellte {@code JButton} zum Hinzufügen neuer Aufgaben.
     * */
    private JButton addTaskButton(JPanel listPanel) {
        // Erstellung und Gestaltung des Buttons zum Hinzufügen neuer Aufgaben
        JButton addTask = new JButton("Neue Aufgabe");
        addTask.setPreferredSize(new Dimension(190,30));
        addTask.addActionListener(_ -> {
            // Die Höhe der gesamten Aufgabenliste wird um die Höhe eines neuen Aufgaben-Elements (30 Pixel)
            // und etwas Platz erhöht, um eine saubere Anordnung der Elemente zu gewährleisten
            tasksHeight += 30;
            // Erstellung vom Panel, Textfeld und dem Löschen-Button für die neue Aufgabe
            JPanel newTask = new JPanel();
            JTextArea taskTextArea = new JTextArea();
            JButton deleteTask = new JButton("X");

            // Gestaltung des Löschen-Buttons
            deleteTask.setForeground(Color.red);
            deleteTask.addActionListener(_ -> {
                // Die Höhe der Aufgabenliste wird angepasst und aktualisiert, das aktuelle Aufgaben-Element
                // wird entfernt und die Liste wird aktualisiert
                tasksHeight -= 30;
                tasksPanel.remove(newTask);
                tasksPanel.revalidate();
                listPanel.setPreferredSize(new Dimension(200,tasksHeight));
            });

            // Gestaltung des Textfeldes und Löschen-Buttons für eine einheitliche Größe
            taskTextArea.setPreferredSize(new Dimension(130, 20));
            deleteTask.setPreferredSize(new Dimension(45,20));

            // Widgets werden zum Panel hinzugefügt
            newTask.add(taskTextArea);
            newTask.add(deleteTask);

            // Panel wird der Aufgabenliste hinzugefügt und aktualisiert
            tasksPanel.add(newTask);
            tasksPanel.revalidate();

            // Die Höhe des Listen-Panels wird angepasst
            listPanel.setPreferredSize(new Dimension(200,tasksHeight));
        });

        return addTask;
    }

    /**
     * Erstellt einen Button zum Speichern von der eingegebenen To-do-Liste.
     *
     * @return Gibt den erstellten {@code JButton} aus.
     * */
    private JButton saveButton() {
        // Erstellung des Buttons zum Speichern von der neuen To-do-Liste
        JButton saveButton = new JButton("Speichern");
        saveButton.addActionListener(_ -> {
            // Lädt die JSON Datei
            DataHandler dataHandler = new DataHandler();
            Map<String, Map<String, Boolean>> jsonMap = dataHandler.getData();
            
            String titel = titelTextArea.getText(); // Der eingegebene Titel
            boolean titelExists = jsonMap.containsKey(titel); // Überprüft ob der Titel existiert

            if (titelExists) {
                // Wenn der Titel existiert, dann wird ein Messagedialog angezeigt
                JOptionPane.showMessageDialog(
                        new JOptionPane(),
                        "Dieser Titel ist bereits vorhanden",
                        "Titel",
                        JOptionPane.ERROR_MESSAGE
                );
            } else if (titel.isEmpty()) {
                // Wenn der eingegebene Titel leer ist, wird ein Messagedialog angezeigt
                JOptionPane.showMessageDialog(
                        new JOptionPane(),
                        "Der Titel darf nicht leer sein",
                        "Titel",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                Map<String, Boolean> tasksMap = new HashMap<>(); // Eine Map für die erstellten Aufgaben
                
                for (int i = 1; i < tasksPanel.getComponentCount(); i++) {
                    // Die for Schleife fängt bei 1 an, weil das erste Element der Button zum Hinzufügen
                    // neuer Aufgaben ist.
                    // Für jede erstellte Aufgabe wird der Name mit dem false Boolean in die Map eingefügt.
                    // Es wird false benutzt, weil die Aufgabe noch nicht abgeschlossen ist
                    JPanel das = (JPanel) tasksPanel.getComponent(i);
                    JTextArea der = (JTextArea) das.getComponent(0);
                    tasksMap.put(der.getText(), false);
                }
                
                // Die neue To-do-Liste wird mit den Aufgaben zu den vorhandenen Dateien hinzugefügt
                // und gespeichert
                jsonMap.put(titel, tasksMap);
                dataHandler.saveDataToJson(jsonMap);
                
                JOptionPane.showMessageDialog(
                        new JOptionPane(),
                        "To-Do erfolgreich gespeichert",
                        "Speichern",
                        JOptionPane.INFORMATION_MESSAGE
                ); // Zeigt den Messagedialog das die Daten erfolgreich gespeichert wurden

                // Erstellt eine neue To-do-Liste und aktualisiert das Panel vom Hauptfenster
                new CreateTaskLabel(getTasksPanel).createJLabel(titel);
                getTasksPanel.revalidate();
            }
        });

        return saveButton;
    }
}
