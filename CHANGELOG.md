# Changelog
Alle nennenswerten Änderungen an diesem Projekt werden in dieser Datei dokumentiert.

## Änderungen vor der Basisversion
## Inhaltsverzeichnis
- [2024-05-12](#2024-05-12)
   - [Refaktorierung](#refaktorierung)
   - [Main.java](#mainjava)
   - [CreateTaskLabel.java](#createtasklabeljava)
   - [NewTodo.java](#newtodojava)
- [2024-05-12](#2024-05-12-1)
  - [Refaktorierung](#refaktorierung-1)
  - [Main.java](#mainjava-1)
  - [CreateTaskLabel.java](#createtasklabeljava-1)
  - [DataHandler.java](#datahandlerjava)

### 2024-05-12
#### Refaktorierung:
- Überarbeitung der Methoden in Main.java, CreateTaskLabel.java
- Neue Datei hinzugefügt: NewTodo.java

#### Main.java
- createGUI Methode überarbeitet
  - Laden und erstellen der To-do-Listen
- createJMenuBar Methode überarbeitet
  - ActionListener zum "Neue Liste erstellen" Item hinzugefügt

#### CreateTaskLabel.java
- createJLabel Methode von private zu public geändert
- createListItems Methode überarbeitet
  - Border zum Aufgaben-Label hinzugefügt
  - Wenn es keine Aufgaben gibt, dann wird ein leeres Label erstellt und hinzugefügt

#### NewTodo.java
- initialisierung vom Panel vom Hauptfenster
- Methode zur Erstellung der Widgets hinzugefügt
- Methode zum Hinzufügen neuer Aufgaben implementiert
- Methode zum Speichern der neuen To-do-Liste implementiert

### 2024-05-12
#### Refaktorierung:
- GUI Erstellung
- Neue Dateien hinzugefügt: Main.java, CreateJLabel.java, DataHandler.json, CHANGELOG.md

#### Main.java
- Methode für die Erstellung des Hauptfensters der GUI hinzugefügt
- Methode für die Menüleiste hinzugefügt

#### CreateTaskLabel.java
- Methode für die Erstellung der To-do-Listen GUI hinzugefügt
- Methode für die Erstellung der Aufgaben GUI hinzugefügt
- Methode zur Berechnung des Prozentsatzes abgeschlossener Aufgaben hinzugefügt

#### DataHandler.java
- Methode zur Speicherung der JSON Datei hinzugefügt
- Methode zum Laden der JSON Datei hinzugefügt
- Methode zum Abrufen der JSON Datei