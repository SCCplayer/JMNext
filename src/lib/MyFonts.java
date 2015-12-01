package lib;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

public abstract class MyFonts {
	public final static Font small = new Font("Arial", Font.PLAIN, 14);
	public final static Font medium = new Font("Arial", Font.PLAIN, 22);
	public final static Font large = new Font("Arial", Font.PLAIN, 30);

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
}
