package gui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class IconDnD extends JFrame {

	public IconDnD() {

		setTitle("Icon Drag & Drop");

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 15));

		ImageIcon icon1 = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/repeat.png"));
		ImageIcon icon2 = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/loop.png"));
		ImageIcon icon3 = new ImageIcon(getClass().getClassLoader()
				.getResource("resources/shuffle.png"));

		JButton button = new JButton(icon2);
		button.setFocusable(false);

		JLabel label1 = new JLabel(icon1, JLabel.CENTER);
		JLabel label2 = new JLabel(icon3, JLabel.CENTER);

		MouseListener listener = new DragMouseAdapter();
		label1.addMouseListener(listener);
		label2.addMouseListener(listener);

		label1.setTransferHandler(new TransferHandler("icon"));
		button.setTransferHandler(new TransferHandler("icon"));
		label2.setTransferHandler(new TransferHandler("icon"));

		panel.add(label1);
		panel.add(button);
		panel.add(label2);
		add(panel);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	class DragMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}
	}

	public static void main(String[] args) {
		new IconDnD();
	}
}