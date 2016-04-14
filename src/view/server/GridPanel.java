package view.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import config.MapConfig;
import model.server.Farm;

public class GridPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Farm farm;

	public GridPanel(Farm farm) {
		this.farm = farm;
		setPreferredSize(new Dimension(300, 600));
		//Thread loop = new Thread(new GameLoop(this));
		//loop.start();
	}

	public void updateGrid(Farm farm) {
		this.farm = farm;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = 600 / MapConfig.MAP_WIDTH;
		int height = 600 / MapConfig.MAP_HEIGHT;

		for (int row = 0; row < 50; row++) {
			for (int col = 0; col < MapConfig.MAP_HEIGHT; col++) {
				if (farm.cellHasSheep(col, row)) {
					g.setColor(Color.WHITE);
				} else if (farm.cellHasGrass(col, row)) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.GRAY);
				}
				g.fillRect(row * (width + 1) + 5, col * (height + 1) + 5,
						width, height);

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
