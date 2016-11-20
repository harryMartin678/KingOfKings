package AI;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Names;
import GameServer.MethodCallup;
import GameServer.MethodParameter;

public class InitialAI extends IAI {
	
	
	//update loop:
	//assign building sites with avaliable workers
		//-farm, mine, building I can first afford that I don't have 
		//-after 5 cycles build wall 20x20 
		//once all buildings are created just continue with mines and farms 
			//-in or outside wall 
	//building army with resources
		//- building, unit,building, unit
			//-after 5 cycles
		//-build unit with avaliable buildings
			//set: 2x(swordsman, swordsman, spearman, hound, archer),giant
			//-replace unbuildable building with swordsman 
	//give certain army sent to war 
		//-first wave 1 set, second wave 2 set, etc.. till 4 sets
	
	public InitialAI(String AIName,int AINum, AIVision vision){
		
		super(AIName,AINum,vision);
		commands = new ArrayList<AICommand>();
		armyLaunch = new ArrayList<AICommand>();

	}

	@Override
	public void StartAI() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void UpdateForMap(int mapNo) {
		// TODO Auto-generated method stub
		AICommand createMine = createMine(mapNo);
		
		if(createMine != null){
			
			commands.add(createMine);
		}
	}
	
	private AICommand createMine(int mapNo){
		
		ArrayList<Integer> workers = vision.getWorker(mapNo,AINum);
		
		if(workers.size() > 0){
			
			int[] pos = vision.findMineSpot(mapNo);
			
			int minDist = Integer.MAX_VALUE;
			int workerNo = 0;
			for(int w = 0; w < workers.size(); w++){
				
				int dist;
				if(minDist > (dist = getDistance(pos, vision.getUnitPos(workers.get(w))))){
					
					minDist = dist;
					workerNo = workers.get(w);
				}
			}
			
			AICommand command = new AICommand();
			command.command = MethodCallup.BUILDBUILDING;
			MethodParameter parameters = new MethodParameter();
			parameters.setBuildBuilding(pos[0],pos[1], mapNo, AINum,
					Names.MINE, new int[]{workerNo});
			command.parameters = parameters;
			System.out.println("Assign Worker: " + workerNo + " " + pos[0] + " " + pos[1] + " IntialAI");
			return command;
			
		}else{
			return null;
		}
		
	}
	
	private int getDistance(int[] one,int[] two){
		
		return Math.abs(one[0] - two[0]) + Math.abs(one[1] - two[1]);
	}
	
	
}
