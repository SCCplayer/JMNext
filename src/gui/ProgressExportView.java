package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ProgressExportView extends JFrame {
	PanelProgressbar pp;
	MainView hf;

	public ProgressExportView(MainView hf) {
		this.hf = hf;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setName("Export Layer Fortschrittsanzeige");
		setSize(new Dimension(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 3,
				Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 5)));

		pp = new PanelProgressbar(this, "Soundbutton-Eigenschaften und Musikdateien werden Exportiert.", "Soundbutton",
				0, hf.getSoundBoardActive().getZeilen() * hf.getSoundBoardActive().getSpalten(),
				hf.getActualFontSize());
		add(pp);
		setLocationRelativeTo(null);
	}

	public void updatePP() {
		pp.nextStep();
	}

	public void setExportDone() {
		pp.setExportDone();
	}

	public void setExportAbgebrochen(String message) {
		pp.setExportAbgebrochen(message);
	}

	public boolean isProzessCanceled() {
		return pp.isProzessCanceled();
	}
}
