package view.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import config.MapConfig;
import model.client.Farm;

public class GridPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Farm farm;
	//private String direction = "none";
	private Map<String,Boolean> keys;

	public GridPanel(Farm farm) {
		this.farm = farm;
		setPreferredSize(new Dimension(600, 600));
		keys = new HashMap<String, Boolean>();
		initializeKeys();
		initializeControls();
//		Thread loop = new Thread(new GameLoop(this));
//		loop.start();
		//add controls
		
        
	}
	public void updateFarm(Farm farm){
		this.farm = farm;
	}
	public Farm getFarm(){
		return this.farm;
	}
	private void initializeKeys(){
		keys.put("up", false);
		keys.put("down", false);
		keys.put("left", false);
		keys.put("right", false);
		keys.put("space", false);
	}
	public void unsetKey(String key){
		keys.put(key,false);
	}
	public void initializeControls(){
		
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        
        inputMap.put(KeyStroke.getKeyStroke("UP"), "UP");
        actionMap.put("UP", new AbstractAction("up") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("up", true);
//				keys.put("down", false);
//				keys.put("left", false);
//				keys.put("right", false);
//				keys.put("space", false);
				
			}
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released UP"), "rUP");
        actionMap.put("rUP", new AbstractAction("rup") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("up", false);
				
			}
        });
        
        //down
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
        actionMap.put("DOWN", new AbstractAction("down") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//keys.put("up", false);
				keys.put("down", true);
				//keys.put("left", false);
				//keys.put("right", false);
				//keys.put("space", false);
				
			}
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released DOWN"), "rDOWN");
        actionMap.put("rDOWN", new AbstractAction("rdown") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("down", false);
				
			}
        });
        
        //left
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        actionMap.put("LEFT", new AbstractAction("left") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				keys.put("up", false);
//				keys.put("down", false);
				keys.put("left", true);
//				keys.put("right", false);
//				keys.put("space", false);
				
			}
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "rLEFT");
        actionMap.put("rLEFT", new AbstractAction("rleft") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("left", false);
				
			}
        });
        
        //right
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        actionMap.put("RIGHT", new AbstractAction("right") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				keys.put("up", false);
//				keys.put("down", false);
//				keys.put("left", false);
				keys.put("right", true);
//				keys.put("space", false);
				
			}
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "rRIGHT");
        actionMap.put("rRIGHT", new AbstractAction("rright") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("right", false);
				
			}
        });     
        
        //space
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");
        actionMap.put("SPACE", new AbstractAction("space") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("space", true);
				
			}
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "rSPACE");
        actionMap.put("rSPACE", new AbstractAction("rspace") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				keys.put("space", false);
				
			}
        }); 
        
        
        
        
	}
	public void updateGrid(Farm farm) {
		this.farm = farm;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = 600 / MapConfig.MAP_WIDTH;
		int height = 600 / MapConfig.MAP_HEIGHT;

		for (int row = 0; row < MapConfig.MAP_WIDTH; row++) {
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
	
	public class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
			if(e.getKeyCode() == KeyEvent.VK_UP){
				keys.put("up", true);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				keys.put("down", true);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				keys.put("left", true);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				keys.put("right", true);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			if(e.getKeyCode() == KeyEvent.VK_UP){
				keys.put("up", true);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				keys.put("down", true);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				keys.put("left", true);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				keys.put("right", true);
			}
		}
	}

	public boolean directionPressed(String string) {
		// TODO Auto-generated method stub
		return keys.get(string);
	}

}
