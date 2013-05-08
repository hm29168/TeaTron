package players;

import java.awt.Color;
import java.util.Random;

import players.architecture.Bike;
import players.architecture.Team;

public class RandomTeam extends Team{

	public RandomTeam(String teamName, String teamOwner, Color teamColor) {
		super(teamName, teamOwner, teamColor);
	}

	@Override
	public int move(Bike bike) {
		return (new Random().nextInt(4) * 90);
	}

}
