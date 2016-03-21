package lib;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;

public abstract class MyFonts {
	public final static Font small = new Font("Arial", Font.PLAIN, 14);
	public final static Font medium = new Font("Arial", Font.PLAIN, 22);
	public final static Font large = new Font("Arial", Font.PLAIN, 30);
	public static Font fontCustomSize = new Font("Arial", Font.PLAIN, 14);
	public static Font fontText = new Font("Arial", Font.PLAIN, 14);
	public static Font fontSymbol;

	public static void guiResizeFont(Component[] comp, Font myFont) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				guiResizeFont(((Container) comp[x]).getComponents(), myFont);
			try {
				comp[x].setFont(comp[x].getFont().deriveFont(Font.PLAIN, myFont.getSize()));
			} catch (Exception e) {
			} // do nothing
		}
	}

	public static void importFontSymbols() throws FontFormatException, IOException {
		final ClassLoader classLoader = MyFonts.class.getClassLoader();
		final InputStream is = classLoader.getResourceAsStream("resources/fontawesome-webfont.ttf");
		try {
			fontSymbol = Font.createFont(Font.TRUETYPE_FONT, is);
			fontSymbol = fontSymbol.deriveFont(Font.PLAIN, 30);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontSymbol);
		} finally {
			is.close();
		}
	}

	public static void setSizeFontSymbols(Font myFont) {
		fontSymbol = fontSymbol.deriveFont(Font.PLAIN, myFont.getSize());
	}

	public static void setSizeFontCustomSize(int fontSize) {
		fontCustomSize = fontCustomSize.deriveFont(Font.PLAIN, fontSize);
	}

	public static void setFontText(JLabel textLabel) {
		textLabel.setFont(fontText);
	}

	public static void setFontSymbol(JLabel symbolLabel) {
		symbolLabel.setFont(fontSymbol);
	}

}