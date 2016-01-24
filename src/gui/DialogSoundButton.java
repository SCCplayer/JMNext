package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lib.Browse;
import lib.MyFonts;

public class DialogSoundButton extends JDialog {
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagConstraints l = new GridBagConstraints();
	private GridBagConstraints r = new GridBagConstraints();
	private JLabel lbName = new JLabel("Button Name:");
	private JLabel lblButtonArt = new JLabel("Button Art: ");
	private JTextField tfName;
	private JTextField tfPath;
	private String[] sbArten = { "Art auswählen", "One Song", "Shuffle",
			"Loop" };
	private JComboBox cbSbArten = new JComboBox(sbArten);
	private JPanel pnlDialog = new JPanel();
	private JButton btnAbbrechen = new JButton("Abbrechen");
	private JPanel pnlSouth = new JPanel();
	private JPanel pnlPathAuswahl = new JPanel();
	private JPanel pnlButtonArt = new JPanel(new GridBagLayout());
	private JPanel pnlButtonArtLeft = new JPanel(new FlowLayout());
	private JPanel pnlButtonArtRight = new JPanel();
	private JButton btnLeeren = new JButton("Leeren");
	private JButton btnAnwenden = new JButton("Anwenden");
	private SoundButton soundButton;
	private JButton btnBrowse = new JButton("Durchsuchen");
	private JButton btnButtonFarbe = new JButton("Buttonfarbe auswählen");
	private JButton btnTextFarbe = new JButton("Textfarbe auswählen");
	private JLabel lbFarbe = new JLabel("Farbe: ");
	private btnListener bl = new btnListener();
	private File musicPath;
	private File[] musicFileArray;
	private JSlider volumeRegler;
	private JCheckBox cbBlende = new JCheckBox("Kreuzblende aktivieren");
	private JCheckBox cbEndlos = new JCheckBox(
			"Automatisch nächsten Song starten");
	private JCheckBox cbOwnPlayer = new JCheckBox(
			"Andere Player nicht ausblenden");
	private JCheckBox dedicatePlayer;
	private JLabel iconShuffle;
	private JLabel iconRepeat;
	private JLabel iconLoop;

	private JCheckBox chBox = new JCheckBox("shuffle");

	public DialogSoundButton(SoundButton sb)

	{
		this.soundButton = sb;
		initialComponents();
		setVisible(true);
	}

	public DialogSoundButton(SoundButton sb, Font myFont) {
		this.soundButton = sb;
		initialComponents();
		MyFonts.guiResizeFont(getComponents(), myFont);
		setVisible(true);
	}

