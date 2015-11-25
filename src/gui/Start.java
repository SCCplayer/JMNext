package gui;

import javax.swing.UIManager;

public abstract class Start {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// UIManager.put("TabbedPane.tabInsets", new Insets(-50, 50, 50, 50));
		MainView hf = new MainView();
	}
}