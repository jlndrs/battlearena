# BattleArena
BattleArena ist ein Spiel, bei dem sich ein Spieler seinen Charakter auswählen kann und dann nacheinander gegen alle vorhandenen Gegner antritt.
Der Spieler hat gewonnen wenn alle Gegner besiegt wurden.

## Abhängigkeiten
- [maven](https://maven.apache.org/)
- [java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)

## Entwicklerdokumentation

### Ablauf des Spiels

Das Spiel läuft solange der aktueller Spieler alle Spiele gewonnen hat und noch mehr als 0 Lebsnpunkte hat. Nach jedem Spiel werden die Lebenspunkte des Spielers zurückgesetzt.

1. Spiel starten (per Run in der IDE)
2. Kämpfer wählen (durch Eingabe der Id)
3. Dem Kämpfer einen Namen geben (optional)
4. Kämpfe werden simuliert

### Design
Das Projekt wurde auf dem Konzept der Abstraktion sowie der dynamischen Bindung aufgebaut. Es werden im Quellcode die verschiedenen Klassen (Kämpfer) definiert, welche von einer Basisklasse erben. Für die Übersichtlichkeit habe ich die verschiedenen Implementierungen der Fighter-Klassen nicht eingefügt.

![UML Diagramm](https://raw.githubusercontent.com/jlndrs/battlearena/master/docs/uml.png)

Die Implementierungen der Fighter:
- [Archer](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Archer.java)
- [Giant](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Giant.java)
- [Dragon](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Dragon.java)
- [Magician](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Magician.java)

