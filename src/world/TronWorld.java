package world;

import java.util.ArrayList;

import players.*;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class TronWorld extends World<CustomActor>{
	
	TronWorld(int width, int height){
		super(new TronGrid<CustomActor>(width, height));
	}
	
	public void show()
    {
        super.show();
    }

    public void step()
    {
        Grid<CustomActor> gr = getGrid();
        ArrayList<CustomActor> actors = new ArrayList<CustomActor>();
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc));

        for (CustomActor a : actors)
        {
            // only act if another actor hasn't removed a
        	//also handle the trail-making here and anything else game-side
            if (a.getGrid() == gr && a.getLocation() != null){
            	if (a instanceof Bike){
            		Bike thisBike = (Bike)a;
            		moveBike(thisBike);
            	}
            }
        }
    }
    
    public TronGrid<CustomActor> getGrid(){
    	return (TronGrid<CustomActor>) super.getGrid();
    }
    
    public void moveBike(Bike b){
    	TronGrid<CustomActor> grid = getGrid();
    	Location location = grid.getLocation(b);
    	int newDirection = b.move();
    	b.setDirection(newDirection);
    	Location newLocation = location.getAdjacentLocation(newDirection);
    	
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
        		crashBike(otherBike);
        	}
        	crashBike(b);
        }
        else {
        	//use grid.remove(location) only when you are moving, not when actually pulling the Actor from the Grid
        	grid.remove(location);
	        grid.put(newLocation, b);
	        grid.put(location, new Trail(grid));
        }
    }
    
    public void crashBike(Bike b){
    	getGrid().remove(b.getLocation());
    	System.out.println(b + " has crashed.");
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
