package view.server;

public class GameLoop implements Runnable{

	private GridPanel grid;
	public GameLoop(GridPanel grid){
		this.grid = grid;
	}
	@Override
	public void run(){
		long lastLoopTime = System.nanoTime();
	    final int TARGET_FPS = 60;
	    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	    long lastFpsTime = 0;
	    
		while(true) {
			long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;
	        lastLoopTime = now;
	        double delta = updateLength / ((double)OPTIMAL_TIME);
	
	        lastFpsTime += updateLength;
	        if(lastFpsTime >= 1000000000){
	            lastFpsTime = 0;
	        }
	
	        //grid.updateGrid(farm);
	        if(delta >= 1)
	        	grid.repaint();
	        //grid.paintImmediately(this.getBounds());
	        
	        try{
	            Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
	        }catch(Exception e){
	        }
		}
	}
}
