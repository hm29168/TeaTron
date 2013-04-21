package world;

import java.awt.Color;

import info.gridworld.grid.Location;
import players.Bike;
import players.SimpleBike;

public class TronRunner {
	
	static boolean RANDOM_POSITION = true;
	
	public static void main(String[] args){
		TronWorld world = new TronWorld(10, 10); //Eventually should be much bigger
		
		//Eventually, we want this function to put the bikes in predetermined (random?) positions
		//Then, we can just run it and see who wins
		
		//Alternatively, rather than manually tabulating it, we could make logs / print outs
		//These logs will say who crashed at what time so we can see who won each round and who got second or so
		
		//direction is in bearings, wtf. get used to it.

		Bike a = new SimpleBike(world.getGrid(), "Jabari",Color.RED);
		Bike b = new SimpleBike(world.getGrid(), "Lilly", Color.YELLOW);
		Bike c = new SimpleBike(world.getGrid(), "Josh", Color.GREEN);
		Bike d = new SimpleBike(world.getGrid(), "Sam", Color.BLUE);
		
		if(!RANDOM_POSITION) {
			//NOTE!! IN THE COMPETITION, WE NEED TO MAKE THIS ORDERED LAYOUT PSEUDO-RANDOM TOO
			//i.e. we can not have Bike A start out in the bottom right position everytime
			a.setDirection(0);
			b.setDirection(90);
			c.setDirection(180);
			d.setDirection(270);
			
			//remember that Location is in the form of (row, col), not (x, y)!
			int rows = world.getGrid().getNumRows();
			int cols = world.getGrid().getNumCols();
			world.add(new Location(rows - 1, 0), a);
			world.add(new Location(0, 0), b);
			world.add(new Location(0, cols - 1), c);
			world.add(new Location(rows - 1, cols - 1), d);
		} else {
			world.add(a);
			world.add(b);
			world.add(c);
			world.add(d);
		}
		world.show();
	}
}
