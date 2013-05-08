package players.architecture;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import world.TronGrid;

/*
 * This is where hiveminded AI is developed,
 * so each bike belongs to a Team 
 */
public abstract class Team {
	private final int TEAM_SIZE = 3;
	
	protected ArrayList<Bike> bikes;
	
	protected String teamName;
	protected String teamOwner;
	protected Image teamImage;
	protected Color teamColor;
	
	private static final String GENERIC_BIKE = "/players/architecture/Bike.gif";
	
	public Team(String teamName, String teamOwner, Color teamColor){
		this.teamName = teamName;
		this.teamOwner = teamOwner;
		this.teamColor = teamColor;
		bikes = new ArrayList<Bike>();
		try {
			teamImage = ImageIO.read(new File(getClass().getResource(getBikeImageString()).toURI()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Bike> generateBikes(TronGrid<CustomActor> grid) {
		for(int i = 0; i < TEAM_SIZE ; i++) {
			new Bike(grid,teamOwner + i, getTeamImage(), teamColor,this);
		}
		
		return bikes;
	}
	
	public void addBike(Bike b) {
		bikes.add(b);
	}
	
	/*
	 * Override to get how each bike should move
	 */
	public abstract int move(Bike bike);
	
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
		return teamName;
	}
}
