package players;

import java.awt.Color;

import players.architecture.Bike;
import players.architecture.Team;

public class ConstantTeam extends Team{

	public ConstantTeam(String teamName, String teamOwner, Color teamColor) {
		super(teamName, teamOwner, teamColor);
	}

	@Override
	public int move(Bike bike) {
		return bike.getDirection();
	}

}
