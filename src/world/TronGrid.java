package world;

import java.awt.Color;
import java.util.ArrayList;

import players.architecture.CustomActor;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

public class TronGrid<E> extends BoundedGrid<E>{
	public TronGrid(int rows, int cols) {
		super(rows, cols);
	}
	
	public Location getLocation(E obj){
		Location location = null;
		
		if(obj != null) { //If you are searching for a null space, then what are you doing??
			for(Location loc : getOccupiedLocations()) {
				if(get(loc) == obj) {
					location = loc;
					break;
				}
			}
		}
		
		return location;
	}
	
	public boolean isValid(Location loc){
		if (loc == null){return false;}
		return super.isValid(loc);
	}
	
    public ArrayList<Location> getUnoccupiedLocations() {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++) {
            for (int c = 0; c < getNumCols(); c++) {
                Location loc = new Location(r, c); // If there's an object at this location, put it in the array.
                if (get(loc) == null) theLocations.add(loc);
            }
        }

        return theLocations;
    }
    
    //This was requested by Josh?
    public Color getColorAt(Location loc) {
    	if(isValid(loc) && get(loc) != null) {
    		return ((CustomActor) get(loc)).getColor(); 
    	}
    	
    	return Color.WHITE;
    }
}
