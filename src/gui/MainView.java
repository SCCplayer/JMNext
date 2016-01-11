package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;
import java.util.Vector;

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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.SbpChange;
import data.SoundButtonProperties;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import lib.Browse;
import lib.MyFonts;

public class MainView extends JFrame {
	private SideView sv;
	private ListenerKeyboard lkb = new ListenerKeyboard();
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private ListenerMenuBar lmb = new ListenerMenuBar(this);
	private ListenerChange lc = new ListenerChange();
	private Vector sbVector = new Vector();

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
	private int zeilen = 0;
	private int spalten = 0;

	private int zeitBlende = 200; // 100 entspricht 1 sec

	private FensterListener fl = new FensterListener();
	private JMenuBar mb = new JMenuBar();
	private JMenu menuDatei = new JMenu("Datei");
	private JMenu menuSoundboard = new JMenu("Soundboard");
	private JMenu menuLayer = new JMenu("Layer");
	private JMenu menuAnsicht = new JMenu("Ansicht");
	private JMenuItem itemPbAusblenden = new JMenuItem(
			"Vortschrittsanzeige im Layer ein-/ausblenden");
	private JMenuItem itemIconBar = new JMenuItem(
			"Symbolleiste ein-/ausblenden");
	private JMenuItem itemFontSmall = new JMenuItem("Schriftgröße: Klein");
	private JMenuItem itemFontMedium = new JMenuItem("Schriftgröße: Mittel");
	private JMenuItem itemFontLarge = new JMenuItem("Schriftgröße: Groß");
	private JMenuItem itemAddSpalte = new JMenuItem("Spalte hinzufügen");
	private JMenuItem itemRemoveSpalte = new JMenuItem("Spalte entfernen");
	private JMenuItem itemAddZeile = new JMenuItem("Zeile hinzufügen");
	private JMenuItem itemRemoveZeile = new JMenuItem("Zeile entfernen");
	private JMenuItem itemSideView = new JMenuItem(
			"Aktueller Layer im Sideview");
	private JMenuItem itemAddLayer = new JMenuItem("Neuen Layer hinzufügen");
	private JMenuItem itemRemoveLayer = new JMenuItem(
			"Aktuellen Layer entfernen");
	private JMenuItem itemSaveLayer = new JMenuItem(
			"Aktuellen Layer speichern");
	private JMenuItem itemLoadLayer = new JMenuItem("Layer laden");
	private JMenuItem itemSaveSoundboard = new JMenuItem(
			"Soundboard speichern unter");
	private JMenuItem itemAutosave = new JMenuItem("Speichern in Autosave");
	private JMenuItem itemLoadSoundboard = new JMenuItem("Soundboard laden");
	private JMenuItem itemSettings = new JMenuItem("Einstellungen");

