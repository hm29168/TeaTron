package players.architecture;
import java.awt.Color;
import java.awt.Image;

import world.TronGrid;

public class Bike extends CustomActor{
	String name;
	
	private Team team;
	
	public Bike(TronGrid<CustomActor> gr, String name, Image image, Color color, Team team) {
		super(gr, image);
		this.name = name;
		this.team = team;
		team.addBike(this);
		setColor(color);
	}
	
	public int move(){
		return team.move(this);
	}
	
	public String toString(){
		return "<" + name + " of " + team + ">";
	}
}
