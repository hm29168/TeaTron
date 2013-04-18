package players;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Bike extends Actor{
	public Bike(){
		super();
	}
	
	//random movement of turning 90 degrees (boxbug)
	public void act(){
		Grid gr = getGrid();
		int dir = getDirection();
		Location newLoc = getLocation().getAdjacentLocation(dir);
		if (gr.isValid(newLoc)){
			moveTo(newLoc);
		}
		else{
			setDirection(dir + 90);
			act();
		}
	}
}
