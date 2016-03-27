package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class HotButtonView extends JFrame {
	private MainView hf;
	private ListenerMenuBar lmb = new ListenerMenuBar();
	private SoundBoard sbHotButton;

	private JMenuBar mb = new JMenuBar();
	private JMenu menuLayer = new JMenu("Layer");

	private JMenuItem itemAddSpalte = new JMenuItem("Spalte hinzufügen");
	private JMenuItem itemRemoveSpalte = new JMenuItem("Spalte entfernen");
	private JMenuItem itemAddZeile = new JMenuItem("Zeile hinzufügen");
	private JMenuItem itemRemoveZeile = new JMenuItem("Zeile entfernen");

	public HotButtonView(MainView hf) {
		this.hf = hf;
		sbHotButton = new SoundBoard(hf, 8, 8, hf.getLmmv());
		sbHotButton.setHotButton();
		setLayout(new BorderLayout());
		add(sbHotButton, BorderLayout.CENTER);
		createMenuLayer();
		setJMenuBar(mb);

		setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 4,
				Toolkit.getDefaultToolkit().getScreenSize().height / 5 * 4);
		setVisible(true);
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

	public class ListenerMenuBar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == itemAddSpalte) {
				sbHotButton.addSpalte();
			} else if (e.getSource() == itemRemoveSpalte) {
				sbHotButton.removeSpalte();
			} else if (e.getSource() == itemAddZeile) {
				sbHotButton.addZeile();
			} else if (e.getSource() == itemRemoveZeile) {
				sbHotButton.removeZeile();
			}
		}
	}
}
