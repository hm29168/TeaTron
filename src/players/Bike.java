package players;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class Bike extends CustomActor{
	String name;
	public Bike(){
		this("Some Bike");
	}
	
	public Bike(String name){
		super();
		this.name = name;
	}
	
	//override this function for movement
	public abstract int move();
	
	//random movement of turning 90 degrees (boxbug)
	public final void act(){
		setDirection(move());
		int dir = getDirection();
		
		Location newLoc = getLocation().getAdjacentLocation(dir);
		moveTo(newLoc);
	}
	
	private final void moveTo(Location newLocation)
    {
		Grid<CustomActor> grid = getGrid();
		Location location = getLocation();
		
        if (grid == null){
            throw new IllegalStateException("This actor is not in a grid.");
        }
        
        if (grid.get(location) != this){
            throw new IllegalStateException(
                    "The grid contains a different custom actor at location "
                            + location + ".");
        }
        
        if (!grid.isValid(newLocation)){
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");
        }

        //okay for now, but let's throw an error (you always are going to move)
        if (newLocation.equals(location)){
            return;
        }
        
        CustomActor other = grid.get(newLocation);
        
        //crash boom
        if (other != null){
        	if (other instanceof Bike){
        		Bike otherBike = (Bike)other;
        		otherBike.crash();
        	}
        	crash();
        }
        else {
        	//use grid.remove(location) only when you are moving, not when actually pulling the Actor from the Grid
        	grid.remove(location);
	        setLocation(newLocation);
	        grid.put(newLocation, this);
        }
    }
	
	private final void crash(){
		removeSelfFromGrid(); //call this instead of using grid.remove(location);
		System.out.println(this + " has crashed.");
	}
	
	public String toString(){
		return "[" + name + "]";
	}
}
