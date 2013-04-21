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
		
		for(int i = 0; i < 4; i ++){
			Location newLocation = location.getAdjacentLocation(dir + (i * 90));
			Grid<CustomActor> grid = getGrid();
			if (grid.isValid(newLocation) && grid.get(newLocation) == null){
				dir += (i * 90);
				break;
			}
			
		}
		return dir;
	}
}
