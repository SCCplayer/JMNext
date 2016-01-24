package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import lib.MyFonts;

public class ViewSettings extends JFrame {

	private MainView hf;

	private JPanel pnlSouth = new JPanel();
	private JPanel pnlCenter = new JPanel();

	private JPanel pnlFadeOutZeit = new JPanel();

	private JSlider sliderFadeOutZeit;

	private JLabel lblFadeOutZeit = new JLabel("Fadeout: ");
	private JLabel lblFadoutZeitEinheit = new JLabel(" in Millisekunden");

	private JButton btnAbbrechen = new JButton("Abbrechen");
	private JButton btnAnwenden = new JButton("Anwenden");

	private BtnListener bl = new BtnListener();

	public ViewSettings(MainView hf) {
		this.hf = hf;
		init();
		setVisible(true);
	}

	public ViewSettings(MainView hf, Font myFont) {
		this.hf = hf;
		init();
		MyFonts.guiResizeFont(this.getComponents(), myFont);
		setVisible(true);
	}

	private void init() {
		setLayout(new BorderLayout());

		sliderFadeOutZeit = new JSlider(0, 3000);
		sliderFadeOutZeit.setMajorTickSpacing(200);
		sliderFadeOutZeit.setMinorTickSpacing(100);
		sliderFadeOutZeit.setPaintLabels(true);
		sliderFadeOutZeit.setPaintTicks(true);
		sliderFadeOutZeit.setValue(hf.getZeitBlende() * 10);
		sliderFadeOutZeit.setPreferredSize(new Dimension(1600, 200));

		pnlFadeOutZeit.setLayout(new FlowLayout());
		pnlFadeOutZeit.add(lblFadeOutZeit);
		pnlFadeOutZeit.add(sliderFadeOutZeit);
		pnlFadeOutZeit.add(lblFadoutZeitEinheit);

		pnlCenter.setLayout(new FlowLayout());
		pnlCenter.add(pnlFadeOutZeit);

		add(pnlCenter, BorderLayout.CENTER);

		pnlSouth.setLayout(new FlowLayout());
		btnAbbrechen.addActionListener(bl);
		btnAnwenden.addActionListener(bl);
		pnlSouth.add(btnAbbrechen);
		pnlSouth.add(btnAnwenden);
		add(pnlSouth, BorderLayout.SOUTH);

		setSize(2100, 300);
		setLocation(100, 100);
	}

	public class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == btnAbbrechen) {
				dispose();
			} else if (e.getSource() == btnAnwenden) {
				hf.setZeitBlende(sliderFadeOutZeit.getValue());
				dispose();
			}
		}

	}
}
