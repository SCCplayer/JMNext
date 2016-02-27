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
import javax.swing.JRootPane;
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
	private int max = 0;
	private JButton btnAbbrechen = new JButton("Abbrechen");

	public ProgressbarView(String bezeichnung, String infotext, String statusItem, int start, int max, Font myFont) {
		me = this;
		this.start = start;
		this.max = max;
		setLayout(new BorderLayout());

		pnlContent.setBorder(BorderFactory.createLineBorder(Color.black));
		lblInfotext = new JLabel(infotext);
		lblInfotext.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		lblStatusItem = new JLabel(statusItem + " " + start + " von " + max);

		lblStatusItem.setVerticalAlignment(SwingConstants.NORTH);
		lblStatusItem.setHorizontalAlignment(SwingConstants.RIGHT);

		lblVon = new JLabel(String.valueOf(0));
		lblBis = new JLabel(String.valueOf(max));
		pb = new JProgressBar(start, max);
		pnlCenter.add(lblInfotext, BorderLayout.NORTH);
		pnlCenter.add(pb, BorderLayout.CENTER);
		pnlCenter.add(lblStatusItem, BorderLayout.SOUTH);
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		pnlContent.add(pnlCenter, BorderLayout.CENTER);

		btnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				me.dispose();
			}
		});

		// setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);

		pnlButton.add(btnAbbrechen);
		pnlContent.add(pnlButton, BorderLayout.SOUTH);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 3,
				Toolkit.getDefaultToolkit().getScreenSize().height / 10 * 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(pnlContent, BorderLayout.CENTER);
		MyFonts.guiResizeFont(getComponents(), myFont);
		setVisible(true);
	}
}
