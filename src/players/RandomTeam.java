package players;

import java.util.Random;

import players.architecture.Bike;
import players.architecture.Team;

public class RandomTeam extends Team{

	public RandomTeam(String teamOwner) {
		super(teamOwner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int move(Bike bike) {
		return (new Random().nextInt(4) * 90);
	}

	@Override
	public String getBikeImageString() {
		// TODO Auto-generated method stub
		return null;
	}

}
