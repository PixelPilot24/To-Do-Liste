package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * In der Main Klasse wird die GUI für die To-do-Liste erstellt.
 * */
public class Main {
    protected JFrame mainFrame;
    private JPanel panel;

    /**
     * Erstellt die GUI für die To-do-Liste.
     * Erstellt das Hauptfenster, die Menüleiste und fügt die To-do-Listen ins Hauptfenster.
     * */
    private void createGUI() {
        mainFrame = new JFrame("To Do");
        mainFrame.setSize(500,500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(0,1));

        JPanel controlPanel = new JPanel(new FlowLayout());
        mainFrame.add(controlPanel);

        panel = new JPanel();
        // Erstellung, gestaltung und Hinzufügung des Layouts zum Panel
        GridLayout gridLayout = new GridLayout(0,5);
        gridLayout.setHgap(20);
        gridLayout.setVgap(20);
        panel.setLayout(gridLayout);

        createJMenuBar();

        Map<String, Map<String, Boolean>> data = new DataHandler().getData();
        List<String> keysList = new ArrayList<>(data.keySet()); // Liste der Namen der To-do-Listen

        // Erstellt die To-do-Listen mit den Elementen
        for (String name : keysList) {
            new CreateTaskLabel(panel).createJLabel(name);
        }

        controlPanel.add(panel);

        // Wenn es keine Elemente gibt, dann soll das Fenster nicht angepasst werden
        if (panel.getComponentCount() > 0) {
            mainFrame.pack();
        }

        mainFrame.setVisible(true);
    }

    /**
     * Erstellt die Menüleiste und fügt die Elemente hinzu.
     * */
    private void createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu datei = new JMenu("Datei");
        JMenuItem newList = new JMenuItem("Neue Liste erstellen");
        JMenuItem close = new JMenuItem("Schließen");

        // ActionListener für die Menüitems
        newList.addActionListener(_ -> new NewTodo(panel));
        close.addActionListener(_ -> System.exit(0));

        // Fügt die Menüitems dem Dateiitem
        datei.add(newList);
        datei.addSeparator();
        datei.add(close);
        menuBar.add(datei);

        mainFrame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.createGUI();
    }
}