package players;

import players.architecture.Bike;
import players.architecture.Team;

public class ConstantTeam extends Team{

	public ConstantTeam(String teamOwner) {
		super(teamOwner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int move(Bike bike) {
		return bike.getDirection();
	}

}