	private GridLayout gdl = new GridLayout(1, 3);
	private JPanel pnlAnzeigeLeft = new JPanel();
	private JPanel pnlAnzeigeCenter = new JPanel();
	private JPanel pnlAnzeigeRight = new JPanel();
	private JPanel pnlAnzeige = new JPanel(gdl);
	private JPanel iconBar;
	private JLabel lblTitel = new JLabel("Aktueller Titel \u23F8");
	private File anzeigePfad;
	private Font actualFontSize;

	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);
	public boolean wasDragged = false;
	private SoundButton soundButton;

	private JFXPanel myJFXPanel = new JFXPanel();
	private MediaPlayer tapeA;
	private MediaPlayer tapeB;

	private SoundButton sbActive;
	private SoundButton sbNext;

	private SoundButtonProperties sbpSource;
	private Stack<SbpChange> SbpChangeStack = new Stack<SbpChange>();

	private File fileAutoSave;

	private JTabbedPane tp = new JTabbedPane();
	private MainView hf;

	public MainView() {
		try {
			hf = this;
			openAutoSave();
			addWindowListener(fl);
			setLayout(new BorderLayout());
			createMenuDatei();
			createMenuSoundboard();
			createMenuLayer();
			createMenuAnsicht();
			setJMenuBar(mb);
			sbVectorToTappedPane();

			pnlAnzeigeLeft.add(new PanelFarbpalette());
			pnlAnzeige.add(pnlAnzeigeLeft);
			pnlAnzeigeCenter.setLayout(new BorderLayout());
			lblTitel.setPreferredSize(new Dimension(500, 120));
			lblTitel.setHorizontalAlignment(SwingConstants.CENTER);
			pnlAnzeigeCenter.add(lblTitel, BorderLayout.CENTER);
			pnlAnzeige.add(pnlAnzeigeCenter);
			pnlAnzeigeRight.add(new PanelFarbpalette());
			pnlAnzeige.add(pnlAnzeigeRight);
			pnlAnzeige.addMouseListener(lmk);
			pnlAnzeige.addMouseMotionListener(lmk);
			pnlAnzeigeRight.setVisible(false);
			pnlAnzeigeLeft.setVisible(false);
			add(pnlAnzeige, BorderLayout.SOUTH);
			loadImageIcons();
			createIconBar();
			add(iconBar, BorderLayout.EAST);
			iconBar.setVisible(false);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(2000, 1400);
			System.out.println(sbVector.size());
			setSizeOfMainViewElements(MyFonts.large);
			actualFontSize = MyFonts.large;
			setVisible(true);
			System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
			System.out
					.println(Toolkit.getDefaultToolkit().getScreenResolution());
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

		add(tp, BorderLayout.CENTER);
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
		// menuDatei.add(itemLoadLayer);
		// menuDatei.add(itemSaveLayer);
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
		itemSideView.addActionListener(lmb);
		itemFontSmall.addActionListener(lmb);
		itemFontMedium.addActionListener(lmb);
		itemFontLarge.addActionListener(lmb);
		menuAnsicht.add(itemPbAusblenden);
		menuAnsicht.add(itemIconBar);
		menuAnsicht.add(itemSideView);
		menuAnsicht.add(itemFontSmall);
		menuAnsicht.add(itemFontMedium);
		menuAnsicht.add(itemFontLarge);
		mb.add(menuAnsicht);
	}

	private void openAutoSave() {
		if (getClass().getClassLoader().getResource("resources").toString()
				.split(":")[0].compareTo("file") == 0) {
			if (getClass().getClassLoader().getResource("resources").toString()
					.split(":").length == 2) {
				fileAutoSave = new File(getClass().getClassLoader()
						.getResource("resources").toString().split(":")[1]
								.concat("/autosave.ser"));
				System.out.println(fileAutoSave.getAbsolutePath());
			} else if (getClass().getClassLoader().getResource("resources")
					.toString().split(":").length == 3) {
				fileAutoSave = new File(getClass().getClassLoader()
						.getResource("resources").toString().split(":")[2]
								.concat("/autosave.ser"));
			}
			System.out.println(fileAutoSave.getAbsolutePath());
		} else {
			fileAutoSave = new File("autosave.ser");
		}
		if (fileAutoSave.exists() == false) {
			try {
				fileAutoSave.createNewFile();
			} catch (Exception e) {
				System.out.println("Datei erstellen fehlgeschlagen");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Daten werden aus AutoSave geladen");
			loadMainView(fileAutoSave);
		}
	}

	public void saveMainView(File speicherFile) {
		try {
			FileOutputStream fileStream = new FileOutputStream(speicherFile);
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeInt(sbVector.size());
			for (int iv = 0; iv < sbVector.size(); iv++) {
				SoundBoard sbSave = (SoundBoard) sbVector.get(iv);
				os.writeInt(sbSave.getZeilen());
				os.writeInt(sbSave.getSpalten());
				os.writeBoolean(sbSave.pbVisible);
				for (int z = 0; z < sbSave.getZeilen(); z++) {
					for (int sp = 0; sp < sbSave.getSpalten(); sp++) {
						os.writeObject(
								sbSave.getSbArray()[z][sp].getProperties());
						System.out.println("Zeile: " + z + " Spalte: " + sp
								+ " gespeichert");
					}
				}
				System.out.println("Layer " + iv + " wurde gespeichert");
			}
			os.close();
		} catch (Exception ex) {
			System.out.println(
					"Objekte konnten nicht vollständig gespeichert werden");
			System.out.println(ex.getMessage());
		}
	}

	public void loadMainView(File ladenFile) {
		try {
			SoundButtonProperties[][] sbpArray;
			FileInputStream fileStream = new FileInputStream(ladenFile);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				int anzahlLayer = os.readInt();
				boolean pbVisible = true;
				System.out.println("Anzahl Layer: " + anzahlLayer);
				for (int i = 0; i < anzahlLayer; i++) {
					zeilen = os.readInt();
					spalten = os.readInt();
					pbVisible = os.readBoolean();
					sbpArray = new SoundButtonProperties[zeilen][spalten];
					for (int z = 0; z < zeilen; z++) {
						for (int sp = 0; sp < spalten; sp++) {
							sbpArray[z][sp] = (SoundButtonProperties) os
									.readObject();
						}
					}
					sbVector.add(new SoundBoard(this, sbpArray, pbVisible));
					soundBoardActive = (SoundBoard) sbVector.get(i);
				}

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

	private class FensterListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("closing");
			// soundBoardActive.saveSoundboard();
			saveMainView(fileAutoSave);
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
			} else if (e.getKeyCode() == 49) {
				tp.setSelectedIndex(0);
			} else if (e.getKeyCode() == 50) {
				tp.setSelectedIndex(1);
			}
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

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
				loadMainView(f);
			}
		}
	}

	public void saveSoundBoardAs() {
		System.out.println("Button speichern");
		File f = Browse.getSaveFileSou();
		System.out.println(f);
		if (f != null) {
			if (f.exists() == true) {
				System.out.println("Datei existiert bereits");
			} else {
				try {
					f.createNewFile();
					saveMainView(f);
				} catch (Exception ex) {
					System.out.println("Datei erstellen fehlgeschlagen");
				}
			}
		}
	}

	public void setSizeOfMainViewElements(Font myFont) {
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
		validate();
		repaint();
	}

	private void lblTitelSetPreferredSize(Font myFont) {
		lblTitel.setFont(myFont);
		if (myFont == MyFonts.small) {
			lblTitel.setPreferredSize(new Dimension(500, 40));
		} else if (myFont == MyFonts.medium) {
			lblTitel.setPreferredSize(new Dimension(500, 80));
		} else if (myFont == MyFonts.large) {
			lblTitel.setPreferredSize(new Dimension(500, 120));
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
			} else if (e.getSource() == itemAddSpalte
					|| e.getSource() == btnAddSpalte) {
				System.out.println("Spalte hinzufügen");
				soundBoardActive.addSpalte();
			} else if (e.getSource() == itemRemoveSpalte
					|| e.getSource() == btnRemoveSpalte) {
				System.out.println("Spalte entfernen");
				soundBoardActive.removeSpalte();
			} else if (e.getSource() == itemAddZeile
					|| e.getSource() == btnAddZeile) {
				System.out.println("Zeile hinzufügen");
				soundBoardActive.addZeile();
			} else if (e.getSource() == itemRemoveZeile
					|| e.getSource() == btnRemoveZeile) {
				System.out.println("Spalte entfernen");
				soundBoardActive.removeZeile();
			} else if (e.getSource() == itemSideView) {
				System.out.println("Sideview ertellen");
				if (sv == null) {
					sv = new SideView(hf, soundBoardActive,
							tp.getTitleAt(tp.getSelectedIndex()),
							tp.getSelectedIndex());
				}
			} else if (e.getSource() == itemSaveSoundboard) {
				saveSoundBoardAs();
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
				new ViewSettings(hf, getActualFontSize());
			} else if (e.getSource() == itemAddLayer) {
				System.out.println("Neuen Layer hinzufügen");
				sbVector.add(new SoundBoard(hf, 8, 6));
				tp.add("Layer " + String.valueOf(sbVector.size()),
						(SoundBoard) sbVector.get(sbVector.size() - 1));
				if (sv == null) {
					tp.setSelectedIndex(sbVector.size() - 1);
				} else if (sv != null) {
					tp.setSelectedIndex(sbVector.size() - 2);
				}
				soundBoardActive = (SoundBoard) sbVector
						.get(sbVector.size() - 1);
			} else if (e.getSource() == itemAutosave) {
				saveMainView(fileAutoSave);
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

	private class ListenerMouseKlick
			implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (e.getSource() == pnlAnzeige) {
					System.out.println(anzeigePfad);
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (soundBoardActive.getMousePosition() != null) {
				if (soundBoardActive.getComponentAt(soundBoardActive
						.getMousePosition()) instanceof SoundButton) {
					soundButton = (SoundButton) soundBoardActive.getComponentAt(
							soundBoardActive.getMousePosition());
					if (soundButton.getProperties().getButtonArt() == 99
							&& anzeigePfad != null) {
						System.out.println("Buttonart = 99");
						soundBoardActive.setCursor(DragSource.DefaultCopyDrop);
					} else {
						soundBoardActive.setCursor(cursorHand);
					}
				}
			} else {
				setCursor(cursorHand);
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			setCursor(cursorHand);

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (soundButton != null && anzeigePfad != null) {
				System.out.println(soundButton + anzeigePfad.getName());
				if (soundButton.getProperties().getButtonArt() == 99) {
					soundButton.getProperties().setMusicPath(anzeigePfad);
					soundButton.setName(anzeigePfad.getName());
					soundButton.getProperties().setButtonArt(0);
					soundButton = null;
				}
			}
			soundBoardActive.setCursor(cursorMove);
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
	}

	public class ListenerChange implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == tp && tp.getComponentCount() > 1) {
				try {
					System.out.println(
							"Selected Index: " + tp.getSelectedIndex());
					soundBoardActive = (SoundBoard) sbVector
							.get(tp.getSelectedIndex());
					System.out.println(
							"soundBoardActive: " + tp.getSelectedIndex() + " "
									+ sbVector.indexOf(soundBoardActive));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.out.println("sbVectorSize: " + sbVector.size()
							+ " tpgetselectedIndex: " + tp.getSelectedIndex());
					System.out.println(
							"Fehler in Methode: MainView.ListenerChange");
				}
			}
		}

	}

	public void loadImageIcons() {
		iconAddZeile = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/addRow.png"));
		iconRemoveZeile = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/removeRow.png"));
		iconAddSpalte = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/addColumn.png"));
		iconRemoveSpalte = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/removeColumn.png"));
	}

	public JPanel createIconBar() {
		int x32 = 64;
		iconBar = new JPanel();
		iconBar.setLayout(new FlowLayout());
		iconAddZeileScale = new ImageIcon(iconAddZeile.getImage()
				.getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnAddZeile = new JButton(iconAddZeileScale);
		btnAddZeile.setPreferredSize(new Dimension(x32, x32));
		btnAddZeile.setToolTipText("Zeile hinzufügen");
		btnAddZeile.addActionListener(lmb);
		iconBar.add(btnAddZeile);

		iconRemoveZeileScale = new ImageIcon(iconRemoveZeile.getImage()
				.getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnRemoveZeile = new JButton(iconRemoveZeileScale);
		btnRemoveZeile.setPreferredSize(new Dimension(x32, x32));
		btnRemoveZeile.setToolTipText("Zeile entfernen");
		btnRemoveZeile.addActionListener(lmb);
		iconBar.add(btnRemoveZeile);

		iconAddSpalteScale = new ImageIcon(iconAddSpalte.getImage()
				.getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnAddSpalte = new JButton(iconAddSpalteScale);
		btnAddSpalte.setPreferredSize(new Dimension(x32, x32));
		btnAddSpalte.setToolTipText("Spalte hinzufügen");
		btnAddSpalte.addActionListener(lmb);
		iconBar.add(btnAddSpalte);

		iconRemoveSpalteScale = new ImageIcon(iconRemoveSpalte.getImage()
				.getScaledInstance(x32, x32, Image.SCALE_DEFAULT));
		btnRemoveSpalte = new JButton(iconRemoveSpalteScale);
		btnRemoveSpalte.setPreferredSize(new Dimension(x32, x32));
		btnRemoveSpalte.setToolTipText("Spalte entfernen");
		btnRemoveSpalte.addActionListener(lmb);
		iconBar.add(btnRemoveSpalte);

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

	public File getFileAutoSave() {
		return fileAutoSave;
	}

	public void setFileAutoSave(File fileAutoSave) {
		this.fileAutoSave = fileAutoSave;
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

	public int getZeitBlende() {
		return zeitBlende;
	}

	public void setZeitBlende(int milisekunden) {
		zeitBlende = milisekunden / 10;
	}

	public void setSideViewNull() {
		sv = null;
	}
}
