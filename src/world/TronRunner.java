package world;

import info.gridworld.grid.Location;
import players.SimpleBike;
import players.Trail;

public class TronRunner {
	public static void main(String[] args){
		TronWorld world = new TronWorld(10, 10);
		world.add(new Location(1, 1), new SimpleBike(world.getGrid(), "Test"));
		world.add(new Location(2, 4), new SimpleBike(world.getGrid(), "Lilly"));
		world.add(new Location(1, 2), new Trail(world.getGrid()));
		world.show();
	}
}
