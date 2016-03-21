package gui;

import javax.swing.UIManager;

import lib.MyFonts;

public abstract class Start {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			MyFonts.importFontSymbols();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainView hf = new MainView();
	}
}