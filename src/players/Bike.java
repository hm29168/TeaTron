package players;
import java.awt.Color;

import world.TronGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class Bike extends CustomActor{
	String name;
	
	public Bike(TronGrid<CustomActor> gr){
		this(gr, "Some Bike");
	}
	
	public Bike(TronGrid<CustomActor> gr, String name){
		super(gr);
		this.name = name;
	}
	
	public Bike(TronGrid<CustomActor> gr, String name, Color color) {
		this(gr, name);
		setColor(color);
	}
	
	//override this function for movement
	public abstract int move();
	
	public String toString(){
		return "[" + name + "]";
	}
}
