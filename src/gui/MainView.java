package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Stack;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.SbpChange;
import data.SoundButtonProperties;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import lib.Browse;
import lib.ExportLayer;
import lib.MyFonts;
import lib.SaveLoad;
import listener.ListenerMouseMainView;

public class MainView extends JFrame {
	private SideView sv;
	private ListenerKeyboard lkb = new ListenerKeyboard();
	private ListenerMenuBar lmb = new ListenerMenuBar(this);

	private ListenerChange lc = new ListenerChange();
	private Vector<SoundBoard> sbVector = new Vector<SoundBoard>();

	private JButton btnAddZeile;
	private JButton btnRemoveZeile;
	private JButton btnAddSpalte;
	private JButton btnRemoveSpalte;

	private ImageIcon iconAddZeile;
	private ImageIcon iconAddZeileScale;
	private ImageIcon iconRemoveZeile;
	private ImageIcon iconRemoveZeileScale;
	private ImageIcon iconAddSpalte;
	private ImageIcon iconAddSpalteScale;
	private ImageIcon iconRemoveSpalte;
	private ImageIcon iconRemoveSpalteScale;

	private SoundBoard soundBoardActive;

	private boolean keyStrgPressed = false;

	private int zeitBlende = 0; // 100 entspricht 1 sec

	private FensterListener fl = new FensterListener();
	private ListenerMouseMainView lmmv = new ListenerMouseMainView(this);

	private JMenuBar mb = new JMenuBar();
	private JMenu menuDatei = new JMenu("Datei");
	private JMenu menuSoundboard = new JMenu("Soundboard");
	private JMenu menuLayer = new JMenu("Layer");
	private JMenu menuAnsicht = new JMenu("Ansicht");
	private JMenuItem itemPbAusblenden = new JMenuItem("Fortschrittsanzeige im Layer ein-/ausblenden");
	private JMenuItem itemIconBar = new JMenuItem("Layer Symbolleiste ein-/ausblenden");
	private JMenuItem itemHotButtonConfig = new JMenuItem("Hot-Button Konfig ein-/ausblenden");
	private JMenuItem itemFontSmall = new JMenuItem("Schriftgröße: Klein");
	private JMenuItem itemFontMedium = new JMenuItem("Schriftgröße: Mittel");
	private JMenuItem itemFontLarge = new JMenuItem("Schriftgröße: Groß");
	private JMenuItem itemAddSpalte = new JMenuItem("Spalte hinzufügen");
	private JMenuItem itemRemoveSpalte = new JMenuItem("Spalte entfernen");
	private JMenuItem itemAddZeile = new JMenuItem("Zeile hinzufügen");
	private JMenuItem itemRemoveZeile = new JMenuItem("Zeile entfernen");
	private JMenuItem itemSideView = new JMenuItem("Aktueller Layer im Sideview");
	private JMenuItem itemAddLayer = new JMenuItem("Neuen Layer hinzufügen");
	private JMenuItem itemRemoveLayer = new JMenuItem("Aktuellen Layer entfernen");
	private JMenuItem itemResetCounterCicle = new JMenuItem("Reset Soundbutton Zähler");
	private JMenuItem itemSaveLayer = new JMenuItem("Aktuellen Layer exportieren");
	private JMenuItem itemLoadLayer = new JMenuItem("Layer importieren");
	private JMenuItem itemSaveSoundboard = new JMenuItem("Soundboard speichern unter");
	private JMenuItem itemAutosave = new JMenuItem("Speichern in Autosave");
	private JMenuItem itemLoadSoundboard = new JMenuItem("Soundboard laden");
	private JMenuItem itemSettings = new JMenuItem("Einstellungen");

	private JPanel pnlEast = new JPanel(new BorderLayout());
	private JPanel pnlCenter = new JPanel(new BorderLayout());
	private JPanel pnlAnzeigeCenter = new JPanel();
	private JPanel iconBar;
	private PanelHotButton pnlHotButton;
	private JLabel lblTitel = new JLabel("Aktueller Titel");
	private File anzeigePfad;

	private Font actualFontSize = MyFonts.small;

	public boolean wasDragged = false;

	private JFXPanel myJFXPanel = new JFXPanel();
	private MediaPlayer tapeA;
	private MediaPlayer tapeB;

	private SoundButton sbActive;
	private SoundButton sbNext;
	private PanelListeSongsPlayed pnlSongsPlayed = new PanelListeSongsPlayed(this);

