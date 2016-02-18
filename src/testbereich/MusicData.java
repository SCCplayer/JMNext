package testbereich;

import java.io.File;
import java.io.Serializable;

public class MusicData implements Serializable {
	private static final long serialVersionUID = 1248418379876134634L;
	private File musicFile;
	private double volume;

	public MusicData(File musicFile, double volume) {
		this.musicFile = musicFile;
		this.volume = volume;
	}

	public File getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(File musicFile) {
		this.musicFile = musicFile;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

}
