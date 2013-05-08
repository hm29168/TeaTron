package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import info.gridworld.grid.Location;
import players.*;
import players.architecture.Bike;
import players.architecture.Team;
import world.TronWorld;

public class TronRunner{

	public static final boolean VISIBLE = true;
	
	static final int RUN_SPEED = 100; //run-time delay in milliseconds
	static final boolean RANDOM_POSITION = true;
	static final boolean CUSTOM_RENDER = true;
	static final boolean USE_SEED = false; //whether or not to use a custom seed
	static long SEED; //controls the setup, not how each Bike runs
	
	private int numCells, cellSize;
	private TronWorld world;
	private TronFrame frame;
	
	private ArrayList<Team> teams = new ArrayList<Team>();
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
		TronRunner tr = new TronRunner(20, 600, 2);
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
	public int getNumCells() { return numCells; }
	public int getCellSize(){ return cellSize; }
	public long getSeed(){ return SEED;	}
	public TronWorld getWorld(){ return world; }
	
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
	public void shuffleBikes(){
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
	
	//setup method
	public void setup() {
		System.out.println("Number of Cells: " + numCells);
		System.out.println("Cell Size: " + cellSize);
		
		world = new TronWorld(numCells, numCells); //Eventually should be much bigger
		
		//direction is in bearings, wtf. get used to it.
		//^lol, why bearings.
			
		//define our teams
		teams.add(new RandomTeam("StraightOuttaCompton", "Sam", Color.BLUE));
		teams.add(new SimpleTeam("BookClub", "Jabari", Color.RED));
		teams.add(new ConstantTeam("HereIsZeeTeam", "Josh", Color.GREEN));
		teams.add(new ClusterTeam("MoreQQLessPewPew", "Lilly", Color.YELLOW));
		
		//define our bikes
		for(Team t: teams) {
			bikes.addAll(t.generateBikes(world.getGrid()));
		}
		reset();
	}
	
	public void reset(){
		world.clear();
		
		if (!USE_SEED){
			SEED = System.currentTimeMillis();
		}
		System.out.println("Seed: " + SEED);
		
		//shuffle the bikes
		shuffleBikes(); //bikes will be shuffled regardless of whether RANDOM_POSITION is true or not
		
		if(!RANDOM_POSITION) {
			//remember that Location is in the form of (row, col), not (x, y)!
			int curRow = numCells / 4;
			int curCol = numCells / 4;
			int placeLen = numCells - (curRow * 2) - 1;
			
			double placeDir = 0.0;
			for(int i = 0; i < bikes.size(); i ++){
				if (i >= 4){break;}
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
		if (VISIBLE){
			show();
		} else world.run(1);
	}
}
