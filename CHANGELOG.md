# Changelog
Alle nennenswerten Änderungen an diesem Projekt werden in dieser Datei dokumentiert.

## [1.0.0] 2024-05-16
### Neue Funktionen
- Hinzufügen von neuen To-do-Listen mit Aufgaben zum ab und anwählen
- Löschen der einzelnen Aufgaben oder der To-do-Liste
- Bearbeitung der Aufgaben oder dem Titel der To-do-Liste

### Added
- Grundlegende Funktionen der Anwendung implementiert
- Benutzeroberfläche und Design fertiggestellt

### Fixed
- Diverse Bugs und Fehler behoben


## Änderungen vor der Basisversion
## Inhaltsverzeichnis
- [2024-05-16](#2024-05-16)
  - [Refaktorierung](#refaktorierung)
  - [DataHandler.java](#datahandlerjava)
  - [NewTodo.java](#newtodojava)
  - [Main.java](#mainjava)
  - [CreateTaskLabel.java](#createtasklabeljava)
  - [ShowTodo.java](#showtodojava)
  - [ShowHandler.java](#showhandlerjava)
- [2024-05-12](#2024-05-12)
   - [Refaktorierung](#refaktorierung-1)
   - [Main.java](#mainjava-1)
   - [CreateTaskLabel.java](#createtasklabeljava-1)
   - [NewTodo.java](#newtodojava-1)
- [2024-05-12](#2024-05-12-1)
  - [Refaktorierung](#refaktorierung-2)
  - [Main.java](#mainjava-2)
  - [CreateTaskLabel.java](#createtasklabeljava-2)
  - [DataHandler.java](#datahandlerjava-1)


### 2024-05-16
#### Refaktorierung
- Überarbeitung der Methoden in DataHandler.java, NewTodo.java, Main.java, CreateTaskLabel.java
- Neue Dateien hinzugefügt: ShowTodo.java, ShowHandler.java, README.md

#### DataHandler.java
- Datentyp von der Map von Map<String, Map<String, Boolean>> zu Map<String, Map<String, List<Objekt>>>
- loadDataFromJson Methode verändert
  - Wenn es keine data.json Datei gibt, dann wird eine neue erstellt

#### NewTodo.java
- Ausgabe im Message Dialog bei erfolgreicher Speicherung verändert
- Wenn eine neue Liste erstellt wird, wird das Layout angepasst

#### Main.java
- createGUI Methode verändert
  - Scrollleisten hinzugefügt
  - Wenn es keine Listen gibt, dann wird eine feste Größe eingestellt
  - Wenn die Anzahl der Listen weniger als fünf ist, dann wird das Layout angepasst

#### CreateTaskLabel.java
- Die Breite der Prozent Anzeige angepasst
- Beim Anklicken der Liste wird diese angezeigt
- getPercent Methode von private zu protected geändert

#### ShowTodo.java
- Methode zur initialisierung des Fensters
- Methode zu Erstellung der Widgets
- Methode um die passende Größe für die Liste zu bestimmen
- Methode die einen Font für durchgestrichene Texte ausgibt
- Methode zur Erstellung der Menüleiste

#### ShowHandler.java
- Methode zur Aktivierung vom Bearbeitungsmodus
- Methode zu Erstellung der Widgets für die Bearbeitung der Aufgabe
- Methode zur Speicherung der To-do-Liste
- Methode zur Erstellung einer neuen Aufgabe
- Methode zum Hinzufügen einer neuen Aufgabe
- Methode zum Löschen einer Aufgabe

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