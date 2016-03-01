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

public class ProgressbarView extends JFrame {

	private JFrame me;

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

	public ProgressbarView(String infotext, String statusItem, int start, int max, Font myFont) {
		System.out.println("Progressbarview wird erzeugt: " + max + " Soundbutton werden exportiert");
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
		lblInfotext = new JLabel(infotext);
		lblInfotext.setBorder(BorderFactory.createEmptyBorder(0, 0, 20 + abstandRahmen, 0));
		lblStatusItem = new JLabel(statusItem + " " + start + " von " + max);

		lblStatusItem.setVerticalAlignment(SwingConstants.NORTH);
		lblStatusItem.setHorizontalAlignment(SwingConstants.RIGHT);

		lblVon = new JLabel(String.valueOf(0));
		lblBis = new JLabel(String.valueOf(max));
		pb = new JProgressBar(start, 100);
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
				me.dispose();
			}
		});

		pnlButton.add(btnAbbrechen);
		pnlContent.add(pnlButton, BorderLayout.SOUTH);
		// setUndecorated(true);
		// getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 3,
				Toolkit.getDefaultToolkit().getScreenSize().height / 10 * 2));
		setLocationRelativeTo(null);
		add(pnlContent, BorderLayout.CENTER);
		MyFonts.guiResizeFont(getComponents(), myFont);
		setVisible(true);
	}

	public void nextStep() {
		counter++;
		pb.setValue(counter);
		System.out.println("pbValue: " + pb.getValue());
		lblStatusItem = new JLabel("Soundbutton" + " " + counter + " von " + max);
		this.validate();
		this.repaint();
	}

	public void closeProgressbarView() {
		me.dispose();
	}
}
