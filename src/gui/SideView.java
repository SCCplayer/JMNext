package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class SideView extends JFrame {
	private SideView sv;
	private SoundBoard sb;
	private MainView hf;
	private int position = 0;
	private String name = "";

	private FensterListener fl = new FensterListener();

	public SideView(MainView hauptfenster, SoundBoard soundBoard, String name, int position) {
		sv = this;
		hf = hauptfenster;
		sb = soundBoard;
		this.position = position;
		this.name = name;
		addWindowListener(fl);
		setLayout(new BorderLayout());
		add(sb, BorderLayout.CENTER);
		setLocation(hf.getSvLocation());
		setSize(hf.getSvSize());
		setVisible(true);
	}

	private class FensterListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {

			System.out.println("closing");
			System.out.println(position);
			hf.setSvLocation(sv.getLocation());
			hf.setSvSize(sv.getSize());
			hf.getTp().add(sb, position);
			hf.getTp().setTitleAt(position, name);
			hf.setSideViewNull();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.out.println("closed");

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