	private SoundButtonProperties sbpSource;
	private Stack<SbpChange> SbpChangeStack = new Stack<SbpChange>();

	private JTabbedPane tp = new JTabbedPane();
	private MainView hf;

	public MainView() {
		try {
			hf = this;
			SaveLoad.openConfig(hf);
			SaveLoad.openAutoSave(hf);
			addWindowListener(fl);
			setLayout(new BorderLayout());
			createMenuDatei();
			createMenuSoundboard();
			createMenuLayer();
			createMenuAnsicht();
			setJMenuBar(mb);
			sbVectorToTappedPane();

			pnlAnzeigeCenter.setLayout(new BorderLayout());

			lblTitel.setHorizontalAlignment(SwingConstants.CENTER);
			pnlAnzeigeCenter.add(lblTitel, BorderLayout.CENTER);
			pnlAnzeigeCenter.addMouseListener(lmmv);
			pnlAnzeigeCenter.addMouseMotionListener(lmmv);
			pnlCenter.add(pnlAnzeigeCenter, BorderLayout.SOUTH);
			add(pnlCenter, BorderLayout.CENTER);
			loadImageIcons();
			createIconBar();
			// pnlHotButton.setVisible(false);
			pnlEast.add(iconBar, BorderLayout.WEST);
			iconBar.setVisible(false);

			// add(pnlSongsPlayed, BorderLayout.EAST);

			setTitle("JMNext");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(Toolkit.getDefaultToolkit().getScreenSize());

			SaveLoad.loadConfig(hf, SaveLoad.getFileConfig());

			pnlHotButton = new PanelHotButton(hf, lmmv);
			pnlEast.add(pnlHotButton, BorderLayout.CENTER);
			add(pnlEast, BorderLayout.EAST);
			pnlHotButton.setVisible(false);

			setVisible(true);

			System.out.println(getZeitBlende());

			System.out.println("Auflösung: " + Toolkit.getDefaultToolkit().getScreenSize());
			System.out.println("dpi: " + Toolkit.getDefaultToolkit().getScreenResolution());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void sbVectorToTappedPane() {
		for (int i = 0; i < sbVector.size(); i++) {
			tp.addTab("Layer " + (i + 1), (SoundBoard) sbVector.get(i));
		}
		if (sbVector.size() > 0) {
			tp.setSelectedIndex(0);
			soundBoardActive = (SoundBoard) sbVector.get(0);
		}

		pnlCenter.add(tp, BorderLayout.CENTER);
		tp.addKeyListener(lkb);
		tp.addChangeListener(lc);
	}

	private void createMenuDatei() {
		itemLoadLayer.addActionListener(lmb);
		itemSaveLayer.addActionListener(lmb);
		itemLoadSoundboard.addActionListener(lmb);
		itemSaveSoundboard.addActionListener(lmb);
		itemAutosave.addActionListener(lmb);
		itemSettings.addActionListener(lmb);
		menuDatei.add(itemLoadLayer);
		menuDatei.add(itemSaveLayer);
		// menuDatei.add(new JSeparator());
		menuDatei.add(itemLoadSoundboard);
		menuDatei.add(itemSaveSoundboard);
		menuDatei.add(new JSeparator());
		menuDatei.add(itemAutosave);
		menuDatei.add(itemSettings);
		mb.add(menuDatei);
	}

	private void createMenuSoundboard() {
		itemAddLayer.addActionListener(lmb);
		itemRemoveLayer.addActionListener(lmb);
		itemResetCounterCicle.addActionListener(lmb);
		menuSoundboard.add(itemResetCounterCicle);
		menuSoundboard.add(new JSeparator());
		menuSoundboard.add(itemAddLayer);
		menuSoundboard.add(itemRemoveLayer);
		mb.add(menuSoundboard);
	}

	private void createMenuLayer() {
		itemAddSpalte.addActionListener(lmb);
		itemRemoveSpalte.addActionListener(lmb);
		itemAddZeile.addActionListener(lmb);
		itemRemoveZeile.addActionListener(lmb);
		menuLayer.add(itemAddSpalte);
		menuLayer.add(itemRemoveSpalte);
		menuLayer.add(new JSeparator());
		menuLayer.add(itemAddZeile);
		menuLayer.add(itemRemoveZeile);
		mb.add(menuLayer);
	}

	private void createMenuAnsicht() {
		itemPbAusblenden.addActionListener(lmb);
		itemIconBar.addActionListener(lmb);
		itemHotButtonConfig.addActionListener(lmb);
		itemSideView.addActionListener(lmb);
		itemFontSmall.addActionListener(lmb);
		itemFontMedium.addActionListener(lmb);
		itemFontLarge.addActionListener(lmb);
		menuAnsicht.add(itemPbAusblenden);
		menuAnsicht.add(itemIconBar);
		menuAnsicht.add(itemHotButtonConfig);
		menuAnsicht.add(itemSideView);
		menuAnsicht.add(itemFontSmall);
		menuAnsicht.add(itemFontMedium);
		menuAnsicht.add(itemFontLarge);
		mb.add(menuAnsicht);
	}

	private class FensterListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("closing");
			// soundBoardActive.saveSoundboard();
			SaveLoad.saveMainView(hf, SaveLoad.getFileAutoSave());
			SaveLoad.saveConfig(hf, SaveLoad.getFileConfig());
		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.out.println("closed");

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class ListenerKeyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("Keyevent");
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			if (e.getKeyCode() == 90) {
				soundBoardActive.undoChange();
			} else if (e.getKeyCode() == 32) {
				soundBoardActive.pausePlayer();
			} else if (e.getKeyCode() > 48 && e.getKeyCode() < 58) {
				pnlHotButton.pressedHotKeyStart(e.getKeyCode() - 48);
			} else if (e.getKeyCode() == 17 && keyStrgPressed == false) {
				keyStrgPressed = true;
			}
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Taste Losgelassen");
			keyStrgPressed = false;
		}
	}

