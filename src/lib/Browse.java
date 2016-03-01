package lib;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class Browse {
	private static JFileChooser fc = new JFileChooser();
	// private static JFileChooser fc = new JFileChooser(
	// "/Users/SCCplayer/Desktop/Musik");
	private static File musicFile;
	private static File musicFolder;
	private static File[] fileArray;
	private static File[] musicFileArray;
	private static FileFilter musicFileFilter = new FileNameExtensionFilter("Musik", "mp3", "wav", "m4a");
	private static FileFilter speicherFileFilter = new FileNameExtensionFilter("Java .ser", "ser");
	private static FileFilter soundBordFileFilter = new FileNameExtensionFilter("Java .sou", "sou");
	private static FileFilter layerFileFilter = new FileNameExtensionFilter("Java .lay", "lay");

	public static File getMusicFile() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(musicFileFilter);
		fc.setFileFilter(musicFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getSaveFile() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(speicherFileFilter);
		fc.setFileFilter(speicherFileFilter);
		int auswahl = fc.showSaveDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getSaveFileSou() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(soundBordFileFilter);
		fc.setFileFilter(soundBordFileFilter);
		int auswahl = fc.showSaveDialog(null);

		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = null;
			String path = fc.getSelectedFile().getPath();
			if (!path.toLowerCase().endsWith(".sou")) {
				path = path + ".sou";
				musicFile = new File(path);
			} else {
				musicFile = fc.getSelectedFile();
			}

		}
		return musicFile;
	}

	public static File getSaveFileLay() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(layerFileFilter);
		fc.setFileFilter(layerFileFilter);
		int auswahl = fc.showSaveDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getSaveFileLay(Font fontSize) {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		setFileChooserFont(fc.getComponents(), fontSize);
		fc.addChoosableFileFilter(layerFileFilter);
		fc.setFileFilter(layerFileFilter);
		int auswahl = fc.showSaveDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getOpenFile() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(speicherFileFilter);
		fc.setFileFilter(speicherFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getOpenFileSou() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(soundBordFileFilter);
		fc.setFileFilter(soundBordFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getOpenFileLay() {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(layerFileFilter);
		fc.setFileFilter(layerFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getOpenFileLay(Font fontSize) {
		musicFile = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		setFileChooserFont(fc.getComponents(), fontSize);
		fc.addChoosableFileFilter(layerFileFilter);
		fc.setFileFilter(layerFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getMusicFolder() {
		musicFolder = null;
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
		}
		return musicFolder;
	}

	public static File getFolder() {
		musicFolder = null;
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
		}
		return musicFolder;
	}

	public static File getFolder(Font fontSize) {
		musicFolder = null;
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setFileChooserFont(fc.getComponents(), fontSize);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
		}
		return musicFolder;
	}

	public static File getMusicFileFolder(Font fontSize) {
		musicFolder = null;
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setFileChooserFont(fc.getComponents(), fontSize);
		fc.addChoosableFileFilter(musicFileFilter);
		System.out.println(fc.getComponentCount());
		fc.setFileFilter(musicFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
			System.out.println(musicFolder.listFiles());
		}
		return musicFolder;
	}

	public static boolean hasFolderMusicfiles(File musicFolder) {
		fileArray = musicFolder.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			if ((fileArray[i].getName().endsWith(".mp3") == true || fileArray[i].getName().endsWith(".m4a") == true
					|| fileArray[i].getName().endsWith(".wav") == true) && fileArray[i].isHidden() == false) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMusicFileOrMusicFilefolder(File musicFolder) {
		if (musicFolder.isDirectory() == true) {
			fileArray = musicFolder.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				if ((fileArray[i].getName().endsWith(".mp3") == true || fileArray[i].getName().endsWith(".m4a") == true
						|| fileArray[i].getName().endsWith(".wav") == true) && fileArray[i].isHidden() == false) {
					return true;
				}
			}
		} else if (musicFolder.getName().endsWith(".mp3") == true || musicFolder.getName().endsWith(".m4a") == true
				|| musicFolder.getName().endsWith(".wav") == true) {
			return true;
		} else {
			return false;
		}
		return false;
	}

	public static File[] getMusicFileArray(File musicFolder) {
		fileArray = musicFolder.listFiles();
		int musicFileCounter = 0;
		for (int i = 0; i < fileArray.length; i++) {
			if ((fileArray[i].getName().endsWith(".mp3") == true || fileArray[i].getName().endsWith(".m4a") == true
					|| fileArray[i].getName().endsWith(".wma") == true) && fileArray[i].isHidden() == false) {
				musicFileCounter++;
			}
		}
		musicFileArray = new File[musicFileCounter];
		// musicFileArray = null;
		musicFileCounter = 0;
		// Schleife um MusicData mit daten zu füllen
		for (int i = 0; i < fileArray.length; i++) {
			if ((fileArray[i].getName().endsWith(".mp3") == true || fileArray[i].getName().endsWith(".m4a") == true
					|| fileArray[i].getName().endsWith(".mp3") == true) && fileArray[i].isHidden() == false) {
				musicFileArray[musicFileCounter] = fileArray[i];
				musicFileCounter++;
			}
		}
		return musicFileArray;
	}

	private static void setFileChooserFont(Component[] comp, Font fontSize) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				setFileChooserFont(((Container) comp[x]).getComponents(), fontSize);
			try {
				comp[x].setFont(fontSize);
			} catch (Exception e) {
			} // do nothing
		}
	}
}
