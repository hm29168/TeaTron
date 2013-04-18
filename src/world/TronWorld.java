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
	
	public static void main(String[] args){
		TronWorld world = new TronWorld(10, 10);
		world.add(new Location(1, 1), new SimpleBike("Test"));
		world.add(new Location(1, 2), new Trail());
		world.show();
	}
	
	TronWorld(int width, int height){
		super(new BoundedGrid<CustomActor>(width, height));
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
            if (a.getGrid() == gr){
                a.act();
            }
        }
    }

    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, CustomActor occupant)
    {
        occupant.putSelfInGrid(getGrid(), loc);
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
        occupant.removeSelfFromGrid();
        return occupant;
    }
}
