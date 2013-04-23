package players;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import players.architecture.Bike;
import players.architecture.CustomActor;

import world.TronGrid;

public class RandomBike extends Bike {

	public RandomBike(TronGrid<CustomActor> gr, String name, Image image, Color color) {
		super(gr, name, image, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int move() {
		return (new Random().nextInt(4) * 90);
	}

}
