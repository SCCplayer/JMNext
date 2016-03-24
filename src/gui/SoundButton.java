package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data.SbpChange;
import data.SoundButtonProperties;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lib.Browse;
import lib.Info;
import lib.MyFonts;

public class SoundButton extends JPanel {

	private SoundBoard sb;

	private String initName;

	public boolean istPausiert = false;
	private int statusSoundButton = 0;

	private GridBagConstraints c = new GridBagConstraints();

	private int iconSize = 20;

	private File[] musicFileArray;

	public static final int oneSong = 0;
	public static final int shuffle = 1;
	public static final int multiSong = 2;
	public static final int loop = 3;
	public static final int oneSongOwnPlayer = 4;
	public static final int shuffleLoop = 5;

	public static final int statusStop = 0;
	public static final int statusPlay = 1;
	public static final int statusPause = 2;

	private Color helleGegenFarbe = new Color(90, 90, 90);
	private Color standardTextfarbe = new Color(51, 51, 51);

	private SoundButtonProperties properties = new SoundButtonProperties();

	private Font fontLblCounterLblDuration = new Font("Monospaced", Font.BOLD, 14);

	private boolean istBtnColorStandard = true;
	private int counterCicle = 0;
	private int counterMultiSong = 0;
	private int titelNummer = 0;

	private JLabel lblName = new JLabel();
	private JLabel lblShuffle = new JLabel("<HTML><BODY>&#xF074</BODY></HTML>");
	private JLabel lblLoop = new JLabel("<HTML><BODY>&#xF021</BODY></HTML>");
	private JLabel lblMultiSong = new JLabel("<HTML><BODY>&#xF021</BODY></HTML>");
	private JLabel lblCounterCicle = new JLabel("0");
	private JLabel lblDuration = new JLabel("0:00");

	private JLabel lblButtonStop = new JLabel("<HTML><BODY>&#xF04D</BODY></HTML>");
	private JLabel lblButtonPlay = new JLabel("<HTML><BODY>&#xF04B</BODY></HTML>");
	private JLabel lblButtonPause = new JLabel("<HTML><BODY>&#xF04C</BODY></HTML>");

	private JLabel lblWarning = new JLabel("<HTML><BODY>&#xF071</BODY></HTML>");
	private JLabel lblOwnPlayer = new JLabel("<HTML><BODY>&#xF0A1</BODY></HTML>");

	private BlinkListener bl = new BlinkListener();
	private Timer blinkTimer = new Timer(500, bl);

	private double volumeBlende = 0;
	private double volumePerStep = 0;
	private double volumeTapeA = 0;

	private int blendenCounterStartwert = 100;
	private int blendenCounter = blendenCounterStartwert;

	private FadeOutListener fadeOutListener = new FadeOutListener();
	private Timer fadeOutTimer = new Timer(10, fadeOutListener);

	private FadeInListener fadeInListener = new FadeInListener();
	private Timer fadeInTimer = new Timer(10, fadeInListener);

	private ProgressbarListener pbl = new ProgressbarListener();
	private Timer pbUpdateTimer = new Timer(10, pbl);

	private JProgressBar pbDuration = new JProgressBar(JProgressBar.VERTICAL, 0, 1000);

	private myDropTargetListener dtl = new myDropTargetListener();

	private SoundButton me;

