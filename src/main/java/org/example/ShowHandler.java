package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Die Klasse ShowHandler verwaltet die Anzeige und Bearbeitung von Aufgabenlisten.
 */
public class ShowHandler {
    private final JFrame mainWindowFrame;
    private final JFrame mainFrame;
    private final JPanel listPanel;
    private final JPanel mainPanel;
    private final JPanel titlePanel;
    private final JPanel tasksPanel;
    private final String oldName;
    private Map<String, Map<String, List<Object>>> data;

    private JTextArea titelTextArea;
    private boolean edit = true;

    /**
     * Erstellt einen neuen ShowHandler.
     *
     * @param panel     Das Panel, in dem die Aufgabenliste angezeigt wird.
     * @param frame     Das Fenster, das die Aufgabenliste enthält.
     * @param name      Der Name der Aufgabenliste.
     * @param _mainPanel    Das Haupt-Panel der Anwendung.
     * @param _frame    Das Hauptfenster der Anwendung.
     */
    public ShowHandler(JPanel panel, JFrame frame, String name, JPanel _mainPanel, JFrame _frame) {
        mainWindowFrame = _frame;
        mainPanel = _mainPanel;
        mainFrame = frame;
        listPanel = (JPanel) panel.getComponent(0);
        titlePanel = (JPanel) listPanel.getComponent(0);
        tasksPanel = (JPanel) listPanel.getComponent(1);
        oldName = name;
        data = new DataHandler().getData();
    }

    /**
     * Aktiviert den Bearbeitungsmodus für die Aufgabenliste.
     */
    protected void edit() {
        // Wenn edit nicht wahr ist, dann wird diese Methode nicht ausgeführt
        if (edit) {
            edit = false;

            // Initialisiert die TextArea für den Titel, das Label, der Button zum Speichern
            // und erstellen einer neuen Aufgabe wird erstellt
            titelTextArea = new JTextArea();
            JLabel titleLabel = (JLabel) titlePanel.getComponent(0);
            JButton saveButton = saveButton();
            JButton newTaskButton = newTaskButton();

            // Bestimmt und fügt den Titel in die TextArea
            String titel = titleLabel.getText();
            titelTextArea.setText(titel);
            titelTextArea.setPreferredSize(new Dimension(tasksPanel.getWidth() - 20, 20));
            titlePanel.add(titelTextArea);
            titlePanel.remove(0);

            // Erstellt in dem Panel für die Aufgaben ein neues Widget aus TextArea und einem Button.
            // Die CheckBox wird entfernt
            for (int i = 0; i < tasksPanel.getComponentCount(); i++) {
                JCheckBox checkBox = (JCheckBox) tasksPanel.getComponent(i);
                createTaskEdit(i, checkBox.getText());
                tasksPanel.remove(i + 1);
            }

            // Der Button zum Hinzufügen der Aufgaben, wird zum Panel hinzugefügt und die Höhe und
            // die Breite werden angepasst
            tasksPanel.add(newTaskButton);
            tasksPanel.revalidate();
            int tasks = tasksPanel.getComponentCount() * 34;
            tasksPanel.setPreferredSize(new Dimension(tasksPanel.getWidth(), tasks + 20));

            // Der Speichern-Button wird dem Panel der Liste hinzugefügt und die Größe wird angepasst
            listPanel.add(saveButton);
            int listHeight = tasks + titlePanel.getHeight() + 72;
            listPanel.setPreferredSize(new Dimension(tasksPanel.getWidth(), listHeight));

            mainFrame.revalidate();
        }
    }

    /**
     * Erstellt eine neue Bearbeitungsmöglichkeit für eine Aufgabe.
     *
     * @param index         Der Index der Aufgabe.
     * @param textAreaText  Der Text der Aufgabe.
     */
    private void createTaskEdit(int index, String textAreaText) {
        int width = tasksPanel.getWidth() - 88;

        JPanel taskPanel = new JPanel();
        JTextArea textArea = new JTextArea(textAreaText);
        textArea.setPreferredSize(new Dimension(width, 20));

        JButton deleteTask = deleteTaskButton(taskPanel);

        taskPanel.add(textArea);
        taskPanel.add(deleteTask);

        tasksPanel.add(taskPanel, index);
    }

