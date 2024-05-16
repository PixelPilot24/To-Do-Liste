package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static java.awt.font.TextAttribute.STRIKETHROUGH;
import static java.awt.font.TextAttribute.STRIKETHROUGH_ON;

/**
 * Diese Klasse erstellt die GUI für eine To-do-Liste.
 * */
public class ShowTodo {
    private final JFrame mainWindowFrame;
    private final JPanel mainPanel;
    private final String listName;
    private final Map<String, Map<String, List<Object>>> data;

    private JPanel showPanel;
    private JFrame mainFrame;

    /**
     * Der Konstruktor initialisiert die Klasse mit dem Namen der To-do-Liste, dem Haupt-Panel und dem
     * Frame für das Hauptfenster
     *
     * @param name Der Name der To-do-Liste.
     * @param panel Das {@code JPanel} vom Hauptfenster, in dem die Listen angezeigt werden.
     * @param frame Das {@code JFrame} vom Hauptfenster, um dieses zu aktualisieren.
     * */
    public ShowTodo(String name, JPanel panel, JFrame frame) {
        mainWindowFrame = frame;
        data = new DataHandler().getData();
        listName = name;
        mainPanel = panel;
    }

    /**
     * Startet die Anwendung, indem das Fenster initialisiert, Widgets erstellt und die Menüleiste hinzufügt.
     * */
    public void run() {
        initWindow();
        createWidgets();
        createMenuBar();
    }

    /**
     * Diese Methode initialisiert das Fenster mit dem Namen der To-do-Liste und zentriert es
     * auf dem Bildschirm.
     * */
    private void initWindow() {
        mainFrame = new JFrame(listName);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Hier werden die grafischen Elemente wie Titel, Aufgaben und Checkboxen erstellt und
     * im Hauptfenster angezeigt.
     * */
    private void createWidgets() {
        List<Object> taskNames = data.get(listName).get("name");
        // Initialisiert das Panel und erstellt Panels für den Titel, die Aufgaben und in welches
        // diese eingefügt werden
        showPanel = new JPanel();
        JPanel listPanel = new JPanel();
        JPanel titelPanel = new JPanel();
        JPanel tasksPanel = new JPanel(new GridLayout(0,1));
        // Erstellt die Scrollleisten
        JScrollPane scrollPane = new JScrollPane(
                showPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        // Erstellt und gestaltet den Titel
        JLabel titleText = new JLabel(listName);
        titleText.setFont(new Font("Serif", Font.PLAIN, 20));

        // Gestaltet das Panel für den Titel und fügt den Titel hinzu
        titelPanel.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 2), "Titel"
                ));
        titelPanel.add(titleText);

