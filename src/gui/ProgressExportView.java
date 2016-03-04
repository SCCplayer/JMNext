package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import lib.ExportLayer;

public class ProgressExportView extends JFrame implements Runnable {
	PanelProgressbar pp;
	MainView hf;
	ExportLayer el;

	public ProgressExportView(MainView hf) {
		this.hf = hf;

	}

	public void updatePP() {
		pp.nextStep();
	}

	@Override
	public void run() {
		// el = new ExportLayer(this, hf.getSoundBoardActive(),
		// Browse.getFolder(hf.getActualFontSize()));
		setSize(new Dimension(1000, 500));
		pp = new PanelProgressbar(this, "Soundbuttonproperties und Dateien werden Exportiert", "Soundbutton", 0,
				hf.getSoundBoardActive().getZeilen() * hf.getSoundBoardActive().getSpalten(), hf.getActualFontSize());
		add(pp);
		Thread erster = new Thread(el);
		erster.setName("erster");
		erster.run();

	}
}
