package view.server;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.server.Sheep;
import model.server.Farm;

public class ScorePanel extends JPanel {
	
	private Farm farm;
	public ScorePanel(Farm farm){
		this.farm = farm;
		setPreferredSize(new Dimension(300, 600));
	}
	
	public void updateScore(Farm farm){
		this.farm = farm;
		addScoresToScreen();
	}
	private void addScoresToScreen(){
		List<Sheep> sheeps = farm.getSheeps();
		for(int i = 0; i< sheeps.size(); i++){
			Sheep sheep = sheeps.get(i);
			JLabel lbl = new JLabel(sheep.getName() + ": " + sheep.getScore());
			lbl.setBounds(i, i * (30 + 1), 30, 10);
			this.add(lbl);
		}
	}
	

}
