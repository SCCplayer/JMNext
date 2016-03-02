package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import lib.Browse;
import lib.ExportLayer;

public class ProgressExportView extends JFrame {
	PanelProgressbar pp;
	MainView hf;

	public ProgressExportView(MainView hf) {
		this.hf = hf;
		pp = new PanelProgressbar(this, "Soundbuttonproperties und Dateien werden Exportiert", "Soundbutton", 0,
				hf.getSoundBoardActive().getZeilen() * hf.getSoundBoardActive().getSpalten(), hf.getActualFontSize());
		add(pp);

		setSize(new Dimension(1000, 500));
		ExportLayer.save(this, hf.getSoundBoardActive(), Browse.getFolder(hf.getActualFontSize()));
		setVisible(true);
	}

	public void updatePP() {
		pp.nextStep(100 / hf.getSoundBoardActive().getZeilen() * hf.getSoundBoardActive().getSpalten());
	}
}
