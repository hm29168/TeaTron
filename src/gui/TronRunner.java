package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import info.gridworld.grid.Location;
import players.*;
import players.architecture.Bike;
import world.TronWorld;

public class TronRunner{
	static final int cellSize = 48; //does nothing yet
	
	static boolean RANDOM_POSITION = true;
	
	public static void main(String[] args){
		TronRunner tr = new TronRunner();
		tr.setupWorld();
	}
	
	public void setupWorld(){
		TronWorld world = new TronWorld(10, 10); //Eventually should be much bigger
		
		//Eventually, we want this function to put the bikes in predetermined (random?) positions
		//Then, we can just run it and see who wins
		
		//direction is in bearings, wtf. get used to it.
		Image bikeImage = null;
		 try {
				bikeImage = ImageIO.read(new File(getClass().getResource("/players/architecture/Bike.gif").toURI()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		Bike a = new ConstantBike(world.getGrid(), "Jabari", bikeImage, Color.RED);
		Bike b = new SimpleBike(world.getGrid(), "Lilly", bikeImage, Color.YELLOW);
		Bike c = new RandomBike(world.getGrid(), "Josh", bikeImage, Color.GREEN);
		Bike d = new SimpleBike(world.getGrid(), "Sam", bikeImage, Color.BLUE);
		
		if(!RANDOM_POSITION) {
			//NOTE!! IN THE COMPETITION, WE NEED TO MAKE THIS ORDERED LAYOUT PSEUDO-RANDOM TOO
			//i.e. we can not have Bike A start out in the bottom right position everytime
			a.setDirection(0);
			b.setDirection(90);
			c.setDirection(180);
			d.setDirection(270);
			
			//remember that Location is in the form of (row, col), not (x, y)!
			int rows = world.getGrid().getNumRows();
			int cols = world.getGrid().getNumCols();
			world.add(new Location(rows - 1, 0), a);
			world.add(new Location(0, 0), b);
			world.add(new Location(0, cols - 1), c);
			world.add(new Location(rows - 1, cols - 1), d);
		} else {
			world.add(a);
			world.add(b);
			world.add(c);
			world.add(d);
		}
		
		world.show(cellSize);
	}
}
