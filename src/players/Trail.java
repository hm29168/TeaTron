package players;
import java.awt.Color;

import world.TronGrid;

import info.gridworld.actor.Actor;

public class Trail extends CustomActor{
	public Trail(TronGrid<CustomActor> gr){
		super(gr);
		setColor(Color.BLUE); //ideally we will make this player's color
	}
	
	public void act(){
		//check for collisions mayhaps
		//also deal with timers (so that we can fade)
	}
}
