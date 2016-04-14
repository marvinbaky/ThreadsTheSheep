package view.server;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerScreen extends JFrame {

		/**
	 * Create the frame.
	 */
	public ServerScreen(GridPanel grid) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setContentPane(grid);
		this.add(grid);
		this.pack();
	}

}
