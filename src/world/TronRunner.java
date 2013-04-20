package world;

import info.gridworld.grid.Location;
import players.SimpleBike;
import players.Trail;

public class TronRunner {
	public static void main(String[] args){
		TronWorld world = new TronWorld(10, 10); //Eventually should be much bigger
		
		//Eventually, we want this function to put the bikes in predetermined (random?) positions
		//Then, we can just run it and see who wins
		
		//Alternatively, rather than manually tabulating it, we could make logs / print outs
		//These logs will say who crashed at what time so we can see who won each round and who got second or so
		world.add(new Location(1, 1), new SimpleBike(world.getGrid(), "Test"));
		world.add(new Location(2, 4), new SimpleBike(world.getGrid(), "Lilly"));
		world.add(new Location(1, 2), new Trail(world.getGrid()));
		world.show();
	}
}
