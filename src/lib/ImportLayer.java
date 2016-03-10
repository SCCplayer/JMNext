package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import data.SoundButtonProperties;
import gui.MainView;
import gui.ProgressImportView;
import gui.SoundBoard;

public class ImportLayer implements Runnable {
	private ProgressImportView piv;
	private MainView hf;
	private File fileLayerImport;
	private int zeilen = 0;
	private int spalten = 0;

	public ImportLayer(ProgressImportView piv, MainView hf, File fileLayerImport) {
		this.piv = piv;
		this.hf = hf;
		this.fileLayerImport = fileLayerImport;
	}

	@Override
	public void run() {
		System.out.println(fileLayerImport);
		if (fileLayerImport != null) {
			try {
				boolean pbVisible;
				SoundButtonProperties sbpTemp;
				SoundButtonProperties[][] sbpArray;
				FileInputStream fileStream = new FileInputStream(fileLayerImport);
				ObjectInputStream os = new ObjectInputStream(fileStream);
				try {
					zeilen = os.readInt();
					spalten = os.readInt();
					piv.setMaxPP(spalten * zeilen);
					piv.setVisible(true);
					pbVisible = os.readBoolean();
					sbpArray = new SoundButtonProperties[zeilen][spalten];
					for (int z = 0; z < zeilen; z++) {
						for (int sp = 0; sp < spalten; sp++) {
							sbpTemp = (SoundButtonProperties) os.readObject();
							sbpTemp.setMusicPath(
									new File(fileLayerImport.getParent() + "/Musicdata/" + sbpTemp.getName()));
							sbpArray[z][sp] = sbpTemp;
							piv.updatePP();
						}
					}
					hf.getSbVector().add(new SoundBoard(hf, sbpArray, pbVisible, hf.getLmmv()));

					hf.getTp().add("Layer " + String.valueOf(hf.getSbVector().size()),
							(SoundBoard) hf.getSbVector().get(hf.getSbVector().size() - 1));

					hf.getTp().setSelectedIndex(hf.getSbVector().size() - 1);
					hf.setSoundBoardActive((SoundBoard) hf.getSbVector().get(hf.getSbVector().size() - 1));
					hf.setSizeOfMainViewElements(hf.getActualFontSize());
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
			String importDoneMessage = "<HTML><BODY>Der Layer " + hf.getSbVector().size()
					+ " wurde erfolgreich importiert. Ingesamt wurden " + spalten * zeilen
					+ " Soundbutton importiert. Achtung: Die Musikpfade wurden auf " + fileLayerImport.getParentFile()
					+ "\\Musicdata angepasst.</BODY></HTML>";
			piv.setImportDone(importDoneMessage);
		}

	}

}
