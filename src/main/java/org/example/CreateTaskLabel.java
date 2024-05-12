package org.example;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.font.TextAttribute.STRIKETHROUGH;
import static java.awt.font.TextAttribute.STRIKETHROUGH_ON;

/**
 * In dieser Klasse wird die GUI für die To-do-Listen mit den Aufgaben erstellt.
 * */
public class CreateTaskLabel {
    private final Map<String, Map<String, Boolean>> data;
    private final JPanel panel;

    /**
     * Lädt die übergebenen Daten und erstellt im Anschluss die GUI der To-do-Liste.
     *
     * @param _panel Das {@code JPanel} vom Hauptfenster zum Hinzufügen der To-do-Listen.
     * */
    public CreateTaskLabel(JPanel _panel) {
        DataHandler dataHandler = new DataHandler();

        data = dataHandler.getData();
        panel = _panel;
        List<String> keysList = new ArrayList<>(data.keySet());

        // Erstellt die To-do-Listen mit den Elementen
        for (String name : keysList) {
            createJLabel(name);
        }
    }

    /**
     * Erstellt eine To-do-Liste mit dem übergebenen Namen und fügt diesen in das Panel vom Hauptfenster hinzu.
     *
     * @param name Der Name von der To-do-Liste.
     * */
    private void createJLabel(String name) {
        Font font = new Font("Serif", Font.BOLD, 20);

        // Erstellt die Überschrift der Liste
        JLabel textLabel = new JLabel(name, JLabel.CENTER);
        textLabel.setMinimumSize(new Dimension(50,30));
        textLabel.setFont(font);

        // Erstellt und berechnet die abgeschlossene Prozentzahl
        JLabel percentLabel = new JLabel(getPercent(name), JLabel.RIGHT);
        percentLabel.setPreferredSize(new Dimension(50,50));
        percentLabel.setFont(font);

        // Erstellt Panel für die Überschrift und die Listenelemente
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Erstellt Panel für die Überschrift mit der Prozentzahl
        JPanel titlePanel = new JPanel(new BorderLayout());
        // Fügt die Labels den richtigen Panels hinzu
        titlePanel.add(textLabel, BorderLayout.CENTER);
        titlePanel.add(percentLabel, BorderLayout.EAST);
        mainPanel.add(titlePanel);
        mainPanel.add(createListItems(name), BorderLayout.SOUTH);
        // Erstellt die Border
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 3, true));
        titlePanel.setBorder(new MatteBorder(0,0,2,0,Color.gray));
        // Ermöglicht das Auswählen einer Liste, um diese anzusehen
        mainPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // todo
                System.out.println(name);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        // Fügt den mainPanel zum Panel vom Main Fenster
        panel.add(mainPanel);
    }

    /**
     * Erstellt die Liste mit den Elementen aus der passenden To-do-Liste.
     *
     * @param name Der Name der To-do-Liste.
     * @return Gibt den fertigen Container mit den Elementen aus.
     * */
    private Container createListItems(String name) {
        Container container = new Container();
        container.setLayout(new GridLayout(5,1));
        List<String> keysList = new ArrayList<>(data.get(name).keySet());

        // Erstellt die Elemente der To-do-Liste
        for (int i = 0; i < keysList.size(); i++) {
            String itemName = keysList.get(i);
            boolean itemValue = data.get(name).get(itemName);
            JLabel itemLabel = new JLabel("- " + itemName);
            itemLabel.setFont(new Font("Serif", Font.PLAIN, 16));

            // Wenn das Element als erledigt markiert ist, dann wird es durchgestrichen
            if (itemValue) {
                Font font = itemLabel.getFont();
                Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                attributes.put(STRIKETHROUGH, STRIKETHROUGH_ON);
                Font strikeFont = font.deriveFont(attributes);
                itemLabel.setFont(strikeFont);
            }

            // Wenn die To-do-Liste mehr als 5 Elemente hat, dann soll das letzte Element drei Punkte sein.
            // Die Schleife wird danach abgebrochen
            if (data.get(name).size() > 5 && i == 4) {
                itemLabel.setText("...");
                itemLabel.setHorizontalAlignment(JLabel.CENTER);
                itemLabel.setFont(new Font("Serif", Font.BOLD, 16));
                container.add(itemLabel);
                break;
            }

            container.add(itemLabel);
        }

        return container;
    }

    /**
     * Berechnet die Anzahl der abgeschlossenen Aufgaben in Prozent und gibt diese aus.
     *
     * @param name Der Name der To-do-Liste.
     * @return Gibt die Prozentzahl mit dem Prozentzeichen aus.
     * */
    private String getPercent(String name) {
        DecimalFormat decimalFormat = new DecimalFormat("0"); // Format für eine ganzzahlige Zahl
        double finished = 0;
        double percent;
        Map<String, Boolean> innerMap = data.get(name);

        // Wenn eine Aufgabe bereits erledigt ist, wird der finished Wert erhöht
        for (Boolean value : innerMap.values()) {
            if (value) {
                finished ++;
            }
        }

        percent = finished / data.get(name).size() * 100; // Berechnung der Prozentzahl

        return decimalFormat.format(percent) + " %";
    }
}
