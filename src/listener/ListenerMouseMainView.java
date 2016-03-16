package listener;

import java.awt.Cursor;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import data.SbpChange;
import gui.DialogSoundButton;
import gui.MainView;
import gui.SoundBoard;
import gui.SoundButton;

public class ListenerMouseMainView implements MouseListener, MouseMotionListener {
	private MainView hf;
	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);

	private SoundBoard soundboardTemp;

	private SoundButton sbTarget;
	private SoundButton sbMouseOver;
	private SoundButton sbDragged;

	public ListenerMouseMainView(MainView hf) {
		this.hf = hf;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		SoundButton sbClicked;
		if (e.getSource() instanceof SoundButton) {
			sbClicked = (SoundButton) e.getSource();

			if (SwingUtilities.isLeftMouseButton(e)) {
				if (sbClicked.getButtonArt() != SoundButton.oneSongOwnPlayer) {
					if (hf.getTapeA() != null) {
						if (hf.getSbActive().istFadeOutTimerAktiv() == true) {
							hf.getSbActive().sbStop();
							sbClicked.sbPlay();
						} else {
							if ((hf.getSbActive() == sbClicked && hf.getSbActive().istPausiert != true)
									|| (sbClicked.getButtonArt() == 99 && hf.getSbActive().istPausiert != true)) {
								hf.setSbNext(null);
								hf.getSbActive().setStatusSoundButtonStop();
								hf.getSbActive().sbFadeOut();
							} else {
								if (sbClicked.getButtonArt() != 99) {
									if (sbClicked.getMusicPath().exists() == true) {
										hf.setSbNext(sbClicked);
										hf.getSbNext().changeColor();
										hf.getSbNext().sbStartBlink();
									}
									hf.getSbActive().sbFadeOut();
								}
							}
						}
					} else {
						if (sbClicked.getButtonArt() != 99) {
							sbClicked.sbPlay();
						}
					}
				} else if (sbClicked.getButtonArt() == SoundButton.oneSongOwnPlayer) {
					if (hf.getOwnPlayer() != null) {
						if (hf.getSbActiveOwnPlayer() != sbClicked) {
							hf.getSbActiveOwnPlayer().sbStop();
							sbClicked.sbPlay();
						} else {
							hf.getSbActiveOwnPlayer().sbStop();
						}
					} else {
						if (sbClicked.getButtonArt() != 99) {
							sbClicked.sbPlay();
						}
					}
				}

			} else if (SwingUtilities.isRightMouseButton(e)) {
				DialogSoundButton dsb = new DialogSoundButton((SoundButton) e.getSource(), hf.getActualFontSize());
			}
		}

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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		hf.setCursor(cursorHand);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		if (SwingUtilities.isLeftMouseButton(e) && hf.wasDragged == true && hf.getSbpSource() != null
				&& (hf.getSoundBoardActive().getMousePosition() != null) && e.getSource() instanceof SoundButton) {

			if (hf.getSoundBoardActive()
					.getComponentAt(hf.getSoundBoardActive().getMousePosition()) instanceof SoundButton) {
				if (hf.getKeyStrgPressed() == true) {
					System.out.println("CopyColor");
					sbTarget = (SoundButton) hf.getSoundBoardActive()
							.getComponentAt(hf.getSoundBoardActive().getMousePosition());
					hf.getSbpChangeStack().push(new SbpChange(sbTarget, sbTarget.getProperties()));
					sbTarget.setColors(hf.getSbpSource());
				} else {
					sbTarget = (SoundButton) hf.getSoundBoardActive()
							.getComponentAt(hf.getSoundBoardActive().getMousePosition());

					hf.getSbpChangeStack().push(new SbpChange(sbTarget, sbTarget.getProperties()));

					System.out.println(sbTarget.getProperties().getName() + " wurde im Stack gespeichert");
					sbTarget.setProperties(hf.getSbpSource());

					System.out.println("Soundbuttonquelle: " + hf.getSbpSource().getName() + "Soundbuttontarget: "
							+ hf.getSbpChangeStack().peek().getSbpLastUpdate().getName());
				}
				hf.wasDragged = false;
				hf.setSbpSource(null);
			}

		} else if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == hf.getPnlAnzeige()) {
			if (sbMouseOver != null && hf.getAnzeigePfad() != null) {
				System.out.println(sbMouseOver + hf.getAnzeigePfad().getName());
				if (sbMouseOver.getProperties().getButtonArt() == 99) {
					hf.getSbpChangeStack().push(new SbpChange(sbMouseOver, sbMouseOver.getProperties()));
					sbMouseOver.getProperties().setMusicPath(hf.getAnzeigePfad());
					sbMouseOver.setName(hf.getAnzeigePfad().getName());
					sbMouseOver.getProperties().setButtonArt(0);
					sbMouseOver = null;
				}
			}
		} else if (SwingUtilities.isLeftMouseButton(e) && hf.wasDragged == true && hf.getSbpSource() != null
				&& hf.getPnlHotButton().getSbHotKey().getMousePosition() != null
				&& e.getSource() instanceof SoundButton) {
			if (hf.getPnlHotButton().getSbHotKey()
					.getComponentAt(hf.getPnlHotButton().getSbHotKey().getMousePosition()) instanceof SoundButton) {
				if (hf.getKeyStrgPressed() == true) {
					System.out.println("CopyColor");
					sbTarget = (SoundButton) hf.getPnlHotButton().getSbHotKey()
							.getComponentAt(hf.getPnlHotButton().getSbHotKey().getMousePosition());
					hf.getSbpChangeStack().push(new SbpChange(sbTarget, sbTarget.getProperties()));
					sbTarget.setColors(hf.getSbpSource());
				} else {
					sbTarget = (SoundButton) hf.getPnlHotButton().getSbHotKey()
							.getComponentAt(hf.getPnlHotButton().getSbHotKey().getMousePosition());

					hf.getSbpChangeStack().push(new SbpChange(sbTarget, sbTarget.getProperties()));

					System.out.println(sbTarget.getProperties().getName() + " wurde im Stack gespeichert");
					sbTarget.setProperties(hf.getSbpSource());

					System.out.println("Soundbuttonquelle: " + hf.getSbpSource().getName() + "Soundbuttontarget: "
							+ hf.getSbpChangeStack().peek().getSbpLastUpdate().getName());
				}
				hf.wasDragged = false;
				hf.setSbpSource(null);
			}
		}
		hf.getSoundBoardActive().setCursor(cursorMove);
		hf.setCursor(cursorMove);
		hf.wasDragged = false;
		hf.setSbpSource(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (hf.getSoundBoardActive().getMousePosition() != null) {
			if (e.getSource() instanceof SoundButton) {
				if (hf.wasDragged == false) {
					sbDragged = (SoundButton) e.getSource();
					hf.setSbpSource(sbDragged.getProperties());
					System.out.println("Buttoneigenschaften wurden gespeichert." + sbDragged.getName());
					hf.wasDragged = true;
				}
				if (hf.getSoundBoardActive().getComponentAt(hf.getSoundBoardActive().getMousePosition()) != e
						.getSource() && hf.getSbpSource() != null) {
					hf.setCursor(DragSource.DefaultCopyDrop);
				}
			} else if (e.getSource() == hf.getPnlAnzeige()) {
				if (hf.getSoundBoardActive().getMousePosition() != null) {
					if (hf.getSoundBoardActive()
							.getComponentAt(hf.getSoundBoardActive().getMousePosition()) instanceof SoundButton) {
						sbMouseOver = (SoundButton) hf.getSoundBoardActive()
								.getComponentAt(hf.getSoundBoardActive().getMousePosition());
						if (sbMouseOver.getProperties().getButtonArt() == 99 && hf.getAnzeigePfad() != null) {
							System.out.println("Buttonart = 99");
							hf.getSoundBoardActive().setCursor(DragSource.DefaultCopyDrop);
						} else {
							hf.getSoundBoardActive().setCursor(cursorHand);
						}
					}
				}
			} else {
				hf.setCursor(cursorHand);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
