package players;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Bike extends CustomActor{
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
			System.out.println("turn");
			setDirection(dir + 90);
			act();
		}
	}
	
	public void moveTo(Location newLocation)
    {
        if (grid == null)
            throw new IllegalStateException("This actor is not in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different actor at location "
                            + location + ".");
        if (!grid.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        CustomActor other = grid.get(newLocation);
        grid.remove(location);
        
        //crash boom
        if (other != null){
        	System.out.println(this + " has crashed.");
        }
        else {
	        location = newLocation;
	        grid.put(location, this);
        }
    }
	
	public String toString(){
		return "Bike";
	}
}
