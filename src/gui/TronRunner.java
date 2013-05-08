package gui;


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import javax.imageio.ImageIO;

import info.gridworld.grid.Location;
import players.*;
import players.architecture.Bike;
import world.TronWorld;

public class TronRunner{
	static final int RUN_SPEED = 100; //run-time delay in milliseconds
	static final boolean RANDOM_POSITION = true;
	static final boolean CUSTOM_RENDER = true;
	
	static final boolean USE_SEED = false; //whether or not to use a custom seed
	static long SEED; //controls the setup, not how each Bike runs
	
	static int WORLD_SIZE = 20; //Side length of square world
	static int WINDOW_WIDTH = 600;
	
	private int numCells, cellSize;
	private TronWorld world;
	private TronFrame frame;
	
	//calculates a proper cell size that is a multiple
	public static int calculateCellSize(int rows, int cols, int frameLen, int multiple){
		//find the longer size
		int big = Math.max(rows, cols);
		
		//calculate the ideal cell size that fits perfectly
		double idealSize = (double)(frameLen + (big + 1))/big;
		
		//ensure that it is a multiple
		return (int)(idealSize/multiple) * multiple;
	}
	
	//setup and run
	public static void main(String[] args){
		//create a 20 x 20 world with max window width of 600, with the individual cell size being a multiple of 2
		TronRunner tr = new TronRunner(WORLD_SIZE, WINDOW_WIDTH, 2);
		tr.reset();
	}
	
	//constructors
	public TronRunner(int numCells){
		this(numCells, 48);
	}
	
	public TronRunner(int numCells, int maxWidth, int multiple){
		this(numCells,
				calculateCellSize(numCells, numCells, maxWidth, multiple)); //actual cell length; use 48 for 100% scale
	}
	
	public TronRunner(int numCells, int cellSize){
		this.numCells = numCells;
		this.cellSize = cellSize;
	}
	
	//getters
	public int getNumCells(){
		return numCells;
	}
	
	public int getCellSize(){
		return cellSize;
	}
	
	public long getSeed(){
		return SEED;
	}
	
	public TronWorld getWorld(){
		return world;
	}
	
	public void show(){
		if (CUSTOM_RENDER){
			if (frame == null){
				frame = new TronFrame(this, cellSize, RUN_SPEED);
				frame.setVisible(true);
				frame.repaint();
			}
			
			world.setFrame(frame); //when we make a new world, it needs the current frame
		}
		else{
			world.show();
		}
	}
	
	//a way to shuffle the bikes
	public void shuffleBikes(Bike[] bikes){
		Random r = new Random(SEED);
		//make 20 random swaps
		for(int i = 0; i < 20; i ++){
			int indexA = r.nextInt(bikes.length);
			int indexB = r.nextInt(bikes.length);
			Bike save = bikes[indexA];
			
			bikes[indexA] = bikes[indexB];
			bikes[indexB] = save;
		}
	}
	
	//setup method
	public void reset(){
		if (!USE_SEED){
			SEED = System.currentTimeMillis();
		}
		
		System.out.println("");
		System.out.println("Seed: " + SEED);
		System.out.println("Number of Cells: " + numCells);
		System.out.println("Cell Size: " + cellSize);
		
		world = new TronWorld(numCells, numCells); 
		
		Image bikeImage = null;
		try {
				bikeImage = ImageIO.read(new File(getClass().getResource("/players/architecture/Bike.gif").toURI()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		//define our bikes
		Bike[] bikes = {new ConstantBike(world.getGrid(), "ROBOT", bikeImage, Color.DARK_GRAY),
				new SimpleBike(world.getGrid(), "Lilly", bikeImage, Color.YELLOW),
				new RandomBike(world.getGrid(), "HUMAN", bikeImage, Color.DARK_GRAY),
				new SimpleBike(world.getGrid(), "Sam", bikeImage, Color.BLUE),
				new SimpleBike(world.getGrid(), "Jabari", bikeImage, Color.RED),
				new SimpleBike(world.getGrid(), "Josh", bikeImage, Color.GREEN)};
		
		world.setTotalBikes(bikes.length);
		
		//shuffle the bikes
		//bikes will be shuffled regardless of whether RANDOM_POSITION is true or not
		//If RANDOM_POSITION is true, then the bikes will not necessarily start in the middle of the corners
		shuffleBikes(bikes); 
		
		if(!RANDOM_POSITION) {
			//remember that Location is in the form of (row, col), not (x, y)!
			int curRow = numCells / 4;
			int curCol = numCells / 4;
			int placeLen = numCells - (curRow * 2) - 1;
			
			double placeDir = 0.0;
			for(int i = 0; i < bikes.length; i ++){
				if (i >= 4){break;}  //We can't do ordered position for more than 4 bikes
				Bike b = bikes[i];
				b.setDirection(180 + (i / 2) * 180);
				world.add(new Location(curRow, curCol), b);
				curRow += Math.sin(placeDir) * placeLen;
				curCol += Math.cos(placeDir) * placeLen;
				placeDir += Math.PI / 2;
			}
		} else {
			for(int i = 0; i < bikes.length; i ++){
				world.add(bikes[i]);
			}
		}
		
		System.out.println("");
		show();
	}
}
