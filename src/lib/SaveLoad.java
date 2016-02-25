package lib;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.SoundButtonProperties;
import gui.MainView;
import gui.SoundBoard;

public abstract class SaveLoad {

	private static File fileAutoSave;
	private static File fileConfig;

	public static void openConfig(MainView hf) {
		fileConfig = new File(getJarPath(hf).concat("config.ser"));
		createMyFile(fileConfig);
	}

	public static void saveConfig(MainView hf, File fileSpeicher) {
		try {
			// 1. Blende
			// 2. Activer Layer
			// 3. hf Position
			// 4. hf Size
			// 5. hf.Fontsize
			// 6. Soundboard Panel Hot Button
			FileOutputStream fileStream = new FileOutputStream(fileSpeicher);
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeInt(hf.getZeitBlende());
			os.writeInt(hf.getTp().getSelectedIndex());
			os.writeObject(hf.getLocation());
			os.writeObject(hf.getSize());
			os.writeObject(hf.getActualFontSize());
			for (int i = 0; i < 9; i++) {
				os.writeObject(hf.getPnlHotButton().getSbHotKey().getSbArray()[i][0].getProperties());
			}
			os.close();
		} catch (Exception ex) {
			System.out.println("Objekte konnten nicht vollständig gespeichert werden");
			System.out.println(ex.getMessage());
		}
	}

	public static void loadConfig(MainView hf, File fileLaden) {
		// 1. Blende
		// 2. Activer Layer
		// 3. hf Position
		// 4. hf Size
		// 5. hf Fontsize
		// 6. Soundboard Panel Hot Button
		try {
			FileInputStream fileStream = new FileInputStream(fileLaden);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				hf.setZeitBlende(os.readInt() * 10);
				hf.getTp().setSelectedIndex(os.readInt());
				hf.setLocation((Point) os.readObject());
				hf.setSize((Dimension) os.readObject());
				hf.setSizeOfMainViewElements((Font) os.readObject());
				for (int i = 0; i < 9; i++) {
					hf.getPnlHotButton().getSbHotKey().getSbArray()[i][0]
							.setProperties((SoundButtonProperties) os.readObject());
				}
			} catch (Exception e) {
				System.out.println("Fehler beim Laden der Config");
				System.out.println(e.getMessage());
			} finally {
				os.close();
			}
		} catch (Exception ex) {
			System.out.println("Fehler beim Öffnen der Config-Datei.");
			System.out.println(ex.getMessage());
		}
	}

	public static void openAutoSave(MainView hf) {
		if (hf.getClass().getClassLoader().getResource("resources").toString().split(":")[0].compareTo("file") == 0) {
			if (hf.getClass().getClassLoader().getResource("resources").toString().split(":").length == 2) {
				// MacOS:
				fileAutoSave = new File(hf.getClass().getClassLoader().getResource("resources").toString().split(":")[1]
						.concat("/autosave.ser"));

			} else if (hf.getClass().getClassLoader().getResource("resources").toString().split(":").length == 3) {
				// Windows:
				fileAutoSave = new File(hf.getClass().getClassLoader().getResource("resources").toString().split(":")[2]
						.concat("/autosave.ser"));
			}
			System.out.println(fileAutoSave.getAbsolutePath());
		} else {
			fileAutoSave = new File("autosave.ser");
		}
		if (fileAutoSave.exists() == false) {
			try {
				fileAutoSave.createNewFile();
			} catch (Exception e) {
				System.out.println("Datei erstellen fehlgeschlagen");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Daten werden aus AutoSave geladen");
			SaveLoad.loadMainView(hf, fileAutoSave);
		}
	}

	private static String getJarPath(MainView hf) {
		if (hf.getClass().getClassLoader().getResource("resources").toString().split(":")[0].compareTo("file") == 0) {
			if (hf.getClass().getClassLoader().getResource("resources").toString().split(":").length == 2) {
				// MacOS:
				return hf.getClass().getClassLoader().getResource("resources").toString().split(":")[1].concat("/");

			} else if (hf.getClass().getClassLoader().getResource("resources").toString().split(":").length == 3) {
				// Windows:
				return hf.getClass().getClassLoader().getResource("resources").toString().split(":")[2].concat("/");
			}
			System.out.println(fileAutoSave.getAbsolutePath());
		} else {
			return "";
		}
		return "";
	}

	private static void createMyFile(File myFile) {
		if (myFile.exists() == false) {
			try {
				myFile.createNewFile();
			} catch (Exception e) {
				System.out.println("Datei erstellen fehlgeschlagen");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Datei existiert bereits, Daten können geladen werden.");
		}
	}

	public static void loadMainView(MainView hf, File ladenFile) {
		try {
			int zeilen;
			int spalten;
			SoundButtonProperties[][] sbpArray;
			FileInputStream fileStream = new FileInputStream(ladenFile);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				int anzahlLayer = os.readInt();
				boolean pbVisible = true;
				System.out.println("Anzahl Layer: " + anzahlLayer);
				for (int i = 0; i < anzahlLayer; i++) {
					zeilen = os.readInt();
					spalten = os.readInt();
					pbVisible = os.readBoolean();
					sbpArray = new SoundButtonProperties[zeilen][spalten];
					for (int z = 0; z < zeilen; z++) {
						for (int sp = 0; sp < spalten; sp++) {
							sbpArray[z][sp] = (SoundButtonProperties) os.readObject();
						}
					}
					hf.getSbVector().add(new SoundBoard(hf, sbpArray, pbVisible, hf.getLmmv()));
					hf.setSoundBoardActive((SoundBoard) hf.getSbVector().get(i));
				}

			} catch (Exception e) {
				System.out.println("Fehler beim Laden");
				System.out.println(e.getMessage());
			} finally {
				os.close();
			}
		} catch (Exception ex) {
			System.out.println("Fehler beim Öffnen der Datei.");
			System.out.println(ex.getMessage());
		}
	}

	public static void saveMainView(MainView hf, File speicherFile) {
		try {
			FileOutputStream fileStream = new FileOutputStream(fileAutoSave);
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeInt(hf.getSbVector().size());
			for (int iv = 0; iv < hf.getSbVector().size(); iv++) {
				SoundBoard sbSave = (SoundBoard) hf.getSbVector().get(iv);
				os.writeInt(sbSave.getZeilen());
				os.writeInt(sbSave.getSpalten());
				os.writeBoolean(sbSave.pbVisible);
				for (int z = 0; z < sbSave.getZeilen(); z++) {
					for (int sp = 0; sp < sbSave.getSpalten(); sp++) {
						os.writeObject(sbSave.getSbArray()[z][sp].getProperties());
						System.out.println("Zeile: " + z + " Spalte: " + sp + " gespeichert");
					}
				}
				System.out.println("Layer " + iv + " wurde gespeichert");
			}
			os.close();
		} catch (Exception ex) {
			System.out.println("Objekte konnten nicht vollständig gespeichert werden");
			System.out.println(ex.getMessage());
		}
	}

	public static void saveSoundBoardAs(MainView hf) {
		System.out.println("Button speichern");
		File f = Browse.getSaveFileSou();
		System.out.println(f);
		if (f != null) {
			if (f.exists() == true) {
				System.out.println("Datei existiert bereits");
			} else {
				try {
					f.createNewFile();
					SaveLoad.saveMainView(hf, f);
				} catch (Exception ex) {
					System.out.println("Datei erstellen fehlgeschlagen");
				}
			}
		}
	}

	public static File getFileAutoSave() {
		return fileAutoSave;
	}

	public static File getFileConfig() {
		return fileConfig;
	}
}
