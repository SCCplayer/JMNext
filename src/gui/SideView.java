package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class SideView extends JFrame {
	private SoundBoard sb;
	private MainView hf;
	private int position = 0;
	private String name = "";

	private FensterListener fl = new FensterListener();

	public SideView(MainView hauptfenster, SoundBoard soundBoard, String name,
			int position) {
		hf = hauptfenster;
		sb = soundBoard;
		this.position = position;
		this.name = name;
		addWindowListener(fl);
		setLayout(new BorderLayout());
		add(sb, BorderLayout.CENTER);
		setLocation(50, 50);
		setSize(1000, 500);
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
			hf.getTp().add(sb, position);
			hf.getTp().setTitleAt(position, name);
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
