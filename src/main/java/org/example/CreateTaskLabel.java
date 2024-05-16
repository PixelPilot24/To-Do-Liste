package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final JFrame mainWindowFrame;
    private final Map<String, Map<String, List<Object>>> data;
    private final JPanel panel;

    /**
     * Lädt die übergebenen Daten.
     *
     * @param _panel Das {@code JPanel} vom Hauptfenster zum Hinzufügen der To-do-Listen.
     * */
    public CreateTaskLabel(JPanel _panel, JFrame frame) {
        mainWindowFrame = frame;
        data = new DataHandler().getData();
        panel = _panel;
    }

    /**
     * Erstellt eine To-do-Liste mit dem übergebenen Namen und fügt diesen in das Panel vom Hauptfenster hinzu.
     *
     * @param name Der Name von der To-do-Liste.
     * */
    public void createJLabel(String name) {
        Font font = new Font("Serif", Font.BOLD, 20);

        // Erstellt die Überschrift der Liste
        JLabel textLabel = new JLabel(name, JLabel.CENTER);
        textLabel.setMinimumSize(new Dimension(50,30));
        textLabel.setFont(font);

        // Erstellt und berechnet die abgeschlossene Prozentzahl
        JLabel percentLabel = new JLabel(getPercent(name), JLabel.RIGHT);
        percentLabel.setPreferredSize(new Dimension(70,50));
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
                new ShowTodo(name, panel, mainWindowFrame).run();
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
        List<Object> taskNames = new ArrayList<>(data.get(name).get("name"));
        List<Object> taskBools = new ArrayList<>(data.get(name).get("bool"));

        // Erstellt die Elemente der To-do-Liste
        for (int i = 0; i < taskNames.size(); i++) {
            String itemName = (String) taskNames.get(i);
            boolean itemValue = (boolean) taskBools.get(i);
            // Erstellt und gestaltet die Aufgabe
            JLabel itemLabel = new JLabel("- " + itemName);
            itemLabel.setBorder(new EmptyBorder(0,10,0,10));
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
            if (i == 4) {
                itemLabel.setText("...");
                itemLabel.setHorizontalAlignment(JLabel.CENTER);
                itemLabel.setFont(new Font("Serif", Font.BOLD, 16));
                container.add(itemLabel);
                break;
            }

            container.add(itemLabel);
        }

        // Wenn es keine Aufgaben gibt, dann wird ein Label erstellt und dem Container hinzugefügt,
        // damit das Aufgaben-Panel nicht leer ist
        if (taskNames.isEmpty()) {
            JLabel emptyLabel = new JLabel();
            emptyLabel.setPreferredSize(new Dimension(100,22));
            container.add(emptyLabel);
        }

        return container;
    }

    /**
     * Berechnet die Anzahl der abgeschlossenen Aufgaben in Prozent und gibt diese aus.
     *
     * @param name Der Name der To-do-Liste.
     * @return Gibt die Prozentzahl mit dem Prozentzeichen aus.
     * */
    protected String getPercent(String name) {
        DecimalFormat decimalFormat = new DecimalFormat("0"); // Format für eine ganzzahlige Zahl
        double finished = 0;
        double percent;
        List<Object> taskBools = new ArrayList<>(data.get(name).get("bool"));

        // Wenn eine Aufgabe bereits erledigt ist, wird der finished Wert erhöht
        for (Object value : taskBools) {
            boolean newValue = (Boolean) value;

            if (newValue) {
                finished++;
            }
        }

        percent = finished / taskBools.size() * 100; // Berechnung der Prozentzahl

        if (Double.isNaN(percent)) {
            // Wenn es keine Aufgaben gibt, dann soll 0 % angezeigt werden
            percent = 0;
        }

        return decimalFormat.format(percent) + " %";
    }
}
