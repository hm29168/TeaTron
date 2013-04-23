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
	static boolean RANDOM_POSITION = false;
	private int numCells, cellSize;
	private long SEED; //controls the setup, not how each Bike runs
	
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
		TronRunner tr = new TronRunner((long) 1366758834221.0, 20, 600, 2);
		tr.setupWorld();
	}
	
	//constructors
	public TronRunner(int numCells){
		this(System.currentTimeMillis(), numCells, 48);
	}
	
	public TronRunner(int numCells, int maxWidth, int multiple){
		this(System.currentTimeMillis(), numCells, maxWidth, multiple);
	}
	
	public TronRunner(long seed, int numCells, int maxWidth, int multiple){
		this(seed, numCells,
				calculateCellSize(numCells, numCells, maxWidth, multiple)); //actual cell length; use 48 for 100% scale
	}
	
	public TronRunner(long seed, int numCells, int cellSize){
		SEED = seed;
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
	public void setupWorld(){
		System.out.println("Seed: " + SEED);
		System.out.println("Number of Cells: " + numCells);
		System.out.println("Cell Size: " + cellSize);
		
		TronWorld world = new TronWorld(numCells, numCells); //Eventually should be much bigger
		
		//Eventually, we want this function to put the bikes in predetermined (random?) positions
		//Then, we can just run it and see who wins
		
		//direction is in bearings, wtf. get used to it.
		Image bikeImage = null;
		try {
				bikeImage = ImageIO.read(new File(getClass().getResource("/players/architecture/Bike.gif").toURI()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//define our bikes
		Bike[] bikes = {new ConstantBike(world.getGrid(), "Jabari", bikeImage, Color.RED),
				new SimpleBike(world.getGrid(), "Lilly", bikeImage, Color.YELLOW),
				new RandomBike(world.getGrid(), "Josh", bikeImage, Color.GREEN),
				new SimpleBike(world.getGrid(), "Sam", bikeImage, Color.BLUE)};
		
		//shuffle the bikes
		shuffleBikes(bikes); //bikes will be shuffled regardless of whether RANDOM_POSITION is true or not
		
		if(!RANDOM_POSITION) {
			//NOTE!! IN THE COMPETITION, WE NEED TO MAKE THIS ORDERED LAYOUT PSEUDO-RANDOM TOO
			//i.e. we can not have Bike A start out in the bottom right position everytime
			
			//remember that Location is in the form of (row, col), not (x, y)!
			int rows = world.getGrid().getNumRows();
			int cols = world.getGrid().getNumCols();
			
			int curRow = numCells / 4;
			int curCol = numCells / 4;
			int placeLen = numCells - (curRow * 2) - 1;
			
			double placeDir = 0.0;
			for(int i = 0; i < bikes.length; i ++){
				if (i >= 4){break;}
				Bike b = bikes[i];
				b.setDirection(180 + (i / 2) * 180);
				world.add(new Location(curRow, curCol), b);
				curRow += Math.sin(placeDir) * placeLen;
				curCol += Math.cos(placeDir) * placeLen;
				placeDir += Math.PI / 2;
			}
		} else {
			for(int i = 0; i < bikes.length; i ++){
				if (i >= 4){break;}
				world.add(bikes[i]);
			}
		}
		
		System.out.println("");
		world.show(cellSize, RUN_SPEED);
	}
}
