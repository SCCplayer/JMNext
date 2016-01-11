package gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import data.SbpChange;
import data.SoundButtonProperties;
import javafx.scene.media.MediaPlayer;
import lib.MyFonts;

public class SoundBoard extends JPanel {
	private MainView hf;
	private SoundButton[][] sbArray;
	private SoundButton[][] sbArrayChange;
	private SoundButton sbTarget;
	private ListenerMouseKlick lmk = new ListenerMouseKlick();

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;

	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);

	private int zeilen;
	private int spalten;

	public boolean pbVisible = true;

	public SoundBoard(MainView parent, int zeilen, int spalten) {
		this.hf = parent;
		this.zeilen = zeilen;
		this.spalten = spalten;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(this,
						String.valueOf(spalten * z + sp));
				sbArray[z][sp].addMouseListener(lmk);
				sbArray[z][sp].addMouseMotionListener(lmk);
				add(sbArray[z][sp]);
			}
		}
	}

	public SoundBoard(MainView parent, SoundButtonProperties[][] sbpArray,
			boolean pbVisble) {
		this.hf = parent;
		this.pbVisible = pbVisble;
		zeilen = sbpArray.length;
		spalten = sbpArray[0].length;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(this,
						String.valueOf(spalten * z + sp));
				sbArray[z][sp].addMouseListener(lmk);
				sbArray[z][sp].addMouseMotionListener(lmk);
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
					sbArrayChange[z][sp].addMouseListener(lmk);
					sbArrayChange[z][sp].addMouseMotionListener(lmk);
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
					sbArrayChange[z][sp].addMouseListener(lmk);
					sbArrayChange[z][sp].addMouseMotionListener(lmk);
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

	private class ListenerMouseKlick
			implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							if (hf.getTapeA() != null) {
								if (hf.getSbActive()
										.istFadeOutTimerAktiv() == true) {
									hf.getSbActive().sbStop();
									sbArray[z][sp].sbPlay();
								} else {
									if ((hf.getSbActive() == sbArray[z][sp]
											&& hf.getSbActive().istPausiert != true)
											|| (sbArray[z][sp]
													.getButtonArt() == 99
													&& hf.getSbActive().istPausiert != true)) {
										hf.setSbNext(null);
										hf.getSbActive()
												.setStatusSoundButtonStop();
										hf.getSbActive().sbFadeOut();
									} else {
										if (sbArray[z][sp]
												.getButtonArt() != 99) {
											hf.setSbNext(sbArray[z][sp]);
											hf.getSbNext().changeColor();
											hf.getSbNext().sbStartBlink();
											hf.getSbActive().sbFadeOut();
										}
									}
								}
							} else {
								if (sbArray[z][sp].getButtonArt() != 99) {
									sbArray[z][sp].sbPlay();
								}
							}
						}
					}
				}

			} else if (SwingUtilities.isRightMouseButton(e)) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							DialogSoundButton dsb = new DialogSoundButton(
									sbArray[z][sp], hf.getActualFontSize());
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			setCursor(cursorHand);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && hf.wasDragged == true
					&& hf.getSbpSource() != null
					&& getMousePosition() != null) {
				if (getComponentAt(getMousePosition()) instanceof SoundButton) {
					sbTarget = (SoundButton) getComponentAt(getMousePosition());

					hf.getSbpChangeStack().push(
							new SbpChange(sbTarget, sbTarget.getProperties()));
					System.out.println(sbTarget.getProperties().getName()
							+ " wurde im Stack gespeichert");
					sbTarget.setProperties(hf.getSbpSource());
					System.out.println("Soundbuttonquelle: "
							+ hf.getSbpSource().getName()
							+ "Soundbuttontarget: " + hf.getSbpChangeStack()
									.peek().getSbpLastUpdate().getName());
					hf.wasDragged = false;
					hf.setSbpSource(null);
				}
			}
			setCursor(cursorMove);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println(getMousePosition());
			if (getMousePosition() != null) {
				if (hf.getSoundBoardActive().getComponentAt(
						getMousePosition()) instanceof SoundButton) {
					if (hf.wasDragged == false) {
						System.out.println("Soundbutton");
						for (int z = 0; z < sbArray.length; z++) {
							for (int sp = 0; sp < sbArray[z].length; sp++) {
								if (e.getSource() == sbArray[z][sp]) {
									hf.setSbpSource(
											sbArray[z][sp].getProperties());
									System.out.println(
											"Buttoneigenschaften wurden gespeichert."
													+ sbArray[z][sp].getName());
									hf.wasDragged = true;
								}
							}
						}
					}
				}
				if (getComponentAt(getMousePosition()) != e.getSource()
						&& hf.getSbpSource() != null) {
					System.out.println("Hallo");
					setCursor(DragSource.DefaultCopyDrop);
				} else {
					setCursor(cursorHand);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
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
