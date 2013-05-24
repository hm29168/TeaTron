package players;

import java.awt.Color;
import java.awt.Image;

import players.architecture.Bike;
import players.architecture.CustomActor;

import world.TronGrid;

public class ConstantBike extends Bike{

	public ConstantBike(TronGrid<CustomActor> gr, String name, Image image, Color color) {
		super(gr, name, image, color);
	}
	
	public void setup(){
	}

	@Override
	public int move() {
		return getDirection();
	}

}
