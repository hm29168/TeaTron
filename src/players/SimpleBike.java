package players;

import world.TronGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class SimpleBike extends Bike{
	public SimpleBike(TronGrid<CustomActor> gr){
		this(gr, "Some Bike");
	}
	
	public SimpleBike(TronGrid<CustomActor> gr, String name){
		super(gr);
		this.name = name;
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
