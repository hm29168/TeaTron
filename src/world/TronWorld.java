package world;

import java.util.HashMap;
import java.util.LinkedList;

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
        LinkedList<CustomActor> actors = new LinkedList<CustomActor>();
        LinkedList<Bike> bikes = new LinkedList<Bike>();
        HashMap<Bike, Location> proposedLocations = new HashMap<Bike, Location>();
        
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc)); //Obtain all of the actors in our grid
        
        for (CustomActor a : actors) {
        	if (a instanceof Bike){
        		bikes.add((Bike) a);
        		proposedLocations.put((Bike) a, proposedMove((Bike) a));
        	}
        	if(a instanceof Trail) {
        		((Trail) a).act();
        	}
        }
        resolveConflicts(actors, bikes, proposedLocations);
    }
    
    public void resolveConflicts(LinkedList<CustomActor> actors, 
    							 LinkedList<Bike> bikes, 
    							 HashMap<Bike, Location> proposedLocations) {
    	
    	LinkedList<Bike> crashedBikes = new LinkedList<Bike>();
    	TronGrid<CustomActor> grid = getGrid();
    	
    	for(Bike b : bikes) {
    		Location location = grid.getLocation(b);
    		Location newLocation = proposedLocations.get(b);
    		
    		if(!grid.isValid(newLocation)) { //Going out of the grid (Want to add trail if do this???)
                crashedBikes.add(b);
    		}
    		
    		if(newLocation.equals(location)) { //Staying in the same place or error occurred in move function
    			crashedBikes.add(b);
    		}
    		
    		//This is a pretty inefficient function
    		for(CustomActor a : actors) {
    			if(proposedLocations.get(b).equals(a.getLocation())) { //If you run into a current actor (including other bikes b/c that's where their trail will be)
    				crashedBikes.add(b);
    				break;
    			}
    		}
    		
    		//Check if there are any conflicts in the proposedLocations
    		proposedLocations.remove(b);
    		if(proposedLocations.containsValue(newLocation)) crashBike(b);
    		proposedLocations.put(b, newLocation);
    	}
    	
    	for(Bike b: bikes) {
    		if(crashedBikes.contains(b)) crashBike(b);
    		else moveBike(b, proposedLocations.get(b));
    	}
    }
    
    public Location proposedMove(Bike b){
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	
    	int newDirection;
    	
    	try {
    		newDirection = b.move();
    	} catch (Exception e) {
    		return location;
    	}

    	b.setDirection(newDirection);
    	return location.getAdjacentLocation(newDirection);
    }
    
    public void moveBike(Bike b, Location newLocation) { //This function is only called when we know for certain there is no conflict
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	System.out.println(b);
    	System.out.println(location);
    	System.out.println();
    	
    	//use grid.remove(location) only when you are moving, not when actually pulling the Actor from the Grid
    	grid.remove(location);
        grid.put(newLocation, b);
        grid.put(location, new Trail(grid, b.getColor()));
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
