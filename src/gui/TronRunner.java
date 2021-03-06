package gui;


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import info.gridworld.grid.Location;
import players.*;
import players.architecture.Bike;
import world.TronWorld;

public class TronRunner{
	static final int RUN_SPEED = 100; //run-time delay in milliseconds
	static final boolean RANDOM_POSITION = false;
	static final boolean CUSTOM_RENDER = true;
	
	static final boolean USE_SEED = false; //whether or not to use a custom seed
	static long SEED; //controls the setup, not how each Bike runs
	
	static int WORLD_SIZE = 30; //Side length of square world
	static int WINDOW_WIDTH = 600;
	
	private int numCells, cellSize;
	private TronWorld world;
	private TronFrame frame;
	
	private ArrayList<Bike> bikes = new ArrayList<Bike>();
	
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
		tr.setup();
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
	public void shuffleBikes(ArrayList<Bike> bikes){
		Random r = new Random(SEED);
		//make 20 random swaps
		for(int i = 0; i < 20; i ++){
			int indexA = r.nextInt(bikes.size());
			int indexB = r.nextInt(bikes.size());
			Bike save = bikes.get(indexA);
			
			bikes.set(indexA, bikes.get(indexB));
			bikes.set(indexB, save);
		}
	}
	
	public void setup() {
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
		bikes.add(new BestBike(world.getGrid(), "Besty", bikeImage, Color.YELLOW));
		bikes.add(new SimpleBike(world.getGrid(), "Sam", bikeImage, Color.BLUE));
		bikes.add(new SimpleBike(world.getGrid(), "Jabari", bikeImage, Color.RED));
		bikes.add(new SimpleBike(world.getGrid(), "Josh", bikeImage, Color.GREEN));
		
		world.setTotalBikes(bikes.size());
		
		reset();
	}
	
	//setup method
	public void reset(){
		world.clear();
		
		if (!USE_SEED){
			SEED = System.currentTimeMillis();
		}
		System.out.println("Seed: " + SEED);
		
		//shuffle the bikes
		//bikes will be shuffled regardless of whether RANDOM_POSITION is true or not
		//If RANDOM_POSITION is true, then the bikes will not necessarily start in the middle of the corners
		shuffleBikes(bikes);
		
		for(Bike b : bikes){
			b.setup();
		}
		
		if(!RANDOM_POSITION) {
			//remember that Location is in the form of (row, col), not (x, y)!
			int curRow = numCells / 4;
			int curCol = numCells / 4;
			int placeLen = numCells - (curRow * 2) - 1;
			
			double placeDir = 0.0;
			for(int i = 0; i < bikes.size(); i ++){
				if (i >= 4){break;}  //We can't do ordered position for more than 4 bikes
				Bike b = bikes.get(i);
				b.setDirection(180 + (i / 2) * 180);
				world.add(new Location(curRow, curCol), b);
				curRow += Math.sin(placeDir) * placeLen;
				curCol += Math.cos(placeDir) * placeLen;
				placeDir += Math.PI / 2;
			}
		} else {
			for(int i = 0; i < bikes.size(); i ++){
				world.add(bikes.get(i));
			}
		}
		
		System.out.println("");
		show();
	}
}
