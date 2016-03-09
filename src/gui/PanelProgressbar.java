package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import lib.MyFonts;

public class PanelProgressbar extends JPanel {

	JFrame parent;
	JPanel me;

	private JPanel pnlContent = new JPanel(new BorderLayout());
	private JPanel pnlCenter = new JPanel(new BorderLayout());
	private JPanel pnlStatus = new JPanel(new BorderLayout());
	private JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

	private JProgressBar pb;
	private JLabel lblInfotext;
	private JLabel lblStatusItem;
	private JLabel lblVon;
	private JLabel lblBis;
	private int start = 0;
	private int counter = 0;
	private int max = 0;
	private JButton btnAbbrechen = new JButton("Abbrechen");
	private boolean prozessAbgebrochen = false;

	public PanelProgressbar(JFrame parent, String infotext, String statusItem, int start, int max, Font myFont) {
		this.parent = parent;
		this.start = start;
		this.counter = start;
		this.max = max;
		me = this;

		int screenResolution = getToolkit().getScreenResolution();
		double resolutionFaktor1 = screenResolution - 100;
		double resolutionFaktor2 = 0.3;
		int abstandRahmen = (int) (resolutionFaktor1 * resolutionFaktor2);
		System.out.println("Faktor1: " + resolutionFaktor1 + " Faktor2: " + resolutionFaktor2);
		System.out.println("Abstand: " + resolutionFaktor1 * resolutionFaktor2);

		setLayout(new BorderLayout());

		pnlContent.setBorder(BorderFactory.createLineBorder(Color.black));
		lblInfotext = new JLabel("<HTML><BODY>" + infotext + "</BODY></HTML>");
		lblInfotext.setBorder(BorderFactory.createEmptyBorder(0, 0, 20 + abstandRahmen, 0));
		lblStatusItem = new JLabel(statusItem + " " + start + " von " + max);

		lblStatusItem.setVerticalAlignment(SwingConstants.NORTH);
		lblStatusItem.setHorizontalAlignment(SwingConstants.RIGHT);

		lblVon = new JLabel(String.valueOf(0));
		lblBis = new JLabel(String.valueOf(max));
		pb = new JProgressBar(start, max);
		pb.setStringPainted(true);
		pnlCenter.add(lblInfotext, BorderLayout.NORTH);
		pnlCenter.add(pb, BorderLayout.CENTER);
		pnlCenter.add(lblStatusItem, BorderLayout.SOUTH);
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(20 + abstandRahmen, 20 + abstandRahmen, 20 + abstandRahmen,
				20 + abstandRahmen));
		pnlContent.add(pnlCenter, BorderLayout.CENTER);

		btnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnAbbrechen.getText().compareTo("OK") == 0) {
					parent.dispose();
				} else if (btnAbbrechen.getText().compareTo("Abbrechen") == 0) {
					prozessAbgebrochen = true;
				}
			}
		});

		pnlButton.add(btnAbbrechen);
		pnlContent.add(pnlButton, BorderLayout.SOUTH);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 3,
				Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 4));
		add(pnlContent, BorderLayout.CENTER);
		MyFonts.guiResizeFont(getComponents(), myFont);
	}

	public boolean nextStep() {
		counter++;
		pb.setValue(counter);
		System.out.println("pbValue: " + pb.getValue());
		lblStatusItem.setText("Soundbutton" + " " + counter + " von " + max);
		validate();
		repaint();
		return true;
	}

	public boolean nextStep(int satz) {
		counter += satz;
		pb.setValue(counter);
		System.out.println("pbValue: " + pb.getValue());
		lblStatusItem = new JLabel("Soundbutton" + " " + counter + " von " + max);
		return true;
	}

	public void setMax(int max) {
		this.max = max;
		pb.setMaximum(max);
		lblStatusItem.setText("Soundbutton" + " " + 0 + " von " + max);
		validate();
		repaint();
	}

	public void setExportDone() {
		lblInfotext.setText(
				"<HTML><BODY>Alle Soundbutton-Eigenschaften und Musikdateien wurden exportiert.</BODY></HTML>");
		btnAbbrechen.setText("OK");
		validate();
		repaint();
	}

	public void setExportAbgebrochen() {
		pb.setVisible(false);
		lblStatusItem.setVisible(false);
		lblInfotext.setText("<HTML><BODY>Export wurde abgebrochen.</BODY></HTML>");
		btnAbbrechen.setText("OK");
		validate();
		repaint();
	}

	public void setImportDone() {
		lblInfotext.setText(
				"<HTML><BODY>Alle Soundbutton-Eigenschaften wurden importiert und die Dateipfade wurden aktualisiert.</BODY></HTML>");
		btnAbbrechen.setText("OK");
		validate();
		repaint();
	}

	public boolean isProzessCanceled() {
		return prozessAbgebrochen;
	}
}
