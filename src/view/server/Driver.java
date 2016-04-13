package view.server;

import java.awt.EventQueue;

import javax.swing.UIManager;

import model.server.Farm;
import model.server.Server;

public class Driver {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		Server server = Server.getServerInstance();
		Farm farm = new Farm();
		GridPanel grid = new GridPanel(farm);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ServerScreen frame = new ServerScreen(grid);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		server.acceptConnection(farm,grid);
	}
}
