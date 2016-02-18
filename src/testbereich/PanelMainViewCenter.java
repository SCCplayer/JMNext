package testbereich;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.MainView;

public class PanelMainViewCenter extends JPanel {
	private MainView hf;
	private JPanel left = new JPanel(new BorderLayout());
	private JPanel right = new JPanel(new BorderLayout());

	public PanelMainViewCenter(MainView hf) {
		this.hf = hf;
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		left.setBackground(Color.red);
		left.setPreferredSize(new Dimension(1000, 1000));
		left.add(new JLabel("left"), BorderLayout.CENTER);
		right.setBackground(Color.green);
		right.setPreferredSize(new Dimension(1000, 1000));

		c.weightx = 1;
		c.weighty = 1;
		c.gridy = 0;
		c.gridx = 0;
		add(left, c);
		c.gridx = 1;
		add(right, c);
	}
}
