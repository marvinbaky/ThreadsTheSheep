package view.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientScreen extends JFrame {

		/**
	 * Create the frame.
	 */
	public ClientScreen(GridPanel grid) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 800, 760);
		//setContentPane(grid);
		this.add(grid);
	}

}
