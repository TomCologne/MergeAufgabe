
# Merge-Aufgabe

## Vorbetrachtungen

Die Aufgabe wäre bspw. in Python deutlich kompakter zu lösen gewesen. Da es aber
auch um Codestruktur, Dokumentation und Arbeitsweise ging, habe ich mich für
Java entschieden.


## Annahmen

- Syntaktisch falsche Eingaben münden in einer Exception. 
- Die leere Eingabe ist erlaubt. 
- Die Intervallgrenzen sind im Intervall enthalten, also mathematisch etwa ]a,b[.
- Ungültige Intervalle mit [a,b]: b < a werden nicht gemergt und an den Anfang
  der Ergebnismenge sortiert.

## Einschränkungen

Ich habe mich gestern Abend in die Aufgabe festgebissen und das Erstellen 
des Repositories vergessen, deshalb gibt es nur einen Commit. 
Normalerweise starte ich mit einem Git-Repository und arbeite ticketbasiert mit
mindestens einem Commit pro Ticket, idealerweise mit separaten Bugfix- und 
Featurebranches und gereviewten Pull Requests. 


## Laufzeitumgebung

Das Programm benötigt mind. Java 8 und das Tool ant zum Bauen.
Über den Ordner nbproject/ kann das Projekt direkt in das Tool
Netbeans importiert werden.

# Bauen

Im Projektordner befindet sich eine Datei build.xml. Ruft man in diesem
Ordner ant ohne Parameter auf, wird das komplette Projekt inklusive Javadocs
erstellt.

# Start des Programms

Nach dem Bauen befindet sich im Projektordner ein dist/-Ordner. 
cd dist/

Das Program startet dann:
java -jar DaimlerMerge.jar
oder
java -jar DaimlerMerge.jar [1,2] [3,4] [3,6]

## Struktur

Im Package merge befindet sich eine Starterklasse mit main()-Methode. Im
Package merge.api sind die Interfaces des Programms definiert. Für MergeAPI gibt
es aktuell nur eine Implementierung mit dem im Weiteren beschriebenen Algorithmus.
Einen Factorymechanismus habe ich ausgelassen. In merge.impl sind die 
Implementierungsklassen der Schnittstellen. 

## Algorithmus

# 1. Einlesen

Ich erwarte einen Eingabestring der Form "([min,max] )*". Dieser wird bis zu 
einer gewissen Länge gegen einen regulären Ausdruck validiert. Ab ca. 100k 
Intervallen erhalte ich eine OutOfMemorException, deshalb wird ab einer festgelegten
Länge nicht mehr validiert.

Der Eingabe String wird geparst, in Intervall-Objekte überführt, und gestreamt in
eine sortierte TreeSet eingepflegt.

# 2. Die Merge-Funktion

Durch die Sortierung nach den min-Werten der Intervalle, müssen nur noch benachbarte
Elemente auf eine gemeinsame Schnittmenge überprüft, und ggf. gemergt werden. 
Dies erfolgt in einem Map/Reduce-Konstrukt.


# 3. Die Ausgabe

Die Ausgabe hat die gleiche Struktur wie die Eingabe, und besteht aus den
gemergten Elementen in einem String.


## Laufzeit

Läßt man die Eingabevalidierung weg, besteht der Algorithmus aus 2 Hauptschleifen.
Die Adhoc-Sortierung in ein TreeSet wird logarithmische Laufzeit haben. Die
Laufzeit des Merge-Algorithmus ist linear, also O(2n) = O(n). 
Insgesamt ergibt dies die Laufzeit O(nlog n).
Die Laufzeit konnte ich in einem Lasttest bestätigen.

## Speicherbedarf

Zwischen 10 und 100 Millionen Eingabeintervallen bekomme ich auf einem Mac Pro
eine OutOfMemoryException beim Einlesen. Die TreeSet benötigt knapp 30%, die Instanzen der 
Intervalobjekte benötigen ungefähr 10% des Heap. Ausser String/StringBuilder fallen
alle anderen Strukturen nicht ins Gewicht.

## Optimierungsideen für sehr große Eingabemengen

Bei Eingaben im Gigabytebereich würde ich eine andere Programmiersprache wählen.
Ansonsten könnte man die Intervalle auf mehrere Eingabedateien verteilen und
mehrstufig mergen. Desweiteren würde ich statt auf TreeSet und Intervall-Objekten
auf int[]-Arrays operieren, was den Speicherbedarf stark reduzieren würde.

## Bearbeitungszeit

Einen erste lauffähige Version hatte ich in knapp 20 Minuten. Die Bearbeitungszeit
der gesamten Aufgabe mit Tests und Dokumentation hat zirka 6 Stunden gedauert.

## Fazit

Die Lösung der Aufgabe hat mir Spaß gemacht.