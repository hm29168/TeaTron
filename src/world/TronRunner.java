package world;

import info.gridworld.grid.Location;
import players.Bike;
import players.SimpleBike;
import players.Trail;

public class TronRunner {
	public static void main(String[] args){
		TronWorld world = new TronWorld(10, 10); //Eventually should be much bigger
		
		//Eventually, we want this function to put the bikes in predetermined (random?) positions
		//Then, we can just run it and see who wins
		
		//Alternatively, rather than manually tabulating it, we could make logs / print outs
		//These logs will say who crashed at what time so we can see who won each round and who got second or so
		
		//direction is in bearings, wtf. get used to it.
		//weird thing. they don't all crash at the same time. wat. timing issues? 
		//or maybe another Bike is crashing before it can crash another. how to deal?
		Bike a = new SimpleBike(world.getGrid(), "Jabari");
		a.setDirection(0);
		Bike b = new SimpleBike(world.getGrid(), "Lilly");
		b.setDirection(90);
		Bike c = new SimpleBike(world.getGrid(), "Josh");
		c.setDirection(180);
		Bike d = new SimpleBike(world.getGrid(), "Sam");
		d.setDirection(270);
		
		//remember that Location is in the form of (row, col), not (x, y)!
		int rows = world.getGrid().getNumRows();
		int cols = world.getGrid().getNumCols();
		world.add(new Location(rows - 1, 0), a);
		world.add(new Location(0, 0), b);
		world.add(new Location(0, cols - 1), c);
		world.add(new Location(rows - 1, cols - 1), d);
		
		world.show();
	}
}