	public void loadSoundBoardFile() {
		File f = Browse.getOpenFileSou();
		if (f != null) {
			if (f.exists()) {
				tp.removeKeyListener(lkb);
				tp.removeChangeListener(lc);
				tp.removeAll();
				sbVector.removeAllElements();
				SaveLoad.loadMainView(hf, f);
			}
		}
	}

	public void setSizeOfMainViewElements(Font myFont) {
		setActualFontSize(myFont);
		JMenu tempMenu;
		lblTitelSetPreferredSize(myFont);
		tp.setFont(myFont);
		mb.setFont(myFont);
		menuDatei.setFont(myFont);
		for (Component menu : mb.getComponents()) {
			menu.setFont(myFont);
			tempMenu = (JMenu) menu;
			for (Component item : tempMenu.getMenuComponents()) {
				item.setFont(myFont);
			}
		}
		SoundBoard temp;
		for (int i = 0; i < sbVector.size(); i++) {
			temp = (SoundBoard) sbVector.get(i);
			temp.setSizeOfButtonelements(myFont);
		}
		MyFonts.guiResizeFont(pnlHotButton.getComponents(), myFont);
		validate();
		repaint();
	}

	private void lblTitelSetPreferredSize(Font myFont) {
		lblTitel.setFont(myFont);
		System.out.println("Fontsize: " + myFont.getSize());
		if (myFont.getSize() == MyFonts.small.getSize()) {
			System.out.println("SMALL");
			pnlAnzeigeCenter.setPreferredSize(new Dimension(0, 40));
		} else if (myFont.getSize() == MyFonts.medium.getSize()) {
			System.out.println("MEDIUM");
			pnlAnzeigeCenter.setPreferredSize(new Dimension(0, 80));
		} else if (myFont.getSize() == MyFonts.large.getSize()) {
			System.out.println("LARGE");
			pnlAnzeigeCenter.setPreferredSize(new Dimension(0, 120));
		}
	}

	private void resetCountercicleAllSoundBoards() {
		SoundBoard temp;
		for (int i = 0; i < sbVector.size(); i++) {
			temp = (SoundBoard) sbVector.get(i);
			temp.resetCounterCicleAllSoundButtons();
		}
	}

	public class ListenerMenuBar implements ActionListener {

		MainView hf;

