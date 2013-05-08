package players;

import java.awt.Color;
import java.awt.Image;

import players.architecture.Bike;
import players.architecture.CustomActor;

import world.TronGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class SimpleBike extends Bike{
	
	public SimpleBike(TronGrid<CustomActor> gr, String name, Image image, Color color) {
		super(gr, name, image, color);
	}

	@Override
	public int move(){
		int dir = getDirection();
		Location location = getLocation();
		
		//check left, forward, right (relative to my current direction)
		for(int i = 0; i < 4; i ++){
			Location newLocation = location.getAdjacentLocation(dir + (i * 90));
			if (isEmptyLocation(newLocation)){
				dir += (i * 90);
				break;
			}
			
		}
		return dir;
	}
}
