package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import data.SoundButtonProperties;
import gui.MainView;
import gui.SoundBoard;

public abstract class ExportLayer {

	public static void save(SoundBoard sbExport, File fileDestinationFolder) {
		if (fileDestinationFolder != null) {
			File fileTemp;
			File FileFolderTemp;
			File[] fileListe;
			SoundButtonProperties sbpTemp;
			int zeilen = sbExport.getZeilen();
			int spalten = sbExport.getSpalten();
			File fileLayerExport = new File(fileDestinationFolder.getPath() + "/Export.lay");
			File fileMusicFolder = new File(fileDestinationFolder.getPath() + "/Musicdata");
			System.out.println(fileLayerExport.getPath());
			System.out.println(fileDestinationFolder.getPath());

			try {
				fileLayerExport.createNewFile();
				fileMusicFolder.mkdir();

				FileOutputStream fileStream = new FileOutputStream(fileLayerExport);
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				os.writeInt(sbExport.getZeilen());
				os.writeInt(sbExport.getSpalten());
				os.writeBoolean(sbExport.pbVisible);
				for (int z = 0; z < zeilen; z++) {
					for (int sp = 0; sp < spalten; sp++) {
						os.writeObject(sbExport.getSbArray()[z][sp].getProperties());
						sbpTemp = sbExport.getSbArray()[z][sp].getProperties();
						if (sbpTemp.getButtonArt() != 99 && sbpTemp.getMusicPath() != null) {
							fileTemp = new File(fileMusicFolder.getPath() + "/" + sbpTemp.getMusicPath().getName());
							if (sbpTemp.getMusicPath().isFile() == true) {
								Files.copy(sbpTemp.getMusicPath().toPath(), fileTemp.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
								System.out.println("Zeile: " + z + " Spalte: " + sp + " gespeichert (Datei)");
							} else if (sbpTemp.getMusicPath().isDirectory() == true) {
								fileListe = sbpTemp.getMusicPath().listFiles();
								Files.copy(sbpTemp.getMusicPath().toPath(), fileTemp.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
								for (int i = 0; i < fileListe.length; i++) {
									FileFolderTemp = new File(fileTemp.getPath() + "/" + fileListe[i].getName());
									System.out.println(fileListe[i].getPath());
									System.out.println(FileFolderTemp.getPath());
									Files.copy(fileListe[i].toPath(), FileFolderTemp.toPath(),
											StandardCopyOption.REPLACE_EXISTING);
								}
								System.out.println("Zeile: " + z + " Spalte: " + sp + " gespeichert (Folder)");
							}
						}
					}
				}
			} catch (Exception ex) {
				System.out.println("Objekte konnten nicht vollständig gespeichert werden");
				System.out.println(ex.getMessage());
			}
		}

	}

	public static void load(MainView hf, File fileLayerImport) {
		System.out.println(fileLayerImport);
		if (fileLayerImport != null) {
			try {
				int zeilen;
				int spalten;
				boolean pbVisible;
				SoundButtonProperties sbpTemp;
				SoundButtonProperties[][] sbpArray;
				FileInputStream fileStream = new FileInputStream(fileLayerImport);
				ObjectInputStream os = new ObjectInputStream(fileStream);
				try {
					zeilen = os.readInt();
					spalten = os.readInt();
					pbVisible = os.readBoolean();
					sbpArray = new SoundButtonProperties[zeilen][spalten];
					for (int z = 0; z < zeilen; z++) {
						for (int sp = 0; sp < spalten; sp++) {
							sbpTemp = (SoundButtonProperties) os.readObject();
							sbpTemp.setMusicPath(
									new File(fileLayerImport.getParent() + "/Musicdata/" + sbpTemp.getName()));
							sbpArray[z][sp] = sbpTemp;
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
		}
	}
}
