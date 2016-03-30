# JMNext 

Java Music Next Generation
Jingle Maschine Next Generation

Funktionsumfang:
Die JAVA MUSIC NEXT Software ist für den Einsatz bei Sportevents, insbesondere Volleyball und Beachvollyball gedacht. Sie unterstützt den Anwender beim zeitgerechten starten und stoppen von Musikwiedergaben. JAVA MUSIC NEXT ist für herkömmliche, Maus und Tastatur gesteuerte Computer, sowie touchfähige Endgeräte geeignet. Darüberhinaus ist JAVA MUSICN EXT unabhängig vom Betriebssystem einsetzbar. Die JAVA MUSIC NEXT Software hinterlegt ausgewählte Musikdateien oder gesamte Musikordner auf ein Layout um diese bei Klicken auf den entsprechenden Button abzuspielen. Dazu stehen unterschiedliche Abspielmethoden, sowie Einstellungen der Musikdatei und des Buttonlayouts zur Verfügung. 

Wichtig! Musikdateien müssen so geschnitten sein, damit diese schon an der entsprechenden Stelle einsetzen.

Funktionen: 
	Hauptansicht besteht aus dem Soundboard:
		- Ein Soundboard kann mehrere Layer haben:
			- Ein Layer kann unterschiedlich viele Soundbutton aufnehmen
========================================================================
	Folgende Formate werden unterstützt: 
		- AAC 
		- MP3
		- WAV
	Einzelne Soundbutton können über einen Rechtsklick gepflegt werden:
		Folgende Einstellungen können vorgenommen werden:
			- Farbe Text
			- Farbe Hintergrund
			- Musikordner/Musikfiles können hinterlegt werden
			- Anzeigename kann angepasst werden
			- Lautstärke des Players 0% - 100%
			- Einstellung Button Art:
				- Shuffle Button:
					- Musikordner mit mehr als einem Titel wurde ausgewählt
					- Musikdateien werden zufällig abgespielt
				- Multisong Button
					- Musikordner mit mehr als einem Titel wurde ausgewählt
					- Musikdateien werden in Ordnerreihenfolge abgespielt (z.B. Alphabetisch)
				- OneSong Button
					- eine Musikdatei ist hinterlegt
				- OwnPlayer Button
					- eine Musikdatei ist hinterlegt
					- überblendet beim starten den Standardplayer (z.B. für Werbung bzw. für Samples etc.)
			- Soundbutton besteht aus:
				- Zähler (Wie oft ein Soundbutton gedrückt wurde) -> Oben links
				- Icon Soundbutton Art -> Oben rechts
				- Name -> Mitte
				- Anzahl Musikdateien/Laufzeit -> unten links
				- Start/Play/Stop Zeichen unten rechts
	Drag And Drop Funktionen:
		- Titelanzeige
			- von der Titelanzeige kann ein Titel auf einen leeren Soundbutton gezogen werden
			- diese Funktion wird benötigt, um coole Lieder bei der Shufflefunktion direkt in den Layer zu ziehen
		- Soundbutton 
			- können per Drag And Drop auf andere Soundbutton des aktuellen Layers kopiert werden
			- Drag And Drop mit gedrückter Strg-Taste kopiert die Farbkonfig des ausgewählten Soundbuttons
		- Aus Dateisystem
			- Musikdateien und Musikordner können per Drag And Drop direkt aus dem Dateisystem auf ein Soundbutton gezogen werden
	Speicher Funktionen:
		- Alle Änderungen werden beim Schließen des Programms in eine Autosave-Datei gespeichert (Beim Neustart wird auf diese Datei zugegriffen)
		- Unter Datei Autosave speichern kann man zwischendurch speichern (Strg + s Tastenkombination muss noch implementiert werden)
		- Komplette Soundboardkonfig kann gespeichert werden
		- Layer können gespeichert werden (ist noch nicht implementiert)
	Exportfunktion:		
		- Layer können einzeln exportiert werden, dabei werden sowohl die Soundbutton-Eigenschaften als auch die Musikfiles exportiert
	Konfiguration Layer:
		- Layer können zu jederzeit erweitert, bzw. verkleinert werden
		- kann unendlich viele Spalten bzw. Zeilen aufnehmen
		- Layer sind unabhängig voneinander konfigurierbar
	Sideviewfunktion:
		- ein Layer kann in einem eigenen Fenster angezeigt werden (Ansicht->Aktueller Layer im Sideview)->Zugriff auf zwei Layer gleichzeitig
	Konfiguration Soundboard:
		- Ein Soundboard kann beliebig viele Layer aufnehmen
		- Die Zähler der Soundbutton aller Layer kann zurück gesetzt werden
		- Fadeoutzeit Standardplayer kann festgelegt werden 0,1 - 3 Sekunden
	Tastaturkürzel:
		- z: macht Drag And Drop sachen rückgängig bei denen ein Soundbutton verändert wurde
		- Strg + '+' vergrößert die Schrift bei allen Elementen
		- Strg + '-' verkleinert die Schrift bei allen Elementen
	Hot-Button-Konfig:
		- Hier können auf Taste 1-9 Soundbuttons hinterlegt werden
	Warnhinweis:
		- Gelöschte Ordner- und Musikpfade werden durch ein Warndreieck angezeigt
	
	
	