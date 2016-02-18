package testbereich;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelColorProperties extends JPanel {
	JLabel lblTextColor;

	public PanelColorProperties() {
		lblTextColor = new JLabel("Farbe");
		setLayout(new BorderLayout());
		add(lblTextColor, BorderLayout.CENTER);
		setPreferredSize(new Dimension(64, 64));

	}
}