	private void initialComponents() {
		System.out.println(soundButton.getButtonArt());
		iconLoop = new JLabel(soundButton.iconLoop);
		iconShuffle = new JLabel(soundButton.iconShuffle);
		iconRepeat = new JLabel(soundButton.iconMultiSong);
		musicPath = soundButton.getMusicPath();
		if (soundButton.getButtonArt() == 0) {
			System.out.println("Soundbutton");
		} else if (soundButton.getButtonArt() == 1) {
			System.out.println("Shufflebutton");
		}
		pnlDialog.setLayout(gbl);
		pnlDialog.setBorder(
				javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

		initConstraints();
		// ========= Eingabe Name ===============================
		pnlDialog.add(lbName, l);

		tfName = new JTextField(soundButton.getName());
		tfName.addActionListener(bl);
		pnlDialog.add(tfName, r);

		// ========== Farbenauswahl Hintergrund ======================
		nextRow(5, 5);
		pnlDialog.add(lbFarbe, l);
		btnButtonFarbe.addActionListener(bl);
		btnButtonFarbe.setBackground(soundButton.getBackground());
		btnButtonFarbe.setForeground(soundButton.getForegroundColorStandard());
		pnlDialog.add(btnButtonFarbe, r);

		// ========== Farbenauswahl Text =============================
		nextRow();
		pnlDialog.add(new JLabel("Textfarbe: "), l);
		btnTextFarbe.addActionListener(bl);
		btnTextFarbe.setBackground(soundButton.getBackground());
		btnTextFarbe.setForeground(soundButton.getForegroundColorStandard());
		pnlDialog.add(btnTextFarbe, r);

		// ========== Pfadauswahl =============================
		nextRow();
		pnlDialog.add(new JLabel("Pfad: "), l);

		if (soundButton.getMusicPath() == null) {
			tfPath = new JTextField();
		} else {
			tfPath = new JTextField(soundButton.getMusicPathString());
		}

		createPanelAuswahl();
		pnlDialog.add(pnlPathAuswahl, r);

		// ========== Button Art =============================
		nextRow();
		lblButtonArt.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDialog.add(lblButtonArt, l);
		// createPanelButtonArt();
		pnlButtonArtRight.add(chBox);
		pnlDialog.add(pnlButtonArtRight, r);

		if (soundButton.getButtonArt() == 0) {
			lblButtonArt.setVisible(false);
			pnlButtonArtRight.setVisible(false);
		} else if (soundButton.getButtonArt() == 1) {
			lblButtonArt.setVisible(true);
			pnlButtonArtRight.setVisible(true);
			chBox.setSelected(true);
		} else if (soundButton.getButtonArt() == 2) {
			lblButtonArt.setVisible(true);
			pnlButtonArtRight.setVisible(true);
		}
		// ========== Volume =============================
		nextRow();
		pnlDialog.add(new JLabel("Lautstärke:"), l);

		volumeRegler = new JSlider(0, 100);
		volumeRegler.setMajorTickSpacing(20);
		volumeRegler.setMinorTickSpacing(1);
		volumeRegler.setPaintLabels(true);
		volumeRegler.setPaintTicks(true);
		volumeRegler.setValue((int) (soundButton.getVolume() * 100));
		pnlDialog.add(volumeRegler, r);

		pnlSouth.setLayout(new FlowLayout());
		btnAbbrechen.addActionListener(bl);
		btnAnwenden.addActionListener(bl);
		btnLeeren.addActionListener(bl);
		pnlSouth.add(btnAbbrechen);
		pnlSouth.add(btnLeeren);
		pnlSouth.add(btnAnwenden);
		pnlDialog.setPreferredSize(new Dimension(1, 1));
		setSize(1500, 500);
		setLocation(100, 100);
		setLayout(new BorderLayout());
		add(pnlDialog, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

	}

	public class btnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAbbrechen) {
				dispose();
			} else if (e.getSource() == btnAnwenden) {
				dialogAnwenden();
			} else if (e.getSource() == btnButtonFarbe) {
				Color choosedButtonFarbe = JColorChooser.showDialog(null,
						"Farbauswahl", btnButtonFarbe.getBackground());
				btnButtonFarbe.setBackground(choosedButtonFarbe);
				btnTextFarbe.setBackground(choosedButtonFarbe);

			} else if (e.getSource() == btnTextFarbe) {
				Color choosedTextFarbe = JColorChooser.showDialog(null,
						"Farbauswahl", btnButtonFarbe.getBackground());
				btnTextFarbe.setForeground(choosedTextFarbe);
				btnButtonFarbe.setForeground(choosedTextFarbe);

			} else if (e.getSource() == tfName) {
				dialogAnwenden();
			} else if (e.getSource() == btnLeeren) {
				tfPath.setText("");
				dialogAnwenden();
			} else if (e.getSource() == btnBrowse) {
				// musicPath = null;
				musicPath = Browse.getMusicFileFolder();
				if (musicPath != null) {
					tfPath.setText(musicPath.getPath());
					if (soundButton.getName()
							.compareTo(tfName.getText()) == 0) {
						tfName.setText(musicPath.getName());
					}

				}
			}

		}
	}

	public void createPanelAuswahl() {
		pnlPathAuswahl.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 10;
		c.ipady = 12;
		c.gridy = 0;
		c.gridx = 0;
		pnlPathAuswahl.add(tfPath, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 0;
		c.gridx = 1;
		btnBrowse.addActionListener(bl);
		pnlPathAuswahl.add(btnBrowse, c);
	}

	private void nextRow() {
		l.gridy++;
		r.gridy++;
	}

	private void nextRow(int vertikalPlatz, int horizontalPlatz) {
		r.ipadx = horizontalPlatz; // zusätzlicher Platz rechts
		r.ipady = vertikalPlatz; // zusätzlicher Platz oben unten
		l.gridy++;
		r.gridy++;
	}

	private void initConstraints() {
		l.fill = GridBagConstraints.HORIZONTAL;
		l.weightx = 0;
		l.weighty = 0;
		l.ipadx = 20;
		l.ipady = 20;
		l.gridy = 0;
		l.gridx = 0;

		r.fill = GridBagConstraints.HORIZONTAL;
		r.weightx = 0.1;
		r.weighty = 0;
		r.ipadx = 0;
		r.ipady = 12;
		r.gridy = 0;
		r.gridx = 1;
	}

	private void dialogAnwenden() {
		// System.out.println(tfPath.getText().length());
		if (tfPath.getText().length() == 0) {
			musicPath = null;
		}
		if (musicPath != null) {
			if (musicPath.listFiles() == null) {
				soundButton.setProperties(0, tfName.getText(), musicPath,
						(double) volumeRegler.getValue() / 100, "0:00",
						btnButtonFarbe.getForeground(),
						btnButtonFarbe.getBackground());
			} else if (musicPath.listFiles() != null) {
				if (chBox.isSelected() == true) {
					soundButton
							.setProperties(1, tfName.getText(), musicPath,
									(double) volumeRegler.getValue() / 100,
									String.valueOf(Browse.getMusicFileArray(
											musicPath).length),
							btnButtonFarbe.getForeground(),
							btnButtonFarbe.getBackground());
				} else if (chBox.isSelected() == false) {
					System.out.println("multisong");
					soundButton
							.setProperties(2, tfName.getText(), musicPath,
									(double) volumeRegler.getValue() / 100,
									String.valueOf(Browse.getMusicFileArray(
											musicPath).length),
							btnButtonFarbe.getForeground(),
							btnButtonFarbe.getBackground());
				}
			}
		} else {
			soundButton.setProperties(99, tfName.getText(), null,
					volumeRegler.getValue(), "0:00",
					btnButtonFarbe.getForeground(),
					btnButtonFarbe.getBackground());
		}
		dispose();
	}
}