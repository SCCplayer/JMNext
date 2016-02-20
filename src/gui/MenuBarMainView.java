package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarMainView extends JMenuBar {
	private JMenu menuDatei = new JMenu("Datei");

	private JMenuItem itemLoadSoundboard = new JMenuItem("Soundboard laden");
	private JMenuItem itemSaveSoundboard = new JMenuItem("Soundboard speichern");
	private JMenuItem itemAutosave = new JMenuItem("Speichern in Autosave");
	private JMenuItem itemSettings = new JMenuItem("Einstellungen");

	private JMenu menuSoundboard = new JMenu("Soundboard");

	private JMenuItem itemAddLayer = new JMenuItem("Neuen Layer hinzufügen");
	private JMenuItem itemRemoveLayer = new JMenuItem("Aktuellen Layer entfernen");
	private JMenuItem itemSaveLayer = new JMenuItem("Aktuellen Layer speichern");
	private JMenuItem itemLoadLayer = new JMenuItem("Layer laden");

	private JMenu menuLayer = new JMenu("Layer");

	private JMenuItem itemAddSpalte = new JMenuItem("Spalte hinzufügen");
	private JMenuItem itemRemoveSpalte = new JMenuItem("Spalte entfernen");
	private JMenuItem itemAddZeile = new JMenuItem("Zeile hinzufügen");
	private JMenuItem itemRemoveZeile = new JMenuItem("Zeile entfernen");

	private JMenu menuAnsicht = new JMenu("Ansicht");

	private JMenuItem itemPbAusblenden = new JMenuItem("Fortschrittsanzeige im Layer ein-/ausblenden");
	private JMenuItem itemIconBar = new JMenuItem("Symbolleiste ein-/ausblenden");
	private JMenuItem itemFontSmall = new JMenuItem("Schriftgröße: Klein");
	private JMenuItem itemFontMedium = new JMenuItem("Schriftgröße: Mittel");
	private JMenuItem itemFontLarge = new JMenuItem("Schriftgröße: Groß");
	private JMenuItem itemSideView = new JMenuItem("Aktueller Layer im Sideview");

}