     /**
     * Erstellt einen Speichern-Button.
     *
     * @return Der Speichern-Button.
     */
    private JButton saveButton() {
        // Button zum Speichern wird erstellt und die Funktion wird bearbeitet
        JButton save = new JButton("Speichern");
        save.addActionListener(_ -> {
            data = new DataHandler().getData();
            List<Object> listNamen = new ArrayList<>(data.keySet()); // Ermittelt alle Namen der To-do-Listen
            String newName = titelTextArea.getText(); // Ermittelt den neu geschriebenen Titel

            // Überprüft den neuen Titel. Wenn dieser vorhanden ist, dann wird ein Fehlertext ausgegeben.
            // Wenn nicht, dann wird die Änderung gespeichert
            if (listNamen.contains(newName) && !oldName.equals(newName)) {
                JOptionPane.showMessageDialog(
                        new JOptionPane(),
                        "Dieser Titel ist bereits vorhanden",
                        "Titel",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                // Erstellt den Titel mit dem neuen Namen und ändert den Font
                JLabel titleText = new JLabel(newName);
                titleText.setFont(new Font("Serif", Font.PLAIN, 20));

                // Erstellt die Aufgaben
                createTask(newName);

                titlePanel.add(titleText);
                titlePanel.remove(0); // Löscht die TextArea vom Titel
                listPanel.remove(2); // Löscht den Speichern-Button
                tasksPanel.remove(tasksPanel.getComponentCount() - 1); // Löscht den Neue-Aufgaben-Button

                // Aktualisiert das Panel der Liste
                listPanel.revalidate();
                listPanel.repaint();

                // Bestimmt und ändert die Größe der Panels vom Titel, der Aufgaben und der gesamten Liste
                ShowTodo showTodo = new ShowTodo("", new JPanel(), mainWindowFrame);
                int width = showTodo.resizeList(titlePanel, tasksPanel);
                int taskHeight = tasksPanel.getComponentCount() * 34;
                int listHeight = titlePanel.getHeight() + taskHeight + 10;
                titlePanel.setPreferredSize(new Dimension(width, titlePanel.getHeight()));
                tasksPanel.setPreferredSize(new Dimension(width, taskHeight));
                listPanel.setPreferredSize(new Dimension(width, listHeight));

                // Setzt den Bearbeitungswert wieder auf wahr, damit wieder bearbeitet werden kann.
                // Zum Schluss wird das Hauptfenster aktualisiert und die Daten gespeichert
                edit = true;
                mainFrame.revalidate();
                new DataHandler().saveDataToJson(data);
            }
        });

        return save;
    }

    /**
     * Erstellt neue Aufgaben.
     *
     * @param newName Der Name der Liste.
     */
    private void createTask(String newName) {
        data = new DataHandler().getData(); // Lädt die aktuellen Daten
        List<Object> taskNames = data.get(oldName).get("name"); // Erstellt eine Liste mit allen Namen der Aufgaben
        List<Object> taskBools = data.get(oldName).get("bool"); // Erstellt eine Liste mit den Werten der Aufgaben
        List<Object> newTaskNames = new ArrayList<>();
        List<Object> newTaskBools = new ArrayList<>();
        ShowTodo showTodo = new ShowTodo("", new JPanel(), mainWindowFrame);

        // Erstellt in der Schleife die CheckBoxen und tauscht diese mit der TextArea und dem Button
        for (int i = 0; i <tasksPanel.getComponentCount() - 1; i++) {
            // Ermittelt den aktuellen Namen der Aufgabe
            JPanel taskPanel = (JPanel) tasksPanel.getComponent(i);
            JTextArea textArea = (JTextArea) taskPanel.getComponent(0);
            String taskName = textArea.getText();

            // Fügt den Namen und den Wert der Aufgabe der passenden Liste hinzu
            newTaskNames.add(taskName);
            newTaskBools.add(false);

            // Erstellt und gestaltet die aktuelle CheckBox
            JCheckBox checkBox = new JCheckBox(taskName);
            checkBox.setFont(new Font("Serif", Font.PLAIN, 16));
            int index = i;
            checkBox.addActionListener(_ -> {
                // Ermittelt den Index von dem Panel der bearbeitenden Liste im Hauptfenster
                List<String> panelNames = new ArrayList<>(data.keySet());
                int panelIndex = panelNames.indexOf(newName);

                List<Object> newTasksBools = data.get(newName).get("bool");
                JPanel panel = (JPanel) mainPanel.getComponent(panelIndex);

                // Wenn die Aufgabe angewählt ist, dann wird der Text durchgestrichen
                // und der Wert angepasst
                if (checkBox.isSelected()) {
                    checkBox.setFont(showTodo.strikeFont(checkBox, true));
                    newTasksBools.set(index, true);
                } else {
                    checkBox.setFont(showTodo.strikeFont(checkBox, false));
                    newTasksBools.set(index, false);
                }

                // Wenn der Index kleiner als vier ist, dann wird der Text, falls nötig,
                // im Hauptfenster durchgestrichen
                if (index < 4) {
                    Container container = (Container) panel.getComponent(1);
                    JLabel textLabel = (JLabel) container.getComponent(index);

                    if (checkBox.isSelected()) {
                        textLabel.setFont(showTodo.strikeFont(checkBox, true));
                    } else {
                        textLabel.setFont(showTodo.strikeFont(checkBox, false));
                    }
                }

                new DataHandler().saveDataToJson(data);
                // Bestimmt das Label von der Prozent Anzeige und dem Titel
                JPanel titlePanel = (JPanel) panel.getComponent(0);
                JLabel titleLabel = (JLabel) titlePanel.getComponent(0);
                JLabel percentLabel = (JLabel) titlePanel.getComponent(1);

                // Überarbeitet den Titel der To-do-Liste und berechnet die Prozent Anzeige
                titleLabel.setText(newName);
                CreateTaskLabel cTL = new CreateTaskLabel(new JPanel(), mainWindowFrame);
                percentLabel.setText(cTL.getPercent(newName));

                mainFrame.revalidate();
            });

            // Überprüft, ob die Aufgabe ausgewählt ist. Wenn ja, dann wird die CheckBox angepasst,
            // der Text durchgestrichen und der Wert in der Liste aktualisiert
            if (taskNames.contains(taskName) && (Boolean) taskBools.get(i)) {
                checkBox.setSelected(true);
                checkBox.setFont(showTodo.strikeFont(checkBox, true));
                newTaskBools.set(i, true);
            }

            tasksPanel.add(checkBox, i);
            tasksPanel.remove(i + 1);
        }

        // Löscht die alte To-do-Liste und fügt die neuen Aufgaben mit den passenden Werten ein
        data.remove(oldName);
        data.put(newName, new HashMap<>());
        data.get(newName).put("name", newTaskNames);
        data.get(newName).put("bool", newTaskBools);
        new DataHandler().saveDataToJson(data);

        // Löscht alle Inhalte aus dem Panel des Hauptfensters
        while (mainPanel.getComponentCount() != 0) {
            mainPanel.remove(0);
        }

        List<String> keysList = new ArrayList<>(data.keySet()); // Liste der Namen der To-do-Listen

        // Erstellt die To-do-Listen mit den Elementen
        for (String name : keysList) {
            new CreateTaskLabel(mainPanel, mainWindowFrame).createJLabel(name);
        }

        mainPanel.revalidate();
    }

      /**
     * Erstellt einen Button zum Hinzufügen einer neuen Aufgabe.
     *
     * @return Der Button zum Hinzufügen einer neuen Aufgabe.
     */
    private JButton newTaskButton() {
        // Erstellt und gestaltet den Button
        JButton newTaskButton = new JButton("Neue Aufgabe");
        newTaskButton.setPreferredSize(new Dimension(100,20));
        newTaskButton.addActionListener(_ -> {
            // Erstellt ein neues Bearbeitungswidget
            int index = tasksPanel.getComponentCount() - 1;
            createTaskEdit(index, "");

            // Passt die Größe des Panels für die Aufgaben und der gesamten To-do-Liste
            int height = tasksPanel.getHeight() + 30;
            tasksPanel.setPreferredSize(new Dimension(tasksPanel.getWidth(), height));
            listPanel.setPreferredSize(new Dimension(tasksPanel.getWidth(), height + 102));

            mainFrame.revalidate();
        });

        return newTaskButton;
    }

    /**
     * Erstellt einen Button zum Löschen einer Aufgabe.
     *
     * @param taskPanel Das Panel, das die Aufgabe enthält.
     * @return Der Button zum Löschen einer Aufgabe.
     */
    private JButton deleteTaskButton(JPanel taskPanel) {
        // Erstellt und gestaltet den Button zum Löschen einer Aufgabe
        JButton deleteTask = new JButton("X");
        deleteTask.setForeground(Color.red);
        deleteTask.setPreferredSize(new Dimension(45,20));
        deleteTask.addActionListener(_ -> {
            // Passt die Größe der Aufgaben-Panels an und löscht die Aufgabe aus dem Panel
            int height = tasksPanel.getHeight() - 30;
            tasksPanel.setPreferredSize(new Dimension(tasksPanel.getWidth(), height));
            tasksPanel.remove(taskPanel);

            mainFrame.revalidate();
        });

        return deleteTask;
    }
}
