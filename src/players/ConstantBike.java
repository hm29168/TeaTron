package players;

import java.awt.Color;

import players.architecture.Bike;
import players.architecture.CustomActor;

import world.TronGrid;

public class ConstantBike extends Bike{

	public ConstantBike(TronGrid<CustomActor> gr, String name, Color color) {
		super(gr, name, color);
	}

	@Override
	public int move() {
		return getDirection();
	}

}
