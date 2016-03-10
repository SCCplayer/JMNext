package lib;

import gui.MainView;
import gui.SoundBoard;

public class CheckLayerConsistenz implements Runnable {
	MainView hf;

	public CheckLayerConsistenz(MainView hf) {
		// TODO Auto-generated constructor stub
		this.hf = hf;
	}

	@Override
	public void run() {
		for (SoundBoard soundboard : hf.getSbVector()) {
			checkLayer(soundboard);
		}
	}

	private void checkLayer(SoundBoard sb) {
		int spalten = sb.getSpalten();
		int zeilen = sb.getZeilen();
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sb.getSbArray()[z][sp].setIcon();
			}
		}
	}

}
