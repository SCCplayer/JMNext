package testbereich;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import gui.MainView;

public class ListenerComponentMainView implements ComponentListener {
	MainView hf;

	public ListenerComponentMainView(MainView hf) {
		this.hf = hf;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("MainView Resized");
		System.out.println(hf.getSize().width);
		// hf.setPreferredSizePanelEast();
		hf.validate();
		hf.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
