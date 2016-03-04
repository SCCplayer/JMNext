package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ProgressExportView extends JFrame {
	PanelProgressbar pp;
	MainView hf;

	public ProgressExportView(MainView hf) {
		this.hf = hf;
		setSize(new Dimension(1000, 500));
		pp = new PanelProgressbar(this, "Soundbuttonproperties und Dateien werden Exportiert", "Soundbutton", 0,
				hf.getSoundBoardActive().getZeilen() * hf.getSoundBoardActive().getSpalten(), hf.getActualFontSize());
		add(pp);
	}

	public void updatePP() {
		pp.nextStep();
	}
}
