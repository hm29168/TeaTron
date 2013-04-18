package players;
import java.awt.Color;

import info.gridworld.actor.Actor;

public class Trail extends Actor{
	public Trail(){
		super();
		setColor(Color.BLUE); //ideally we will make this player's color
	}
	
	public void act(){
		//check for collisions mayhaps
		//also deal with timers (so that we can fade)
	}
}
