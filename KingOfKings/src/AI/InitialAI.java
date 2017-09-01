package AI;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import Buildings.Building;
import Buildings.Names;
import GameServer.MethodCallup;
import GameServer.MethodParameter;
import Map.GameEngineCollisionMap;
import Units.Unit;
import Util.Matrix;
import Util.Point;

//mine stop bug is caused by trying to move an unit to a place where it can't go 
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
	
	//private int beat;
	private int buildingTurnNo;
	private int nonResourceBuildingNo;
	private String[] buildingQueue = new String[]{Names.STOCKPILE,Names.SWORDSSMITH,Names.SPEARYARD,
			Names.HOUNDPIT,Names.STOCKPILE,Names.GIANTLIAR,Names.CASTLE};
	private String[] unitQueue = new String[]{Names.WORKER,Names.SWORDSMAN,Names.WORKER,Names.SWORDSMAN,
			Names.ARCHER,Names.SPEARMAN,Names.GIANT,Names.HOUND};
	private int unitBuildNo;
	private Random generator;
	private int buildingsToUnit;
	private int MAX_WORKERS_PER_MAP = 5;
	
	public InitialAI(String AIName,int AINum, AIVision vision,long seed){
		
		super(AIName,AINum,vision);
		commands = new ArrayList<AICommand>();
		armyLaunch = new ArrayList<AICommand>();
		buildingTurnNo = 0;
		unitBuildNo = 0;
		//beat = 0;
		buildingsToUnit = 0;
		generator = new Random(seed);

	}

	@Override
	public void StartAI() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void UpdateForMap(int mapNo,int food,int gold) {
		// TODO Auto-generated method stub
		
		//System.out.println(food + " " + gold + " IntialAI");
		
		if(buildingsToUnit == 2){
			
			boolean hasCreated = queueUnits(mapNo, food, gold);
			if(hasCreated){
				buildingsToUnit = 0;
			}
			
		}else{
			
			boolean hasBuilt = buildAIBuilding(mapNo,food,gold);
			
			if(hasBuilt){
				
				buildingsToUnit++;
				
			}
			
		}
//		if(beat == 10){
//			
//			queueUnits(mapNo,food,gold);
//			beat = 0;
//			
//		}else{
//			
//			buildAIBuilding(mapNo,food,gold);
//		}
//		
//		beat++;
	}
	
	private boolean enoughResToBuildBuilding(int food, int gold, String name){
		
		Building building =  GameGraphics.Building.GetBuildingClass(name);
		
		return building.FoodNeeded() <= food && building.GoldNeeded() <= gold;
	}
	
	private boolean enoughResToCreateUnit(int food, int gold, String name){
		
		Unit unit = Unit.GetUnit(name);
		
		return unit.foodNeeded() <= food && unit.goldNeeded() <= gold;
	}
	
	private boolean buildAIBuilding(int mapNo,int food, int gold){
		
		AICommand build = null;
		if(buildingTurnNo == 0){
			
			build = createBuilding(mapNo,Names.MINE,food,gold);
		
		}else if(buildingTurnNo == 1){
			
			build = createBuilding(mapNo,Names.FARM,food,gold);
		
		}else if(buildingTurnNo == 2){
			
			if(buildingQueue[nonResourceBuildingNo] == Names.WALL){
				
				build = createWall(mapNo,food,gold);
				
			}else{
				
				build = createBuilding(mapNo, buildingQueue[nonResourceBuildingNo],food,gold);
			}
			
			if(build != null){
				nonResourceBuildingNo = (nonResourceBuildingNo+1)%buildingQueue.length;
			}
		}
		
		if(build != null){
			
			System.out.println(build.parameters.buildingType + " InitalAI");
			commands.add(build);
			buildingTurnNo = (buildingTurnNo+1) % 3;
		}
		
		return build != null;
		
		
	}

	private AICommand createWall(int mapNo, int food, int gold) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = vision.getWorker(mapNo, AINum);
		
		if(workers.size() > 0){
			int wallSize = vision.getRequiredWallSize(mapNo);
			Point mapCenter = vision.getMapCenter(mapNo);
			AICommand command = new AICommand();
			command.command = MethodCallup.BUILDWALLS;
			MethodParameter parameters = new MethodParameter();
			parameters.setBuildWall(mapCenter, wallSize, Matrix.ToIntArray(workers), mapNo, AINum);
			command.parameters = parameters;
		}
		
		
		return null;
	}

	private AICommand createBuilding(int mapNo, String buildingType,int food,int gold) {
		// TODO Auto-generated method stub
		
		if(!enoughResToBuildBuilding(food,gold,buildingType)){
			
			//System.out.println(food + " " + gold + " InitialAI");
			return null;
		}
		ArrayList<Integer> workers = vision.getWorker(mapNo, AINum);
		
		//System.out.println(workers.size() + " InitialAI");
		
		if(workers.size() > 0){
			
			int[] pos = vision.findBuildingSpot(mapNo, buildingType,generator);
			int closestWorker = getClosestWorker(workers, pos);
			
			return createAIBuildCommand(closestWorker, pos, mapNo,buildingType);
		}
		
		return null;
	}
	
	private boolean queueUnits(int mapNo,int food, int gold){
		
		AICommand[] unitsToCreate = findUnitToCreate(mapNo,unitQueue[unitBuildNo],food, gold);
		
		for(int u = 0;  unitsToCreate != null && u < unitsToCreate.length; u++){
			
			commands.add(unitsToCreate[u]);
		}
		
		//if(unitsToCreate.length > 0){
			
		unitBuildNo++;
		
		if(unitBuildNo == unitQueue.length){
			
			unitBuildNo = 0;
		}
		
		return unitsToCreate != null;
		//}
	}
	
	private AICommand[] findUnitToCreate(int mapNo, String unitName,int food,int gold) {
		// TODO Auto-generated method stub
		
		if(!enoughResToCreateUnit(food, gold, unitName) && 
				((unitName == Names.WORKER && GameEngineCollisionMap.noOfWorkersOnMap(mapNo) < MAX_WORKERS_PER_MAP )
						|| unitName != Names.WORKER)){
			
			//System.out.println(food + " " + gold + " InitialAI");
			return null;
		}
		
		ArrayList<Integer> buildings = vision.getUnitBuilder(unitName, mapNo);
		AICommand[] commands = new AICommand[buildings.size()];
		
		int c = 0;
		for(int buildingNo : buildings){
			
			MethodParameter parameters = new MethodParameter();
			parameters.setAddUnitToBuildQueue(buildingNo, unitName, AINum);
			commands[c] = new AICommand();
			commands[c].command = MethodCallup.ADDUNITTOBUILDINGQUEUE;
			commands[c].parameters = parameters;
			c++;
		}
		
		return commands;
	}
	
	private AICommand createAIBuildCommand(int closestWorker,int[] pos,int mapNo,String buildingType){
		
		AICommand command = new AICommand();
		command.command = MethodCallup.BUILDBUILDING;
		MethodParameter parameters = new MethodParameter();
		parameters.setBuildBuilding(pos[0],pos[1], mapNo, AINum,
				buildingType, new int[]{closestWorker});
		command.parameters = parameters;
		
		return command;
	}
	
	private int getClosestWorker(ArrayList<Integer> workers, int[] pos){
		
		int minDist = Integer.MAX_VALUE;
		int workerNo = 0;
		for(int w = 0; w < workers.size(); w++){
			
			int dist;
			if(minDist > (dist = getDistance(pos, vision.getUnitPos(workers.get(w))))){
				
				minDist = dist;
				workerNo = workers.get(w);
			}
		}
		
		return workerNo;
	}
	
	private int getDistance(int[] one,int[] two){
		
		return Math.abs(one[0] - two[0]) + Math.abs(one[1] - two[1]);
	}
	
	
}
