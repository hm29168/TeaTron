package players;
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
	
	//override this function for movement
	public abstract int move();
	
	public String toString(){
		return "[" + name + "]";
	}
}
