package players.architecture;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/*
 * This is where hiveminded AI is developed,
 * so each bike belongs to a Team 
 */
public abstract class Team {
	
	protected ArrayList<Bike> bikes;
	
	protected String teamOwner;
	protected Image teamImage;
	
	private static final String GENERIC_BIKE = "/players/architecture/Bike.gif";
	
	public Team(String teamName){
		this.teamOwner = teamName;
		bikes = new ArrayList<Bike>();
		try {
			teamImage = ImageIO.read(new File(getClass().getResource(getBikeImageString()).toURI()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 
	 */
	public abstract int move(Bike bike);
	
	public void addBike(Bike bike){
		bikes.add(bike);
	}
	
	/*
	 * Each team can provide their own image to be displayed.
	 * Otherwise, the generic image is provided.
	 */
	protected String getBikeImageString(){
		return GENERIC_BIKE;
	}
	
	public Image getTeamImage(){
		return teamImage;
	}
	
	public String toString(){
		return teamOwner;
	}
}