	public SoundButton(SoundBoard parentSoundboard, String name) {
		me = this;
		sb = parentSoundboard;
		initName = name;
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		properties.setButtonArt(99);
		properties.setForeground(lblName.getForeground());
		setBackground(Color.WHITE);
		properties.setBackground(getBackground());
		properties.setName(name);
		lblName.setText(name);
		lblName.setPreferredSize(new Dimension(20, 14));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOwnPlayer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShuffle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMultiSong.setHorizontalAlignment(SwingConstants.RIGHT);
		lblButtonStop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblButtonPlay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblButtonPause.setHorizontalAlignment(SwingConstants.RIGHT);

		MyFonts.setFontText(lblCounterCicle);
		MyFonts.setFontText(lblName);
		MyFonts.setFontText(lblDuration);

		MyFonts.setFontSymbol(lblButtonPause);
		MyFonts.setFontSymbol(lblButtonPlay);
		MyFonts.setFontSymbol(lblButtonStop);
		MyFonts.setFontSymbol(lblOwnPlayer);
		MyFonts.setFontSymbol(lblMultiSong);
		MyFonts.setFontSymbol(lblLoop);
		MyFonts.setFontSymbol(lblWarning);
		MyFonts.setFontSymbol(lblShuffle);

		createBtnPanel();
		lblShuffle.setVisible(false);
		lblMultiSong.setVisible(false);
		lblLoop.setVisible(false);
		lblWarning.setVisible(false);
		lblOwnPlayer.setVisible(false);
		DropTarget dt = new DropTarget(this, dtl);
		setDropTarget(dt);
	}

	/*
	 * Aufbau Soundbutton - GridBagLayout: Aufteilung
	 * Grid: 3x3
	 * 
	 * ----------------------------------------------------
	 * |gridx = 0		|gridx = 1			|gridx = 2		|
	 * |gridy = 0		|gridy = 0			|gridy = 0		|
	 * |lblCounterCicle	|lblIconsButtonArt	|gridheight = 3	|
	 * -------------------------------------|pbDuration		|
	 * |gridx = 0							|				|
	 * |gridy = 1							|				|
	 * |gridwidth = 2						|				|	
	 * |lblName 							|				|
	 * -------------------------------------|				|
	 * |gridx = 0		|gridx = 1			|				|
	 * |gridy = 2		|gridy = 2			|				|
	 * |lblDuration		|lblPause/Stop/Play	|				|
	 * -----------------------------------------------------
	 */