        // Gestaltet das Panel für die Aufgaben und fügt diese hinzu
        tasksPanel.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 2), "Aufgaben"
        ));

        for (int i = 0; i < taskNames.size(); i++) {
            // Erstellt die Checkboxen und fügt diese zum Panel hinzu
            JCheckBox checkBox = checkBox(i);

            tasksPanel.add(checkBox);
        }

        // Fügt alle Panel zusammen
        listPanel.add(titelPanel);
        listPanel.add(tasksPanel);
        showPanel.add(listPanel);

        // Fügt die Scrollleisten zum Frame hinzu und aktualisiert diesen
        mainFrame.add(scrollPane);
        mainFrame.revalidate();

        // Bestimmt die maximale Breite und Höhe der einzelnen Panels und passt diese an
        int width = resizeList(titelPanel, tasksPanel);
        int height = titelPanel.getHeight() + tasksPanel.getHeight();
        titelPanel.setPreferredSize(new Dimension(width, titelPanel.getHeight()));
        tasksPanel.setPreferredSize(new Dimension(width, tasksPanel.getHeight()));
        listPanel.setPreferredSize(new Dimension(width, height + 15));
        mainFrame.setSize(new Dimension(width + 50,475));
    }

    /**
     *  Diese Methode erstellt eine Checkbox für eine bestimmte Aufgabe basierend auf dem Index und
     *  setzt entsprechend den Status und die Schriftart.
     *
     * @param index Der Index für die jeweilige Aufgabe.
     * */
    private JCheckBox checkBox(int index) {
        // Ruft die Listen mit den Namen und dem Status der Aufgaben auf
        List<Object> taskNames = data.get(listName).get("name");
        List<Object> taskBools = data.get(listName).get("bool");
        // Bestimmt den aktuellen Namen und den Status der Aufgabe
        String taskName = (String) taskNames.get(index);
        boolean taskBool = (Boolean) taskBools.get(index);

        // Erstellt und gestaltet die Checkbox
        JCheckBox checkBox = new JCheckBox(taskName);
        checkBox.setFont(new Font("Serif", Font.PLAIN, 16));
        checkBox.addActionListener(_ -> {
            // Bestimmt die Position vom Panel der To-do-Liste im Hauptfenster
            List<String> panelNames = new ArrayList<>(data.keySet());
            int panelIndex = panelNames.indexOf(listName);

            // Panel der To-do-Liste vom Hauptfenster
            JPanel panel = (JPanel) mainPanel.getComponent(panelIndex);

            // Wenn die Checkbox ausgewählt ist, dann wird der Text durchgestrichen und
            // der Wert geändert. Falls nicht, wird der Text normal angezeigt
            if (checkBox.isSelected()) {
                checkBox.setFont(strikeFont(checkBox, true));
                taskBools.set(index, true);
            } else {
                checkBox.setFont(strikeFont(checkBox, false));
                taskBools.set(index, false);
            }

            // Wenn der index unter vier ist, wird die Aufgabe im Hauptfenster ebenfalls angepasst
            if (index < 4) {
                Container container = (Container) panel.getComponent(1);
                JLabel textLabel = (JLabel) container.getComponent(index);

                if (checkBox.isSelected()) {
                    textLabel.setFont(strikeFont(checkBox, true));
                } else {
                    textLabel.setFont(strikeFont(checkBox, false));
                }
            }

            new DataHandler().saveDataToJson(data);

            // Das Panel für die Prozent Anzeige wird ermittelt
            JPanel titlePanel = (JPanel) panel.getComponent(0);
            JLabel titleLabel = (JLabel) titlePanel.getComponent(0);
            JLabel percentLabel = (JLabel) titlePanel.getComponent(1);

            // Der Titel wird angepasst und die Prozent werden berechnet und ebenfalls angepasst
            titleLabel.setText(listName);
            CreateTaskLabel cTL = new CreateTaskLabel(new JPanel(), mainWindowFrame);
            percentLabel.setText(cTL.getPercent(listName));

            mainFrame.revalidate();
        });

        // Wenn die aktuelle CheckBox angewählt sein soll, dann wird diese angewählt
        // und der Text durchgestrichen
        if (taskBool) {
            checkBox.setSelected(true);
            checkBox.setFont(strikeFont(checkBox, true));
        }

        return checkBox;
    }

    /**
     * Diese Methode berechnet die optimale Breite für das Titel- und Aufgaben-Panel,
     * um die Anzeige anzupassen.
     *
     * @param titelPanel Das {@code JPanel} von dem Titel.
     * @param tasksPanel Das {@code JPanel} von den Aufgaben.
     *
     * @return Gibt die maximale Größe aus.
     * */
    protected int resizeList(JPanel titelPanel, JPanel tasksPanel) {
        // Bestimmt die Größen der Panels
        Dimension titelSize = titelPanel.getSize();
        Dimension tasksSize = tasksPanel.getSize();

        // Wenn die Breite und die Höhe kleiner als 300 sind, dann ist die Ausgabe 300.
        // Falls nicht, dann wird der höchste Wert ausgegeben
        if (titelSize.width < 300 && tasksSize.width < 300) {
            return 300;
        } else return Math.max(titelSize.width, tasksSize.width);
    }

    /**
     * Diese Methode gibt einen Font, aus der entweder durchgestrichen ist, oder nicht durchgestrichen ist.
     *
     * @param checkBox Von der aktuellen {@code JCheckBox} wird der Font ermittelt.
     * @param isSelected Abfrage, ob die CheckBox ausgewählt ist oder nicht.
     *
     * @return Gibt den passenden Font aus.
     * */
    protected Font strikeFont(JCheckBox checkBox, Boolean isSelected) {
        Font font = checkBox.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());

        if (isSelected) {
            attributes.put(STRIKETHROUGH, STRIKETHROUGH_ON);

            return font.deriveFont(attributes);
        } else {
            return new Font("Serif", Font.PLAIN, 16);
        }

    }

    /**
     * Erstellt eine Menüleiste mit den MenuItems.
     * */
    private void createMenuBar() {
        // Erstellt die Menüleiste und die MenuItems
        JMenuBar menuBar = new JMenuBar();
        JMenu option = new JMenu("Option");
        JMenuItem editList = new JMenuItem("Bearbeiten");
        JMenuItem deleteList = new JMenuItem("Löschen");
        mainFrame.revalidate();

        ShowHandler handler = new ShowHandler(showPanel, mainFrame, listName, mainPanel, mainWindowFrame);

        // ActionListener für die Menüitems
        editList.addActionListener(_ -> handler.edit()); // Aktiviert den Bearbeitungsmodus
        deleteList.addActionListener(_ -> {
            // Fragt, ob die Liste wirklich gelöscht werden soll
            int result = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Soll \"" + listName + "\" wirklich gelöscht werden?",
                    "Löschen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            // Falls ja, dann wird die Liste gelöscht
            if (result == JOptionPane.YES_OPTION) {
                // Entfernt die To-do-Liste aus den Dateien und speichert diese ab
                data.remove(listName);
                new DataHandler().saveDataToJson(data);

                // Sucht im Hauptfenster nach dem Panel mit dem Namen der To-do-Liste.
                // Wenn dieser gefunden wurde, dann wird dieser aus dem Panel entfernt,
                // das Haupt-Frame aktualisiert und die Schleife abgebrochen
                for (int i = 0; i < mainPanel.getComponentCount(); i++) {
                    JPanel child = (JPanel) mainPanel.getComponent(i);
                    JPanel _mainPanel = (JPanel) child.getComponent(0);
                    JLabel _titleLabel = (JLabel) _mainPanel.getComponent(0);
                    String childName = _titleLabel.getText();

                    if (childName.equals(listName)) {
                        mainPanel.remove(i);
                        mainPanel.revalidate();
                        mainWindowFrame.repaint();
                        mainWindowFrame.revalidate();
                        break;
                    }
                }

                mainFrame.dispose();
            }
        });

        // Fügt die Menüitems dem Dateiitem
        option.add(editList);
        option.add(deleteList);
        menuBar.add(option);

        mainFrame.setJMenuBar(menuBar);
    }
}
