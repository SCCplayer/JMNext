package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;

import data.SbpChange;
import data.SoundButtonProperties;
import javafx.scene.media.MediaPlayer;
import lib.MyFonts;
import listener.ListenerMouseMainView;

public class SoundBoard extends JPanel {
	private MainView hf;
	private SoundButton[][] sbArray;
	private SoundButton[][] sbArrayChange;
	private SoundButton sbTarget;
	private ListenerMouseMainView lmmv;

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;

	private int zeilen;
	private int spalten;

	public boolean pbVisible = true;

	public SoundBoard(MainView parent, int zeilen, int spalten,
			ListenerMouseMainView lmmv) {
		this.hf = parent;
		this.lmmv = lmmv;
		this.zeilen = zeilen;
		this.spalten = spalten;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(this,
						String.valueOf(spalten * z + sp));
				sbArray[z][sp].addMouseListener(lmmv);
				sbArray[z][sp].addMouseMotionListener(lmmv);
				add(sbArray[z][sp]);
			}
		}
	}

	public SoundBoard(MainView parent, SoundButtonProperties[][] sbpArray,
			boolean pbVisble, ListenerMouseMainView lmmv) {
		this.hf = parent;
		this.pbVisible = pbVisble;
		this.lmmv = lmmv;
		zeilen = sbpArray.length;
		spalten = sbpArray[0].length;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(this,
						String.valueOf(spalten * z + sp));
				sbArray[z][sp].addMouseListener(lmmv);
				sbArray[z][sp].addMouseMotionListener(lmmv);
				add(sbArray[z][sp]);
				sbArray[z][sp].setProperties(sbpArray[z][sp]);
			}
		}
		if (pbVisble == true) {
			pbEinblenden();
		} else {
			pbAusblenden();
		}
	}

	public void addSpalte() {
		int counter = zeilen * spalten;
		spalten++;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				if (sp + 1 != spalten) {
					sbArrayChange[z][sp] = sbArray[z][sp];
				} else {
					sbArrayChange[z][sp] = new SoundButton(this,
							String.valueOf(counter));
					sbArrayChange[z][sp].addMouseListener(lmmv);
					sbArrayChange[z][sp].addMouseMotionListener(lmmv);
					counter++;
				}
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		if (pbVisible == false) {
			pbAusblenden();
		}
		MyFonts.guiResizeFont(getComponents(), hf.getActualFontSize());
		validate();
		repaint();
	}

	public void removeSpalte() {
		spalten--;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArrayChange[z][sp] = sbArray[z][sp];
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
	}

	public void addZeile() {
		int counter = zeilen * spalten;
		zeilen++;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				if (z + 1 != zeilen) {
					sbArrayChange[z][sp] = sbArray[z][sp];
				} else {
					sbArrayChange[z][sp] = new SoundButton(this,
							String.valueOf(counter));
					sbArrayChange[z][sp].addMouseListener(lmmv);
					sbArrayChange[z][sp].addMouseMotionListener(lmmv);
					counter++;
				}
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		if (pbVisible == false) {
			pbAusblenden();
		}
		MyFonts.guiResizeFont(getComponents(), hf.getActualFontSize());
		validate();
		repaint();
	}

	public void removeZeile() {
		zeilen--;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArrayChange[z][sp] = sbArray[z][sp];
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
	}

	public void undoChange() {
		if (hf.getSbpChangeStack().empty() == false) {
			SbpChange sbpChange = hf.getSbpChangeStack().pop();
			System.out.println(sbpChange.getSbpLastUpdate().getName()
					+ " wird wieder hergestellt");
			sbpChange.getSbLastUpdate()
					.setProperties(sbpChange.getSbpLastUpdate());
			System.out.println("Undo changes");
		}
	}

	public void pausePlayer() {
		if (hf.getSbActive() != null) {
			if (hf.getSbActive().istPausiert == true
					|| hf.getSbActive().istFadeOutTimerAktiv() == true) {
				if (hf.getSbActive().istFadeOutTimerAktiv() == true) {
					hf.getSbActive().getFadeOutTimer().stop();
					hf.getSbActive().istPausiert = false;
					hf.getSbActive().setStatusSoundButtonPlay();
					hf.getSbActive().sbFadeIn();
				}
				hf.getSbActive().istPausiert = false;
				hf.getSbActive().setStatusSoundButtonPlay();
				hf.getSbActive().sbFadeIn();
			} else {
				if (hf.getSbActive().istFadeInTimerAktiv() == true) {
					hf.getSbActive().getFadeInTimer().stop();
					hf.getSbActive().istPausiert = true;
					hf.getSbActive().setStatusSoundButtonPause();
					hf.getSbActive().sbFadeOut();
				}
				hf.getSbActive().istPausiert = true;
				hf.getSbActive().setStatusSoundButtonPause();
				hf.getSbActive().sbFadeOut();
			}
		}
	}

	public void setAnzeigePfad(File musicPath) {
		hf.setAnzeigePfad(musicPath);
	}

	public void pbAusblenden() {
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp].pbAusblenden();
			}
		}
		pbVisible = false;
	}

	public void pbEinblenden() {
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp].pbEinblenden();
			}
		}
		pbVisible = true;
	}

	public void setSizeOfButtonelements(Font myFont) {
		MyFonts.guiResizeFont(getComponents(), myFont);
	}

	public MediaPlayer getTapeA() {
		return hf.getTapeA();
	}

	public void setTapeA(MediaPlayer tapeA) {
		hf.setTapeA(tapeA);
	}

	public void setTitelAnzeige(String titel) {
		hf.setTitelAnzeige(titel);
	}

	public SoundButton getSbActive() {
		return hf.getSbActive();
	}

	public void setSbActive(SoundButton sb) {
		hf.setSbActive(sb);
		;
	}

	public SoundButton getSbNext() {
		return hf.getSbNext();
	}

	public void setSbNext(SoundButton sb) {
		hf.setSbNext(sb);
	}

	public int getZeilen() {
		return zeilen;
	}

	public void setZeile(int zeilen) {
		this.zeilen = zeilen;
	}

	public int getSpalten() {
		return spalten;
	}

	public void setSpalten(int spalten) {
		this.spalten = spalten;
	}

	public SoundButton[][] getSbArray() {
		return sbArray;
	}

	public int getZeitBlende() {
		return hf.getZeitBlende();
	}

}
