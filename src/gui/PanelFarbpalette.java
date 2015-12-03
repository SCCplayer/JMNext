package gui;

import javax.swing.JPanel;

public class PanelFarbpalette extends JPanel {
	PanelColorProperties[] meineFarbEigenschaften = new PanelColorProperties[5];

	public PanelFarbpalette() {
		for (int i = 0; i < 5; i++) {
			meineFarbEigenschaften[i] = new PanelColorProperties();
			add(meineFarbEigenschaften[i]);

		}

	}
}
