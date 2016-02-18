package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class PanelListeSongsPlayed extends JPanel {

	private MainView hf;
	private JPanel pnlBorder = new JPanel(new BorderLayout());
	private JList<String> listPlayedSongs;
	private DefaultListModel<String> listModelPlayedSongs = new DefaultListModel<String>();

	public PanelListeSongsPlayed(MainView hf) {
		this.hf = hf;
		listPlayedSongs = new JList<>(listModelPlayedSongs);

		JPanel content = new JPanel(new BorderLayout());
		content.add(listPlayedSongs);
		add(content);
		setPreferredSize(new Dimension(hf.getSize().width / 5, 100));
	}

	public void addSongsPlayed(String filePath) {
		listModelPlayedSongs.addElement(filePath);
		System.out.println("Song wurde zur Liste hinzugefügt");
	}
}
