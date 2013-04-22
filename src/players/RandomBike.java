package players;

import java.awt.Color;
import java.util.Random;

import players.architecture.Bike;
import players.architecture.CustomActor;

import world.TronGrid;

public class RandomBike extends Bike {

	public RandomBike(TronGrid<CustomActor> gr, String name, Color color) {
		super(gr, name, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int move() {
		return (new Random().nextInt(4) * 90);
	}

}
