# BattleArena
BattleArena ist ein Spiel, bei dem sich ein Spieler seinen Charakter auswählen kann und dann nacheinander gegen alle vorhandenen Gegner antritt.
Der Spieler hat gewonnen wenn alle Gegner besiegt wurden.

## Abhängigkeiten
- [maven](https://maven.apache.org/) - für den Build-Prozess / die Abhängigkeiten
- [java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) - Programmiersprache

Für das Projekt ist keine Main-Methode angegeben, sodass das Projekt nur aus der IDE mit Auswahl der MainKlasse (BattleArena.java) ausgeführt werden kann.

## Entwicklerdokumentation

### Ablauf des Spiels

Das Spiel läuft solange der aktueller Spieler alle Spiele gewonnen hat und noch mehr als 0 Lebsnpunkte hat. Nach jedem Spiel werden die Lebenspunkte des Spielers zurückgesetzt.

1. Spiel starten (per Run in der IDE)
2. Kämpfer wählen (durch Eingabe der Id)
3. Dem Kämpfer einen Namen geben (optional)
4. Kämpfe werden simuliert

### Design
Das Projekt wurde auf dem Konzept der Abstraktion sowie der dynamischen Bindung aufgebaut. Es werden im Quellcode die verschiedenen Klassen (Kämpfer) definiert, welche von einer Basisklasse erben.

Durch die dynamische Bindung (hier: Liste von Kämpfern im BattleService) kann das Projekt einfach und unkompliziert um neue Kämpfer erweitert aber auch reduziert werden.

![UML Diagramm](https://raw.githubusercontent.com/jlndrs/battlearena/master/docs/uml-diagram.png)

Die Implementierungen der Fighter:
- [Archer](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Archer.java)
- [Giant](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Giant.java)
- [Dragon](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Dragon.java)
- [Magician](https://github.com/jlndrs/battlearena/blob/master/src/main/java/de/juliandrees/battlearena/model/fighter/Magician.java)

### Abstraktion
Die Klasse `Fighter` ist eine abstrakte Klasse, die auch die Spiellogik für den Spieler beinhaltet. Jeder Fighter braucht eine Id und einen Standard-Namen (Kämpfername). Da von mehreren Stellen darauf zugegriffen wird habe ich mich dazu entschieden ein `FighterRegister` anzulegen, welche diese Informationen beinhaltet.

*Ursprüngliches Problem: Für Ausgaben o.ä braucht man keine Objekte, sondern lediglich Basisinformationen. Diese Basisinformationen kann man aber nicht erreichen wenn diese nur in Objekten vom Typ Fighter verfügbar sind.*

Daher sind diese zwei Informationen (Id und Standard-Name) im FighterRegister vorhanden.
Bei der Initialisierung eines Fighter-Objektes werden dann diese beiden Eigenschaften anhand der Klasse aus dem `FighterRegister` geladen.

Für die Liste von Skills ist eine abstrakte Methode definiert, die in den Subklassen implementiert werden muss:

``` java
protected abstract List<Skill> defineSkills();
```



### Objektorientierung
Datenblöcke, die inhaltlich zu einem Thema / Bereich gehören, wurden hier in einzelne Klassen / Objekte aufgeteilt und zusammengefasst. 

#### Einmalige Instanziierung (Zuständigkeiten)
Klassen wie `BattleService` und `GameMenu` werden nur einmal instanziiert. Der Grund: inhaltlich gehören die Attribute (z.B. die Streams im GameMenu) zu keiner bestehenden Klasse, sie müssen nicht für die Klasse verfügbar sein, in der sie verwendet werden (wie sie es z.B. in der Main-Klasse wären).

Daher erstellt man Klassen, die diese Attribute durch Methoden verwalten können. Die Klassen, die ein Objekt der Klasse dann verwenden können über die Methoden auf die Attribute indirekt zugreifen.

Durch die Aufteilung auf Klassen beinhaltet die Hauptklasse deutlich weniger Referenzen auf Objekte und kann trotzdem alle nötigen Funktionen nutzen, die Zuständigkeiten werden in Klassen ausgelagert.
So werden die Attribute ebenfalls vor fremden und falschem Zugriff geschützt (Thema: Datenkapselung).

Aus der Abstraktion entstanden folgende Klassen:

#### `Fighter.java`
Der Basisfighter. Jeder Fighter hat eine Id und einen Namen, Lebenspunkte und die eigene Stärke. Ein Fighter soll angreifen und sich verteidigen können.

#### `Battle.java`
Ein Battle ist ein Kampf, der zwischen zwei Fightern ausgetragen wird. Ein Kampf kann nur einen Gewinner haben.

#### `GameMenu.java`
Das GameMenu ist die Konsole, auf der der Benutzer Eingaben und das System Ausgaben machen kann.

#### `BattleService.java`
Der BattleService ist ein Service welcher das Spiel verwaltet. Er beinhaltet die Fighter und initialisiert die Spiele mit den Gegnern. 

### Polymorphie
Die einzelnen Implementierungen benötigen keine besonderen Implementierungen von Methoden, außer eine: `defineSkills(): List<Skill>`. In dieser Methode werden für jede Klasse die Fähigkeiten definiert, die der im Kampf zur Verfügung hat. Da dies für die Superklasse nicht festgelegt werden kann, muss dies durch Polymorphie in den Subklassen festgelegt werden.

### Datenkapselung
Die Objekte verwalten sich größtenteils selbst. Für andere Objekte bedeutet das konkret: wenig Zugriff auf die Eigenschaften anderer Objekte, und wenn: meist zur Lesezugriff. Die Datenkapselung reduziert das Risiko von falscher Nutzung der Objekte und Eigenschaften.

Die Datenkapselung gilt nicht nur für Attribute (und deren get- und set-Methoden) sondern auch für andere Funktionen.

Dazu: die Klassen sind keine reinen POJO-Objekte (Datenobjekte), da Logik in der Klasse enthalten ist.

### Lambda-Ausdrücke
Lambda-Ausdrücke sind Kurzschreibweisen für bestimmte Funktionen in Java (z.B Iterieren von Listen, Initialisierung von Obekten, etc..).

In *BattleArena* werden auch Lambda-Ausdrücke verwendet, um den Quellcode zu reduzieren:
``` java
.filter(fighterRegister -> fighterRegister.getFighterClass().equals(fighterClazz))
```

anstatt

``` java
for (FighterRegister register : FighterRegister.values()) {
    if (register.getFighterClass().equals(fighterClazz)) {
        ...
    }
}
```

*ODER*:
``` java
fighters.forEach(Fighter::initialize);
```

anstatt

``` java
for (Fighter fighter : fighters) {
    fighter.initialize();
}
```

Lambda-Ausdrücke sind nicht notwendig, bieten jedoch eine schöne und einfache Alternative zu alltäglichen Funktionen, die deutlich mehr Code verursachen würden (Syntaktischer Zucker).
