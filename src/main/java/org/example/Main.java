package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * In der Main Klasse wird die GUI für die To-do-Liste erstellt.
 * */
public class Main {
    protected JFrame mainFrame;

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

        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(0,5);
        gridLayout.setHgap(20);
        gridLayout.setVgap(20);
        panel.setLayout(gridLayout);
        createJMenuBar();

        // Erstellt die To-do-Listen mit den Elementen
        new CreateTaskLabel(panel);

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
        // todo weitermachen: Erstellung und Speicherung von einer neuen To-do-Liste
        //close.addActionListener(_ -> System.exit(0));
        close.addActionListener(_ -> System.out.println(new DataHandler().getData()));

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