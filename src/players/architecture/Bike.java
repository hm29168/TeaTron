package players.architecture;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import world.TronGrid;

public abstract class Bike extends CustomActor{
	String name;
	private TronGrid<CustomActor> grid;
	
	public Bike(TronGrid<CustomActor> gr, String name, Image image, Color color) {
		super(gr, image);
		this.grid = gr;
		this.name = name;
		setColor(color);
	}
	
	//override this function for movement
	public abstract int move();
	
	//helpers
	protected final boolean isValidLocation(Location loc){
		return grid.isValid(loc);
	}
	
	protected final boolean isEmptyLocation(Location loc){
		return (isValidLocation(loc) && grid.get(loc) == null);
	}
	
	protected final List<Location> getOccupiedLocations(){
		return grid.getOccupiedLocations();
	}
	
	protected final List<Location> getUnoccupiedLocations(){
		return grid.getUnoccupiedLocations();
	}
	
	protected final List<Location> getBikeLocations(){
		ArrayList<Location> bikeLocations = new ArrayList<Location>();
		
		for(int row = 0; row < grid.getNumRows(); row ++){
			for(int col = 0; col < grid.getNumCols(); col ++){
				Location loc = new Location(row, col);
				if (grid.get(loc) instanceof Bike){
					bikeLocations.add(loc);
				}
			}
		}
		
		return bikeLocations;
	}
	
	protected final List<Location> getTrailLocations(){
		ArrayList<Location> trailLocations = new ArrayList<Location>();
		
		for(int row = 0; row < grid.getNumRows(); row ++){
			for(int col = 0; col < grid.getNumCols(); col ++){
				Location loc = new Location(row, col);
				if (grid.get(loc) instanceof Trail){
					trailLocations.add(loc);
				}
			}
		}
		
		return trailLocations;
	}
	
    protected final Color getColorAt(Location loc) {
    	if(!isEmptyLocation(loc)) {
    		return ((CustomActor) grid.get(loc)).getColor(); 
    	}
    	
    	return Color.WHITE;
    }
    
    protected final int getDirectionAt(Location loc) {
    	if(!isEmptyLocation(loc)){
    		return ((CustomActor) grid.get(loc)).getDirection();
    	}
    	
    	return 0;
    }
	
    //renders
	public String toString(){
		return "<" + name + ">";
	}
}
