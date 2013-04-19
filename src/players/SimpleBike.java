package players;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class SimpleBike extends Bike{
	public SimpleBike(){
		super();
	}
	
	public SimpleBike(String name){
		super(name);
	}

	@Override
	public int move(){
		int dir = getDirection();
		Location location = getLocation();
		Location newLocation = location.getAdjacentLocation(dir);
		Grid<CustomActor> grid = getGrid();
		if (!grid.isValid(newLocation)){
			setDirection(dir + 90);
			return move();
		}
		return dir;
	}
}