	public void createBtnPanel() {
		setLayout(new GridBagLayout());
		// lblCounterCicle (Oben links)
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 3;
		c.weightx = 0;
		c.weighty = 0;
		lblCounterCicle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
		add(lblCounterCicle, c);

		c.ipady = 0;
		c.ipadx = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		lblWarning.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 8));
		add(lblWarning, c);

		c.ipady = 0;
		c.ipadx = 2;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		lblShuffle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 8));
		add(lblShuffle, c);
		add(lblLoop, c);

		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		lblMultiSong.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 8));
		add(lblMultiSong, c);

		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		lblOwnPlayer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 8));
		add(lblOwnPlayer, c);

		// lblDuration (Unten links)
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		lblDuration.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 0));
		add(lblDuration, c);

		// Anzeige Play/Pause/Stop (unten Rechts)
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		lblButtonStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 8));
		lblButtonPlay.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 8));
		lblButtonPause.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 8));
		add(lblButtonStop, c);
		lblButtonPlay.setVisible(false);
		lblButtonPause.setVisible(false);
		lblButtonStop.setVisible(false);
		add(lblButtonPlay, c);
		add(lblButtonPause, c);

		// lblName (Mitte)
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.1;
		c.weighty = 1.0;
		c.gridwidth = 2;
		add(lblName, c);

		// pbDuration Progressbar (Rechts)
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridheight = 3;
		pbDuration.setValue(1000);
		pbDuration.setForeground(new Color(0, 176, 103));
		add(pbDuration, c);
	}

	public void sbPlay() {
		if (getButtonArt() != 99 && properties.getMusicPath() != null) {
			if (properties.getMusicPath().exists() == true) {
				if (properties.getButtonArt() != oneSongOwnPlayer) {
					blendenCounterStartwert = sb.getZeitBlende();
					blendenCounter = blendenCounterStartwert;
					changeColor();
					sb.setTapeA(new MediaPlayer(new Media(getMusicPathASCII())));
					sb.getTapeA().play();
					if (sb.getOwnPlayer() == null) {
						sb.getTapeA().setVolume(getVolume());
					} else {
						sb.getSbActiveOwnPlayer().setVolumeTapeA(getVolume());
						sb.getTapeA().setVolume(0.1);
						System.out.println("Volume Standardplayer: " + getVolume());
					}

					lblCounterUp();
					setStatusSoundButtonPlay();
					if (blinkTimer.isRunning() == false) {
						blinkTimer.start();
					}
					pbUpdateTimer.start();
					sb.setSbActive(this);
				} else {
					blendenCounterStartwert = sb.getZeitBlende();
					blendenCounter = blendenCounterStartwert;
					changeColor();
					sb.setOwnPlayer(new MediaPlayer(new Media(getMusicPathASCII())));
					if (sb.getTapeA() != null) {
						volumeTapeA = sb.getTapeA().getVolume();
						sb.getTapeA().setVolume(0.1);
					}
					sb.getOwnPlayer().play();
					sb.getOwnPlayer().setVolume(getVolume());
					setStatusSoundButtonPlay();
					if (blinkTimer.isRunning() == false) {
						blinkTimer.start();
					}
					pbUpdateTimer.start();
					sb.setSbActiveOwnPlayer(this);
				}
			}
		}
	}

	public void sbStop() {
		if (properties.getButtonArt() != oneSongOwnPlayer) {
			if (sb.getSbActive() != null) {
				setStatusSoundButtonAfterStop();
				fadeOutTimer.stop();
				sb.getTapeA().stop();
				sb.getTapeA().dispose();
				sb.setTapeA(null);
				blinkTimer.stop();
				pbUpdateTimer.stop();
				setColorStandard();
				istPausiert = false;
				setAnzeigeZuruecksetzen();
				sb.setSbActive(null);
			}
		} else {
			if (sb.getSbActiveOwnPlayer() != null) {
				setStatusSoundButtonAfterStop();
				fadeOutTimer.stop();
				sb.getOwnPlayer().stop();
				sb.getOwnPlayer().dispose();
				sb.setOwnPlayer(null);
				if (sb.getTapeA() != null) {
					sb.getTapeA().setVolume(volumeTapeA);
				}
				blinkTimer.stop();
				pbUpdateTimer.stop();
				setColorStandard();
				istPausiert = false;
				setAnzeigeZuruecksetzen();
				sb.setSbActiveOwnPlayer(null);
			}
		}
	}

	public void sbFadeOut() {
		blendenCounterStartwert = sb.getZeitBlende();
		blendenCounter = blendenCounterStartwert;
		fadeOutTimer.start();
	}

	public void sbFadeIn() {
		blendenCounterStartwert = sb.getZeitBlende();
		blendenCounter = blendenCounterStartwert;
		fadeInTimer.start();
	}

	public void sbStartBlink() {
		blinkTimer.start();
	}

	public void setProperties(int btnArt, String name, File musicPath, double volume, String totalDuration,
			Color foreground, Color background) {
		properties.setButtonArt(btnArt);
		setIcon();
		properties.setName(name);
		lblName.setText(properties.getName());
		properties.setMusicPath(musicPath);
		if (musicPath != null) {
			if (properties.getMusicPath().listFiles() != null) {
				setMusicFileArray(Browse.getMusicFileArray(properties.getMusicPath()));
				setLblDuration(String.valueOf(Browse.getMusicFileArray(properties.getMusicPath()).length));
			}
		}
		properties.setVolume(volume);
		properties.setTotalDuration(totalDuration);
		lblDuration.setText(properties.getTotalDuration());
		properties.setForeground(foreground);
		setLabelsTextColor(properties.getForeground());
		properties.setBackground(background);
		setBackground(properties.getBackground());
	}

	public void setProperties(SoundButtonProperties properties) {
		this.properties.copyProperties(properties);
		if (properties.getButtonArt() == 99 || properties.getMusicPath() == null) {

		} else {
			if (properties.getMusicPath().listFiles() != null) {
				setMusicFileArray(Browse.getMusicFileArray(properties.getMusicPath()));
				setLblDuration(String.valueOf(Browse.getMusicFileArray(properties.getMusicPath()).length));
			}
		}
		setIcon();
		lblName.setText(properties.getName());
		lblDuration.setText(properties.getTotalDuration());
		setLabelsTextColor(properties.getForeground());
		setBackground(properties.getBackground());
	}

	public SoundButtonProperties getProperties() {
		return properties;
	}

	public void pbAusblenden() {
		pbDuration.setVisible(false);
	}

	public void pbEinblenden() {
		pbDuration.setVisible(true);
	}

	public void setIcon() {
		if (properties.getButtonArt() == SoundButton.oneSong || properties.getButtonArt() == 99) {
			lblShuffle.setVisible(false);
			lblLoop.setVisible(false);
			lblMultiSong.setVisible(false);
			lblWarning.setVisible(false);
			lblOwnPlayer.setVisible(false);
			setIconWarning();
		} else if (properties.getButtonArt() == SoundButton.shuffle) {
			lblLoop.setVisible(false);
			lblMultiSong.setVisible(false);
			lblShuffle.setVisible(true);
			lblWarning.setVisible(false);
			lblOwnPlayer.setVisible(false);
			setIconWarning();
		} else if (properties.getButtonArt() == SoundButton.multiSong) {
			lblLoop.setVisible(false);
			lblMultiSong.setVisible(true);
			lblShuffle.setVisible(false);
			lblWarning.setVisible(false);
			lblOwnPlayer.setVisible(false);
			setIconWarning();
		} else if (properties.getButtonArt() == SoundButton.loop) {
			lblShuffle.setVisible(false);
			lblMultiSong.setVisible(false);
			lblWarning.setVisible(false);
			lblLoop.setVisible(true);
			lblOwnPlayer.setVisible(false);
			setIconWarning();
		} else if (properties.getButtonArt() == SoundButton.oneSongOwnPlayer) {
			lblShuffle.setVisible(false);
			lblMultiSong.setVisible(false);
			lblWarning.setVisible(false);
			lblLoop.setVisible(false);
			lblOwnPlayer.setVisible(true);
			setIconWarning();
		}
	}

	public void setIconWarning() {
		if (properties.getMusicPath() != null) {
			if (properties.getMusicPath().exists() == false) {
				lblShuffle.setVisible(false);
				lblMultiSong.setVisible(false);
				lblWarning.setVisible(true);
				lblLoop.setVisible(false);
			}
		}
	}

	public void setLblDuration(String duration) {
		lblDuration.setText(duration);
	}

	public void setTotalDuration(String duration) {
		if (properties.getButtonArt() == 0 || properties.getButtonArt() == 99
				|| properties.getButtonArt() == oneSongOwnPlayer) {
			properties.setTotalDuration(duration);
			lblDuration.setText(properties.getTotalDuration());
		} else if (properties.getButtonArt() == 1 || properties.getButtonArt() == 2) {
			properties.setTotalDuration(String.valueOf(musicFileArray.length));
			lblDuration.setText(properties.getTotalDuration());
		}
	}

	public String getTotalDuration() {
		return properties.getTotalDuration();
	}

	public void lblCounterUp() {
		counterCicle++;
		lblCounterCicle.setText(String.valueOf(counterCicle));
	}

	public Timer getFadeOutTimer() {
		return fadeOutTimer;
	}

	public Timer getFadeInTimer() {
		return fadeInTimer;
	}

	public void setCounterCicle(int anzahl) {
		counterCicle = anzahl;
		lblCounterCicle.setText(String.valueOf(counterCicle));
	}

	public void resetCounterCicle() {
		counterCicle = 0;
		lblCounterCicle.setText(String.valueOf(counterCicle));
	}

	public double getVolume() {
		return properties.getVolume();
	}

	public void setVolume(int volume) {
		properties.setVolume((double) volume / 100);
	}

	public void setAnzeigeZuruecksetzen() {
		pbDuration.setValue(1000);
		sb.setPbMainView(1000);
		lblDuration.setText(properties.getTotalDuration());
	}

	public int getButtonArt() {
		return properties.getButtonArt();
	}

	public void setButtonArt(int buttonArt) {
		properties.setButtonArt(buttonArt);
	}

	public File getMusicPath() {
		return properties.getMusicPath();
	}

	public void setForegroundColorStandard(Color foreground) {
		properties.setForeground(foreground);
	}

	public Color getForegroundColorStandard() {
		return properties.getForeground();
	}

	public String getMusicPathASCII() {
		Date myDate = new Date();
		String timeStamp = myDate.toString().split(" ")[3];
		if (properties.getMusicPath() != null) {
			if (properties.getButtonArt() == 0 || properties.getButtonArt() == 4) {
				if (properties.getButtonArt() != oneSongOwnPlayer) {
					sb.setTitelAnzeige(getMusicPath().getName());
					sb.setAnzeigePfad(properties.getMusicPath());
				}
				sb.getHf().addSongsPlayed(timeStamp.toString() + " " + getMusicPath().getName());

				return properties.getMusicPath().toURI().toASCIIString();
			} else if (properties.getButtonArt() == 1) {
				Random shuffle = new Random();
				int zufallsZahl = shuffle.nextInt(musicFileArray.length);
				System.out.println(zufallsZahl + ": " + musicFileArray[zufallsZahl].getName());
				sb.setTitelAnzeige(musicFileArray[zufallsZahl].getName());

				sb.getHf().addSongsPlayed(timeStamp.toString() + " " + musicFileArray[zufallsZahl].getName());
				sb.setAnzeigePfad(musicFileArray[zufallsZahl]);
				return musicFileArray[zufallsZahl].toURI().toASCIIString();
			} else if (properties.getButtonArt() == 2) {
				if (counterMultiSong < musicFileArray.length) {
				} else {
					counterMultiSong = 0;
				}
				titelNummer = counterMultiSong;
				counterMultiSong++;
				System.out.println(musicFileArray.length);
				sb.setTitelAnzeige(musicFileArray[titelNummer].getName());
				sb.getHf().addSongsPlayed(timeStamp.toString() + " " + musicFileArray[titelNummer].getName());
				sb.setAnzeigePfad(musicFileArray[titelNummer]);
				return musicFileArray[titelNummer].toURI().toASCIIString();
			}
		}
		return null;
	}

	public void setBackgroundColorStandard(Color background) {
		properties.setBackground(background);
	}

	public void setPbDurationValue(int value) {
		pbDuration.setValue(value);
		if (properties.getButtonArt() != oneSongOwnPlayer) {
			sb.setPbMainView(value);
		}
	}

	public void setMusicPath(File musicPath) {
		properties.setMusicPath(musicPath);
	}

	public String getName() {
		return properties.getName();
	}

	public void setName(String name) {
		lblName.setText(name);
		properties.setName(name);
	}

	public void setMusicFileArray(File[] musicFileArray) {
		this.musicFileArray = musicFileArray;
	}

	public String getMusicPathString() {
		return properties.getMusicPath().getPath();
	}

	public void setColorStandard() {
		setBackground(properties.getBackground());
		setLabelsTextColor(properties.getForeground());
		if (properties.getButtonArt() != oneSongOwnPlayer) {
			sb.getHf().setColorStandardPnlAnzeigeCenter();
		}
		istBtnColorStandard = true;
	}

	public boolean getIstButtonColorStandard() {
		return istBtnColorStandard;
	}

	public void changeColor() {
		if (istBtnColorStandard == true) {
			setBackground(properties.getForeground());
			setLabelsTextColor(properties.getBackground());
			istBtnColorStandard = false;
			if (properties.getButtonArt() != oneSongOwnPlayer) {
				sb.getHf().changeColorPnlAnzeigeCenter(properties.getBackground());
			}
		} else {
			setColorStandard();
			istBtnColorStandard = true;
		}
	}

	private class BlinkListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeColor();
		}
	}

	public void setLabelsTextColor(Color foreground) {
		lblCounterCicle.setForeground(foreground);
		lblDuration.setForeground(foreground);
		lblName.setForeground(foreground);
		lblButtonPlay.setForeground(foreground);
		lblButtonPause.setForeground(foreground);
		lblButtonStop.setForeground(foreground);
		lblLoop.setForeground(foreground);
		lblOwnPlayer.setForeground(foreground);
		lblMultiSong.setForeground(foreground);
		lblShuffle.setForeground(foreground);
	}

	public boolean istFadeOutTimerAktiv() {
		if (fadeOutTimer.isRunning() == true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean istFadeInTimerAktiv() {
		if (fadeInTimer.isRunning() == true) {
			return true;
		} else {
			return false;
		}
	}

	public void reset() {
		properties.setBackground(Color.white);
		properties.setForeground(standardTextfarbe);
		setLabelsTextColor(properties.getForeground());
		setBackground(properties.getBackground());
		properties.setMusicPath(null);
		setName(initName);
		properties.setButtonArt(99);
		setCounterCicle(0);
		setTotalDuration("0:00");
		setIcon();
	}

	public void setColors(SoundButtonProperties sbpSource) {
		properties.setBackground(sbpSource.getBackground());
		properties.setForeground(sbpSource.getForeground());
		setLabelsTextColor(properties.getForeground());
		setBackground(properties.getBackground());
	}

	public void setStatusSoundButtonStop() {
		lblButtonPlay.setVisible(false);
		lblButtonPause.setVisible(false);
		lblButtonStop.setVisible(true);
	}

	public void setStatusSoundButtonAfterStop() {
		lblButtonPlay.setVisible(false);
		lblButtonPause.setVisible(false);
		lblButtonStop.setVisible(false);
	}

	public void setStatusSoundButtonPlay() {
		lblButtonPlay.setVisible(true);
		lblButtonPause.setVisible(false);
		lblButtonStop.setVisible(false);
	}

	public void setStatusSoundButtonPause() {
		lblButtonPlay.setVisible(false);
		lblButtonPause.setVisible(true);
		lblButtonStop.setVisible(false);
	}

	private class FadeOutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (sb.getTapeA() != null) {
				volumeBlende = sb.getTapeA().getVolume();
				volumePerStep = volumeBlende / blendenCounter;
				volumeBlende = volumeBlende - volumePerStep;
				sb.getTapeA().setVolume(volumeBlende);
				if (blendenCounter != 0) {
					blendenCounter--;
				}
				if (sb.getSbNext() != null) {
					sbStop();
					sb.getSbNext().sbPlay();
					sb.setSbActive(sb.getSbNext());
					sb.setSbNext(null);
					blendenCounter = blendenCounterStartwert;
				}
				if ((blendenCounter == 0 && istPausiert == false) || (blendenCounter == 0 && sb.getSbNext() != null)) {
					sbStop();
					blendenCounter = blendenCounterStartwert;
					if (sb.getSbNext() != null) {
						sb.getSbNext().sbPlay();
						sb.setSbActive(sb.getSbNext());
						sb.setSbNext(null);
					}
				} else if (blendenCounter == 0 && istPausiert == true && sb.getSbNext() == null) {
					System.out.println("Pause");
					fadeOutTimer.stop();
					blendenCounter = blendenCounterStartwert;
					sb.getTapeA().pause();
				}
			}
		}
	}

	private class FadeInListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (sb.getTapeA() != null) {
				sb.getTapeA().play();
				volumeBlende = sb.getTapeA().getVolume();
				volumePerStep = (getVolume() - volumeBlende) / blendenCounter;
				volumeBlende = volumeBlende + volumePerStep;
				sb.getTapeA().setVolume(volumeBlende);
				// System.out.print(blendenCounter + " FadeIn");
				blendenCounter--;
				// System.out.println(" " + sb.getTapeA().getVolume());
				if (blendenCounter == 0) {
					blendenCounter = blendenCounterStartwert;
					fadeInTimer.stop();
				} else {

				}
			}
		}
	}

	private class ProgressbarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getTotalDuration().compareTo("0:00") == 0) {
				if (properties.getButtonArt() != oneSongOwnPlayer) {
					setTotalDuration(Info.getTotalDuration(sb.getTapeA()));
				} else {
					setTotalDuration(Info.getTotalDuration(sb.getOwnPlayer()));
				}

			}
			if (sb.getTapeA() != null && properties.getButtonArt() != oneSongOwnPlayer) {
				setLblDuration(Info.getRestzeit(sb.getTapeA()));
				setPbDurationValue(Info.getPercent(sb.getTapeA()));

				if (Info.getRestzeitSekunde(sb.getTapeA()) == 0 && getButtonArt() == shuffleLoop) {
					sbStop();
					sb.setTapeA(null);
					sbPlay();
				}
				if (Info.getRestzeitSekunde(sb.getTapeA()) == 0
						&& (getButtonArt() == oneSong || getButtonArt() == shuffle || getButtonArt() == multiSong)) {
					sbStop();
					sb.setTapeA(null);
					pbUpdateTimer.stop();
					sb.setSbActive(null);
				}
			}
			if (sb.getOwnPlayer() != null && properties.getButtonArt() == oneSongOwnPlayer) {
				setLblDuration(Info.getRestzeit(sb.getOwnPlayer()));
				setPbDurationValue(Info.getPercent(sb.getOwnPlayer()));

				if (Info.getRestzeitSekunde(sb.getOwnPlayer()) == 0 && (getButtonArt() == oneSongOwnPlayer)) {
					sbStop();
					sb.setOwnPlayer(null);
					pbUpdateTimer.stop();
					sb.setSbActiveOwnPlayer(null);
				}
			}
		}
	}

	private class myDropTargetListener implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			// TODO Auto-generated method stub
			System.out.println("Drop");
			try {
				Transferable tr = dtde.getTransferable();
				DataFlavor[] flavors = tr.getTransferDataFlavors();
				for (int i = 0; i < flavors.length; i++) {
					if (flavors[i].isFlavorJavaFileListType()) {
						// Accept dropped file
						dtde.acceptDrop(dtde.getDropAction());
						// Write the path from the first file into the
						// JTextField tfTest
						File tempFile = new File(tr.getTransferData(flavors[i]).toString().substring(1,
								tr.getTransferData(flavors[i]).toString().length() - 1));

						if (Browse.isMusicFileOrMusicFilefolder(tempFile) == true) {

							sb.getHf().getSbpChangeStack().push(new SbpChange(me, me.getProperties()));

							setMusicPath(new File(tr.getTransferData(flavors[i]).toString().substring(1,
									tr.getTransferData(flavors[i]).toString().length() - 1)));
							// System.out.println(tr.getTransferData(flavors[i]).toString());

							setName(getMusicPath().getName());
							if (getMusicPath().isDirectory() == true) {
								if (getMusicPath() != null) {
									if (properties.getMusicPath().listFiles() != null) {
										setMusicFileArray(Browse.getMusicFileArray(properties.getMusicPath()));
										setLblDuration(String
												.valueOf(Browse.getMusicFileArray(properties.getMusicPath()).length));
									}
								}
								setButtonArt(multiSong);
								setIcon();
							} else {
								setButtonArt(oneSong);
							}

							System.out.println(tr.getTransferData(flavors[i]).toString().substring(1,
									tr.getTransferData(flavors[i]).toString().length() - 1));
						}

						dtde.dropComplete(true);
						return;
					}
				}
			}

			catch (Throwable t) {
				t.printStackTrace();
			}
			// If an error occurred
			dtde.rejectDrop();
			System.out.println("DragAndDrop Fehlgeschlagen");
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub

		}

	}

	public void setVolumeTapeA(double volumeTapeA) {
		this.volumeTapeA = volumeTapeA;
	}
}
