package players.architecture;
import java.awt.Color;


import world.TronGrid;

public abstract class Bike extends CustomActor{
	String name;
	
	public Bike(TronGrid<CustomActor> gr, String name, Color color) {
		super(gr);
		this.name = name;
		setColor(color);
	}
	
	//override this function for movement
	public abstract int move();
	
	public String toString(){
		return "<" + name + ">";
	}
}
