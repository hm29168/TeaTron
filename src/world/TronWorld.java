package world;

import java.util.ArrayList;

import players.*;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class TronWorld extends World<CustomActor>{
	
	int numCrashed;
	
	//The setMessage function is pretty cool to use in World.java
	//Also, if we really have time, we could implement user control with the keyPressed function to play against our AI :D
	
	TronWorld(int width, int height){
		super(new TronGrid<CustomActor>(width, height));
		numCrashed = 1;
	}
	
    //Need this function so don't have to cast everytime
    public TronGrid<CustomActor> getGrid(){
    	return (TronGrid<CustomActor>) super.getGrid();
    }	

    public void step() {
        Grid<CustomActor> gr = getGrid();
        ArrayList<CustomActor> actors = new ArrayList<CustomActor>();
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc)); //Obtain all of the actors in our grid

        for (CustomActor a : actors) {
        	if (a instanceof Bike){
        		moveBike((Bike) a);
        	}
        	if(a instanceof Trail) {
        		((Trail) a).act();
        	}
        }
    }
    
    public void moveBike(Bike b){
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	int newDirection = b.move();
    	b.setDirection(newDirection);
    	Location newLocation = location.getAdjacentLocation(newDirection);
    	
        if (!grid.isValid(newLocation) || newLocation.equals(location)){
//            throw new IllegalArgumentException("Location " + newLocation
//                    + " is not valid.");
        	crashBike(b);
        }
        
        CustomActor other = grid.get(newLocation);
        
        //crash boom
        if (other != null){
        	if (other instanceof Bike){
        		crashBike((Bike) other);
        	}
        	crashBike(b);
        }
        else {
        	//use grid.remove(location) only when you are moving, not when actually pulling the Actor from the Grid
        	grid.remove(location);
	        grid.put(newLocation, b);
	        grid.put(location, new Trail(grid, b.getColor()));
        }
    }
    
    public void crashBike(Bike b){
    	getGrid().remove(b.getLocation());
    	setMessage("Crash #" + numCrashed + " is " +  b);
    	numCrashed++;
    }

    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, CustomActor occupant)
    {
        TronGrid<CustomActor> grid = getGrid();
        grid.remove(loc);
        grid.put(loc, occupant);
    }

    /**
     * Adds an occupant at a random empty location.
     * @param occupant the occupant to add
     */
    public void add(CustomActor occupant)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, occupant);
    }

    /**
     * Removes an actor from this world.
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given
     * location.
     */
    public CustomActor remove(Location loc)
    {
        CustomActor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        getGrid().remove(loc);
        return occupant;
    }
}
