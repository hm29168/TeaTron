package players;

import java.awt.Color;

import info.gridworld.grid.Location;
import players.architecture.*;

public class ClusterTeam extends Team{

	public ClusterTeam(String teamName, String teamOwner, Color teamColor) {
		super(teamName, teamOwner, teamColor);
	}

	@Override
	public int move(Bike bike) {
		Location location = bike.getLocation();
		
		int averageX = 0, averageY = 0;
		
		for(Bike b : bikes) {
			averageX += b.getLocation().getRow();
			averageY += b.getLocation().getCol();
		}
		
		averageX /= bikes.size();
		averageY /= bikes.size();
		
		int dir = location.getDirectionToward(new Location(averageX, averageY)); // Head towards the center
		if(dir % 90 != 0) dir += 45; //If not a right angle, make it a right angle.
		return dir;
	}
}
