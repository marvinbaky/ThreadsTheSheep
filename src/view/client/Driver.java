package view.client;

import java.awt.EventQueue;
import java.util.Scanner;

import javax.swing.UIManager;

import model.client.ClientThread;
import model.client.Farm;
import model.client.Tile;


public class Driver {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
//		Server server = Server.getServerInstance();
		Tile[][] tiles = new Tile[100][100];
		
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		
		Farm farm = new Farm(tiles);
		GridPanel grid = new GridPanel(farm);
		
		Scanner sc = new Scanner(System.in);
		String username = sc.next();
		
		ClientThreadWithUI client = new ClientThreadWithUI(username, 1000, grid);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ClientScreen frame = new ClientScreen(grid);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		client.run();
	}
}
