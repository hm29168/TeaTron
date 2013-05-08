package players.architecture;
import java.awt.Color;
import java.awt.Image;

import world.TronGrid;

public class Trail extends CustomActor{

    private static final double DARKENING_FACTOR = 0.05;
    private static final int DARK_LIMIT = 150;
	
	public Trail(TronGrid<CustomActor> gr, Image image, Color color){
		super(gr, image);
		setColor(color);
	}
	
	public void act(){ //Grid world code for darkening
        Color c = getColor();
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));
        
        if(red >= DARK_LIMIT || blue >= DARK_LIMIT || green >= DARK_LIMIT) { //Don't darken to the point where we can't tell the original color
        	setColor(new Color(red, green, blue));
        }
	}
}
