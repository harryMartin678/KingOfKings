package AI;

import java.util.ArrayList;

public abstract class IAI {

	protected String AIName;
	protected int AINum;
	protected AIVision vision;
	protected ArrayList<AICommand> commands;
	protected ArrayList<AICommand> armyLaunch;
	
	public IAI(String AIName,int AINum,AIVision vision) {
		
		this.AIName = AIName;
		this.AINum = AINum;
		this.vision = vision;
	}
	public abstract void StartAI();
	public AICommand collectCommand() {
		// TODO Auto-generated method stub
		AICommand next = commands.get(0);
		commands.remove(0);
		
		return next;
	}
	public ArrayList<AICommand> getArmyLaunch() {
		// TODO Auto-generated method stub
		ArrayList<AICommand> copy = (ArrayList<AICommand>) armyLaunch.clone();
		armyLaunch.clear();
		return copy;
	}

	public abstract void UpdateForMap(int mapNo,int food,int gold);
	public int getNoOfCommands() {
		// TODO Auto-generated method stub
		return commands.size();
	}
}
