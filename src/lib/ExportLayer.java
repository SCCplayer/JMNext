package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import data.SoundButtonProperties;
import gui.ProgressExportView;
import gui.SoundBoard;

public class ExportLayer implements Runnable {
	private ProgressExportView pev;
	private SoundBoard sbExport;
	private File fileDestinationFolder;
	private File fileTemp;
	private File FileFolderTemp;
	private File[] fileListe;
	private File fileLayerExport;
	private File fileMusicFolder;
	private FileOutputStream fileStream;
	private ObjectOutputStream os;

	public ExportLayer(ProgressExportView pev, SoundBoard sbExport, File fileDestinationFolder) {
		this.pev = pev;
		this.sbExport = sbExport;
		this.fileDestinationFolder = fileDestinationFolder;
	}

	@Override
	public void run() {
		if (fileDestinationFolder != null) {
			pev.setVisible(true);
			SoundButtonProperties sbpTemp;
			int zeilen = sbExport.getZeilen();
			int spalten = sbExport.getSpalten();
			fileLayerExport = new File(fileDestinationFolder.getPath() + "/Export.lay");
			fileMusicFolder = new File(fileDestinationFolder.getPath() + "/Musicdata");
			System.out.println(fileLayerExport.getPath());
			System.out.println(fileDestinationFolder.getPath());

			try {
				fileLayerExport.createNewFile();
				fileMusicFolder.mkdir();

				fileStream = new FileOutputStream(fileLayerExport);
				os = new ObjectOutputStream(fileStream);

				os.writeInt(sbExport.getZeilen());
				os.writeInt(sbExport.getSpalten());
				os.writeBoolean(sbExport.pbVisible);
				for (int z = 0; z < zeilen; z++) {
					for (int sp = 0; sp < spalten; sp++) {
						if (Thread.currentThread().isInterrupted() == false) {
							abbrechenExportProzess();
							os.writeObject(sbExport.getSbArray()[z][sp].getProperties());
							sbpTemp = sbExport.getSbArray()[z][sp].getProperties();
							if (sbpTemp.getButtonArt() != 99 && sbpTemp.getMusicPath() != null) {
								fileTemp = new File(fileMusicFolder.getPath() + "/" + sbpTemp.getMusicPath().getName());
								if (sbpTemp.getMusicPath().isFile() == true) {
									Files.copy(sbpTemp.getMusicPath().toPath(), fileTemp.toPath(),
											StandardCopyOption.REPLACE_EXISTING);
									System.out.println("Zeile: " + z + " Spalte: " + sp + " gespeichert (Datei)");
									abbrechenExportProzess();
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
										abbrechenExportProzess();
									}
									System.out.println("Zeile: " + z + " Spalte: " + sp + " gespeichert (Folder)");
								}
							}
							System.out.println(Thread.currentThread().getName());
							pev.updatePP();
						} else {
							return;
						}
					}
				}
				os.close();
				fileStream.close();
				System.out.println("=======Export Layer Ende==========");
			} catch (Exception ex) {
				System.out.println("Objekte konnten nicht vollständig gespeichert werden");
				System.out.println(ex.getMessage());
			}
			if (Thread.currentThread().isInterrupted() == false) {
				pev.setExportDone();
			}
		}

	}

	private void abbrechenExportProzess() {
		if (pev.isProzessCanceled() == true) {
			try {
				os.close();
				fileStream.close();
				pev.setExportAbgebrochen();
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void deleteDir(File path) {
		for (File file : path.listFiles()) {
			if (file.isDirectory())
				deleteDir(file);
			file.delete();
		}
		path.delete();
	}
}
