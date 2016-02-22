package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lib.MyFonts;
import listener.ListenerMouseMainView;

public class PanelHotButton extends JPanel {

	private JPanel pnlHeader = new JPanel(new BorderLayout());
	private JPanel pnlKeyNumber = new JPanel(new GridLayout(9, 1));
	private SoundBoard sbHotKey;
	private Dimension x32 = new Dimension(128, 0);

	private ArrayList<JPanel> listPanelWest = new ArrayList<JPanel>();
	private ArrayList<JLabel> listLabelKeyNumber = new ArrayList<JLabel>();

	public PanelHotButton(MainView hf, ListenerMouseMainView lmmv)

	{
		listLabelKeyNumber.add(new JLabel("Taste"));
		listLabelKeyNumber.get(listLabelKeyNumber.size() - 1).setPreferredSize(x32);
		listLabelKeyNumber.get(listLabelKeyNumber.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);

		pnlHeader.add(listLabelKeyNumber.get(listLabelKeyNumber.size() - 1), BorderLayout.WEST);

		listLabelKeyNumber.add(new JLabel("Soundbutton"));
		listLabelKeyNumber.get(listLabelKeyNumber.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);

		pnlHeader.add(listLabelKeyNumber.get(listLabelKeyNumber.size() - 1), BorderLayout.CENTER);

		for (int i = 1; i < 10; i++) {
			listPanelWest.add(new JPanel(new BorderLayout()));
			listLabelKeyNumber.add(new JLabel(String.valueOf(i)));
			listLabelKeyNumber.get(listLabelKeyNumber.size() - 1).setHorizontalAlignment(SwingConstants.CENTER);
			listPanelWest.get(listPanelWest.size() - 1).add(listLabelKeyNumber.get(listLabelKeyNumber.size() - 1));
			listPanelWest.get(listPanelWest.size() - 1).setBorder(BorderFactory.createLineBorder(Color.GRAY));
			listPanelWest.get(listPanelWest.size() - 1).setPreferredSize(x32);
			pnlKeyNumber.add(listPanelWest.get(listPanelWest.size() - 1));
		}
		setLayout(new BorderLayout());
		add(pnlHeader, BorderLayout.NORTH);
		add(pnlKeyNumber, BorderLayout.WEST);
		sbHotKey = new SoundBoard(hf, 9, 1, lmmv);
		add(sbHotKey);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5, 0));

		MyFonts.guiResizeFont(getComponents(), MyFonts.large);
	}
}
