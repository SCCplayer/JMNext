package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ProgressImportView extends JFrame {
	PanelProgressbar pp;
	MainView hf;

	public ProgressImportView(MainView hf) {
		this.hf = hf;
		setSize(new Dimension(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5 * 3,
				Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 5)));
		pp = new PanelProgressbar(this, "Der ausgewählte Layer wird importiert.", "Soundbutton", 0, 100,
				hf.getActualFontSize());
		add(pp);
		setLocationRelativeTo(null);
	}

	public void updatePP() {
		pp.nextStep();
	}

	public void setMaxPP(int max) {
		pp.setMax(max);
	}

	public void setImportDone(String message) {
		pp.setImportDone(message);
	}

	public boolean isProzessCanceled() {
		return pp.isProzessCanceled();
	}
}
