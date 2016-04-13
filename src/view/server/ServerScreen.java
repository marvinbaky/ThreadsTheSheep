package view.server;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerScreen extends JFrame {

		/**
	 * Create the frame.
	 */
	public ServerScreen(GridPanel grid) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 1000, 760);
		//setContentPane(grid);
		this.add(grid);
	}

}
