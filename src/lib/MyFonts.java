package lib;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public abstract class MyFonts {
	public final static Font small = new Font("Arial", Font.PLAIN, 14);
	public final static Font medium = new Font("Arial", Font.PLAIN, 22);
	public final static Font large = new Font("Arial", Font.PLAIN, 30);
	public static Font fontCustomSize = new Font("Arial", Font.PLAIN, 14);
	public static Font fontSymbols;

	public static void guiResizeFont(Component[] comp, Font myFont) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof Container)
				guiResizeFont(((Container) comp[x]).getComponents(), myFont);
			try {
				comp[x].setFont(myFont);
			} catch (Exception e) {
			} // do nothing
		}
	}

	public static void importFontSymbols() throws FontFormatException, IOException {
		final ClassLoader classLoader = MyFonts.class.getClassLoader();
		final InputStream is = classLoader.getResourceAsStream("resources/seguisym.ttf");
		try {
			fontSymbols = Font.createFont(Font.TRUETYPE_FONT, is);
			fontSymbols = fontSymbols.deriveFont(Font.PLAIN, 30);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontSymbols);
		} finally {
			is.close();
		}
	}

	public static void setSizeFontSymbols(Font myFont) {
		fontSymbols = fontSymbols.deriveFont(Font.PLAIN, myFont.getSize());
	}

	public static void setSizeFontCustomSize(int fontSize) {
		fontCustomSize = fontCustomSize.deriveFont(Font.PLAIN, fontSize);
	}

}