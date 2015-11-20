package data;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

public class SoundButtonProperties implements Serializable {

	private static final long serialVersionUID = 6310166706611600147L;
	private static int anzahl = 0;
	private int id = 0;
	private int buttonArt = 0;
	private String name;
	private File musicPath;
	// private MusicData[] songListe;
	private double volume = 0.5;
	private String totalDuration = "0:00";
	private Color foreground;
	private Color background;

	public SoundButtonProperties() {
		anzahl++;
		id = anzahl;
	}

	public void copyProperties(SoundButtonProperties sbp) {
		id = sbp.getID();
		buttonArt = sbp.getButtonArt();
		name = sbp.getName();
		if (sbp.getMusicPath() != null) {
			musicPath = sbp.getMusicPath();
		}
		volume = sbp.getVolume();
		totalDuration = sbp.getTotalDuration();
		foreground = sbp.getForeground();
		background = sbp.getBackground();
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public int getButtonArt() {
		return buttonArt;
	}

	public void setButtonArt(int buttonArt) {
		this.buttonArt = buttonArt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getMusicPath() {
		return musicPath;
	}

	public void setMusicPath(File musicPath) {
		this.musicPath = musicPath;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

}
