package players;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import players.architecture.*;

public class SimpleTeam extends Team{

	public SimpleTeam(String teamOwner) {
		super(teamOwner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int move(Bike bike) {
		int dir = bike.getDirection();
		Location location = bike.getLocation();
		
		//check left, forward, right (relative to my current direction)
		for(int i = 0; i < 4; i ++){
			Location newLocation = location.getAdjacentLocation(dir + (i * 90));
			Grid<CustomActor> grid = bike.getGrid();
			if (grid.isValid(newLocation) && grid.get(newLocation) == null){
				dir += (i * 90);
				break;
			}
			
		}
		return dir;
	}
}
