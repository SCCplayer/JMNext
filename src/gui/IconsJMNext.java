package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class IconsJMNext {
	private static ImageIcon iconShuffle = new ImageIcon(
			IconsJMNext.class.getClassLoader().getResource("resources/shuffle.png"));
	private static ImageIcon iconLoop = new ImageIcon(
			IconsJMNext.class.getClassLoader().getResource("resources/loop.png"));
	private static ImageIcon iconMultiSong = new ImageIcon(
			IconsJMNext.class.getClassLoader().getResource("resources/repeat.png"));

	private static JLabel lblIconShuffle = new JLabel(iconShuffle);
	private static JLabel lblIconLoop = new JLabel(iconLoop);
	private static JLabel lblIconMultiSong = new JLabel(iconMultiSong);

	public static ImageIcon getIconShuffle() {
		return iconShuffle;
	}

	public static void setIconShuffle(ImageIcon iconShuffle) {
		IconsJMNext.iconShuffle = iconShuffle;
	}

	public static ImageIcon getIconLoop() {
		return iconLoop;
	}

	public static void setIconLoop(ImageIcon iconLoop) {
		IconsJMNext.iconLoop = iconLoop;
	}

	public static ImageIcon getIconMultiSong() {
		return iconMultiSong;
	}

	public static void setIconMultiSong(ImageIcon iconMultiSong) {
		IconsJMNext.iconMultiSong = iconMultiSong;
	}

	public static JLabel getLblIconShuffle() {
		return lblIconShuffle;
	}

	public static void setLblIconShuffle(JLabel lblIconShuffle) {
		IconsJMNext.lblIconShuffle = lblIconShuffle;
	}

	public static JLabel getLblIconLoop() {
		return lblIconLoop;
	}

	public static void setLblIconLoop(JLabel lblIconLoop) {
		IconsJMNext.lblIconLoop = lblIconLoop;
	}

	public static JLabel getLblIconMultiSong() {
		return lblIconMultiSong;
	}

	public static void setLblIconMultiSong(JLabel lblIconMultiSong) {
		IconsJMNext.lblIconMultiSong = lblIconMultiSong;
	}
}
