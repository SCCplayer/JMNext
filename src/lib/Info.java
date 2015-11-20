package lib;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public abstract class Info {
	private static Duration gesamtDauer;
	private static double gesamtDauerInMS;
	private static Duration songDauer;
	private static double restDauer;
	private static int minuten;
	private static int sekunden;
	private static int milisekunden;
	private static String anzeigeZeit;
	private static String gesamtZeit;
	private static int percent;

	private JFXPanel myJFXPanel = new JFXPanel();

	public static String getRestzeit(MediaPlayer player) {
		songDauer = player.getTotalDuration().subtract(player.getCurrentTime());
		restDauer = songDauer.toMillis();
		sekunden = (int) (restDauer / 1000);
		minuten = (int) sekunden / 60;
		sekunden = sekunden % 60;
		if (sekunden < 10) {
			anzeigeZeit = String.valueOf(minuten).concat(":0")
					.concat(String.valueOf(sekunden));
		} else {
			anzeigeZeit = String.valueOf(minuten).concat(":")
					.concat(String.valueOf(sekunden));
		}
		return anzeigeZeit;
	}

	public static int getPercent(MediaPlayer player) {
		double d;
		if (player.getCurrentTime().toMillis() != 0
				&& player.getTotalDuration().toMillis() != 0) {
			d = 1000 - (player.getCurrentTime().toMillis() / player
					.getTotalDuration().toMillis()) * 1000;
			percent = (int) d;
		} else {
			percent = 1000;
		}
		// System.out.println(percent);
		return percent;
	}

	public static String getTotalDuration(MediaPlayer player) {
		gesamtDauer = player.getTotalDuration();
		gesamtDauerInMS = gesamtDauer.toMillis();
		sekunden = (int) (gesamtDauerInMS / 1000);
		minuten = (int) sekunden / 60;
		sekunden = sekunden % 60;
		if (sekunden < 10) {
			gesamtZeit = String.valueOf(minuten).concat(":0")
					.concat(String.valueOf(sekunden));
		} else {
			gesamtZeit = String.valueOf(minuten).concat(":")
					.concat(String.valueOf(sekunden));
		}
		return gesamtZeit;
	}

	public static double getRestzeitSekunde(MediaPlayer player) {
		return player.getTotalDuration().subtract(player.getCurrentTime())
				.toSeconds();
	}
}