		public ListenerMenuBar(MainView hf) {
			this.hf = hf;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == itemPbAusblenden) {
				if (soundBoardActive.pbVisible == true) {
					soundBoardActive.pbAusblenden();
				} else {
					soundBoardActive.pbEinblenden();
				}
			} else if (e.getSource() == itemIconBar) {
				if (iconBar.isVisible() == true) {
					iconBar.setVisible(false);
				} else {
					iconBar.setVisible(true);
				}
			} else if (e.getSource() == itemHotButtonConfig) {
				if (pnlHotButton.isVisible() == true) {
					pnlHotButton.setVisible(false);
				} else {
					pnlHotButton.setVisible(true);
				}
			} else if (e.getSource() == itemAddSpalte || e.getSource() == btnAddSpalte) {
				System.out.println("Spalte hinzufügen");
				soundBoardActive.addSpalte();
			} else if (e.getSource() == itemRemoveSpalte || e.getSource() == btnRemoveSpalte) {
				System.out.println("Spalte entfernen");
				soundBoardActive.removeSpalte();
			} else if (e.getSource() == itemAddZeile || e.getSource() == btnAddZeile) {
				System.out.println("Zeile hinzufügen");
				soundBoardActive.addZeile();
			} else if (e.getSource() == itemRemoveZeile || e.getSource() == btnRemoveZeile) {
				System.out.println("Spalte entfernen");
				soundBoardActive.removeZeile();
			} else if (e.getSource() == itemSideView) {
				System.out.println("Sideview ertellen");
				if (sv == null) {
					sv = new SideView(hf, soundBoardActive, tp.getTitleAt(tp.getSelectedIndex()),
							tp.getSelectedIndex());
				}
			} else if (e.getSource() == itemSaveSoundboard) {
				SaveLoad.saveSoundBoardAs(hf);
			} else if (e.getSource() == itemFontSmall) {
				setSizeOfMainViewElements(MyFonts.small);
				actualFontSize = MyFonts.small;
			} else if (e.getSource() == itemFontMedium) {
				setSizeOfMainViewElements(MyFonts.medium);
				actualFontSize = MyFonts.medium;
			} else if (e.getSource() == itemFontLarge) {
				setSizeOfMainViewElements(MyFonts.large);
				actualFontSize = MyFonts.large;
				// Browse.getMusicFileFX();
			} else if (e.getSource() == itemLoadSoundboard) {
				loadSoundBoardFile();
				sbVectorToTappedPane();
			} else if (e.getSource() == itemSettings) {
				new DialogSettings(hf, getActualFontSize());
			} else if (e.getSource() == itemAddLayer) {
				System.out.println("Neuen Layer hinzufügen");
				sbVector.add(new SoundBoard(hf, 8, 6, lmmv));
				tp.add("Layer " + String.valueOf(sbVector.size()), (SoundBoard) sbVector.get(sbVector.size() - 1));
				if (sv == null) {
					tp.setSelectedIndex(sbVector.size() - 1);
				} else if (sv != null) {
					tp.setSelectedIndex(sbVector.size() - 2);
				}
				soundBoardActive = (SoundBoard) sbVector.get(sbVector.size() - 1);
				MyFonts.guiResizeFont(soundBoardActive.getComponents(), getActualFontSize());
			} else if (e.getSource() == itemAutosave) {
				SaveLoad.saveMainView(hf, SaveLoad.getFileAutoSave());
			} else if (e.getSource() == itemSaveLayer) {
				ExportLayer.save(soundBoardActive, Browse.getFolder());
			} else if (e.getSource() == itemLoadLayer) {
				ExportLayer.load(hf, Browse.getOpenFileLay());
			} else if (e.getSource() == itemResetCounterCicle) {
				resetCountercicleAllSoundBoards();
			} else if (e.getSource() == itemRemoveLayer) {
				System.out.println("Aktuellen Layer entfernen");
				if (sbVector.size() > 0) {
					sbVector.remove(tp.getSelectedIndex());
					tp.removeTabAt(tp.getSelectedIndex());
					if (sbVector.size() > 0) {
						tp.setSelectedIndex(0);
					}
				}
			}
		}
	}

	public class ListenerChange implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == tp && tp.getComponentCount() > 1) {
				try {
					System.out.println("Selected Index: " + tp.getSelectedIndex());
					soundBoardActive = (SoundBoard) sbVector.get(tp.getSelectedIndex());
					System.out.println(
							"soundBoardActive: " + tp.getSelectedIndex() + " " + sbVector.indexOf(soundBoardActive));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.out.println(
							"sbVectorSize: " + sbVector.size() + " tpgetselectedIndex: " + tp.getSelectedIndex());
					System.out.println("Fehler in Methode: MainView.ListenerChange");
				}
			}
		}

	}

	public void loadImageIcons() {
		iconAddZeile = new ImageIcon(getClass().getClassLoader().getResource("resources/addRow.png"));
		iconRemoveZeile = new ImageIcon(getClass().getClassLoader().getResource("resources/removeRow.png"));
		iconAddSpalte = new ImageIcon(getClass().getClassLoader().getResource("resources/addColumn.png"));
		iconRemoveSpalte = new ImageIcon(getClass().getClassLoader().getResource("resources/removeColumn.png"));
	}

	public JPanel createIconBar() {
		int x32 = 64;
		iconBar = new JPanel();
		iconBar.setLayout(new FlowLayout());
		iconAddZeileScale = new ImageIcon(iconAddZeile.getImage().getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnAddZeile = new JButton(iconAddZeileScale);
		btnAddZeile.setPreferredSize(new Dimension(x32, x32));
		btnAddZeile.setToolTipText("Zeile hinzufügen");
		btnAddZeile.addActionListener(lmb);
		iconBar.add(btnAddZeile);

		iconRemoveZeileScale = new ImageIcon(
				iconRemoveZeile.getImage().getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnRemoveZeile = new JButton(iconRemoveZeileScale);
		btnRemoveZeile.setPreferredSize(new Dimension(x32, x32));
		btnRemoveZeile.setToolTipText("Zeile entfernen");
		btnRemoveZeile.addActionListener(lmb);
		iconBar.add(btnRemoveZeile);

		iconAddSpalteScale = new ImageIcon(iconAddSpalte.getImage().getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnAddSpalte = new JButton(iconAddSpalteScale);
		btnAddSpalte.setPreferredSize(new Dimension(x32, x32));
		btnAddSpalte.setToolTipText("Spalte hinzufügen");
		btnAddSpalte.addActionListener(lmb);
		iconBar.add(btnAddSpalte);

		iconRemoveSpalteScale = new ImageIcon(
				iconRemoveSpalte.getImage().getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnRemoveSpalte = new JButton(iconRemoveSpalteScale);
		btnRemoveSpalte.setPreferredSize(new Dimension(x32, x32));
		btnRemoveSpalte.setToolTipText("Spalte entfernen");
		btnRemoveSpalte.addActionListener(lmb);
		iconBar.add(btnRemoveSpalte);
		iconBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		return iconBar;

	}

	public MediaPlayer getTapeA() {
		return tapeA;
	}

	public void setTapeA(MediaPlayer tapeA) {
		this.tapeA = tapeA;
	}

	public void setAnzeigePfad(File musicPath) {
		anzeigePfad = musicPath;
	}

	public void setTitelAnzeige(String titel) {
		lblTitel.setText(titel);
	}

	public SoundButton getSbActive() {
		return sbActive;
	}

	public void setSbActive(SoundButton sb) {
		sbActive = sb;
	}

	public SoundButton getSbNext() {
		return sbNext;
	}

	public void setSbNext(SoundButton sb) {
		sbNext = sb;
	}

	public SoundButtonProperties getSbpSource() {
		return sbpSource;
	}

	public void setSbpSource(SoundButtonProperties sbpSource) {
		this.sbpSource = sbpSource;
	}

	public Stack<SbpChange> getSbpChangeStack() {
		return SbpChangeStack;
	}

	public SoundBoard getSoundBoardActive() {
		return soundBoardActive;
	}

	public JTabbedPane getTp() {
		return tp;
	}

	public void setSoundBoardActive(SoundBoard soundBoardActive) {
		this.soundBoardActive = soundBoardActive;
	}

	public Font getActualFontSize() {
		return actualFontSize;
	}

	public void setActualFontSize(Font actualFontSize) {
		this.actualFontSize = actualFontSize;
	}

	public void addSongsPlayed(String filePath) {
		pnlSongsPlayed.addSongsPlayed(filePath);
	}

	public int getZeitBlende() {
		return zeitBlende;
	}

	public void setZeitBlende(int millisekunden) {
		zeitBlende = millisekunden / 10;
	}

	public void setSideViewNull() {
		sv = null;
	}

	public Vector<SoundBoard> getSbVector() {
		return sbVector;
	}

	public void setSbVector(Vector<SoundBoard> sbVector) {
		this.sbVector = sbVector;
	}

	public ListenerMouseMainView getLmmv() {
		return lmmv;
	}

	public boolean getKeyStrgPressed() {
		return keyStrgPressed;
	}

	public File getAnzeigePfad() {
		return anzeigePfad;
	}

	public JPanel getPnlAnzeige() {
		return pnlAnzeigeCenter;
	}
}
