package GameServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import AI.AIHandler;
import Buildings.Building;
import Buildings.BuildingAttackList;
import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.BuildingProgress.WorkersSite;
import Buildings.Castle;
import Buildings.Farm;
import Buildings.Names;
import Buildings.Tower;
import Buildings.TowerBattles;
import Buildings.Wall;
import GameClient.ParseText;
import IntermediateAI.FormationMovement;
import IntermediateAI.MapRouteFinder;
import IntermediateAI.Pathfinder;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Map.Map;
import Map.MapList;
import Player.Diplomacy;
import Player.PlayerList;
import Units.Archer;
import Units.Unit;
import Units.UnitBattleList;
import Units.UnitList;
import Units.Worker;
import Util.Matrix;
import Util.Point;


//composition:
//
public class GameEngine{
	
	
	private GameEngineContext context;
	private int beat;
	private int resourceBeat;
	private UserCommandList commands;
	private int communicationTurn;
	private IGotToTurn beacon;
	private int playerNo;
	
	
	public GameEngine(String mapEntry,int playerNo,IGotToTurn beacon,String loadGame){
		
		context = new GameEngineContext();
		commands = new UserCommandList(context);
		
		communicationTurn = 0;
		resourceBeat = 0;

		this.beacon = beacon;
		this.playerNo = playerNo;
		
		context.maps = new MapList(mapEntry);
		context.gameName = mapEntry;
		context.units = new UnitList();
		context.buildings = new BuildingList();
		//context.players = new PlayerList(3,5000,5000);
		context.dip = new Diplomacy(playerNo);
		context.sites = new BuildingProgress();
		context.battles = new UnitBattleList(context.units);
		context.buildingAttackList = new BuildingAttackList(context.maps,context.buildings);
		
		GameEngineCollisionMap.SetUpCollisionMaps(context.maps);
		
		try {
			context.saveGame = new SavedGame();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beat = 0;
		
		//context.players.showPlayersMaps(context.maps);
		
		if(loadGame == null){
			
			
			context.players = new PlayerList(playerNo,5000,5000);
			context.ais = new AIHandler(context.units,context.buildings,context.maps,context.players);
			
			for(int i = 0; i < playerNo; i++){
				context.maps.getMap(i).setPlayer(i);
				context.players.setPlayerViewedMap(i,i);
			}
			
			//stub
			//context.maps.getMap(1).setPlayer(0);
			//context.players.setPlayerViewedMap(0, 1);
			
	//		context.units.addUnit(Names.SWORDSMAN, 0, 50, 50, 0);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 51, 50, 0);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 50, 51, 0);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 52, 50, 0);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 54, 53, 1);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 55, 55, 1);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 54, 54, 1);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 55, 54, 1);
	//		context.units.addUnit(Names.SWORDSMAN, 0, 54, 55, 1);
			
//			context.units.addUnit(Names.WORKER, 0, (context.maps.getMapWidth(0)/2)-6, 
//					(context.maps.getMapHeight(0)/2)-2, 0);
//	
//			context.units.addUnit(Names.HOUND, 0, 
//					(context.maps.getMapWidth(0)/2)-11, (context.maps.getMapHeight(0)/2)-10, 0);
//			context.buildings.addBuilding(1, 0,(context.maps.getMapWidth(0)/2)-11,
//			(context.maps.getMapHeight(0)/2)-15, Names.STOCKPILE);
	
			
			for(int i = 0; i < context.maps.getSize(); i++){
				
				context.units.addUnit(Names.WORKER, i, (context.maps.getMapWidth(0)/2)-6, 
						(context.maps.getMapHeight(0)/2)-2, context.maps.getPlayer(i));
				context.units.addUnit(Names.WORKER, i, (context.maps.getMapWidth(0)/2)-6, 
						(context.maps.getMapHeight(0)/2)-4, context.maps.getPlayer(i));
				//context.maps.getPlayer(i)
				context.buildings.addBuilding(context.maps.getPlayer(i), i, 
						context.maps.getMapWidth(i)/2, context.maps.getMapHeight(i)/2,
						Names.ROYALPALACE);
				//context.buildings.addBuilding(context.maps.getPlayer(i), i, 
					//	(context.maps.getMapWidth(i)/2)+6, (context.maps.getMapHeight(i)/2)+6,
				//		Names.GIANTLIAR);
				//context.buildings.addBuilding(context.maps.getPlayer(i), i, 
						//(context.maps.getMapWidth(i)/2)-10, (context.maps.getMapHeight(i)/2)-10, Names.ARCHERYTOWER);
				//context.buildings.addBuilding(1,i,3,3,Names.STOCKPILE);
	//			context.buildings.addBuilding(1,i,(context.maps.getMapWidth(i)/2),
	//					(context.maps.getMapHeight(i)/2),Names.MINE);
				
				//context.units.addUnit(Names.ARCHER, i, (context.maps.getMapWidth(i)/2)-6, (context.maps.getMapHeight(i)/2)-6, context.maps.getPlayer(i));
				//context.units.addUnit(Names.GIANT, i, (context.maps.getMapWidth(i)/2)-5, (context.maps.getMapHeight(i)/2)-3, context.maps.getPlayer(i));
				//context.units.addUnit(Names.HOUND, i, (context.maps.getMapWidth(i)/2)-11, (context.maps.getMapHeight(i)/2)-10, 2);
				//context.units.addUnit(Names.AXEMAN, i, (context.maps.getMapWidth(i)/2)-9, (context.maps.getMapHeight(i)/2)-7, context.maps.getPlayer(i));
				//context.units.addUnit(Names.AXEMAN, i, (context.maps.getMapWidth(i)/2)-11, (context.maps.getMapHeight(i)/2)-7, context.maps.getPlayer(i));
			}
		
		}else{
			
			try {
				LoadGame loader = new LoadGame(loadGame);
				context.units.addAll(loader.getUnits());
				context.buildings.addAll(loader.getBuildings());
				context.battles.addAll(loader.getBattles());
				context.sites.addAll(loader.getSites(),context.buildings);
				context.buildingAttackList.addAll(loader.getDestructions());
				context.players = new PlayerList(loader.getPlayers());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		Thread onFrame = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					long time = System.currentTimeMillis();
					gameLoop();
					
					long waitTime = 100 - time;
					
					if(waitTime < 10){
						
						waitTime = 10;
					}
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		onFrame.start();
		//this.saveGame();
		
	}
	
	private void CommunicationTurn(){
		
		//commands.callMethods(communicationTurn);
		commands.setUpCallMethods(communicationTurn);
		beacon.ready();
		
		while(true){
			
			if(beacon.isEveryoneReady()){
				
				break;
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		communicationTurn++;
	}
	
	public void gameLoop(){

		if(beacon.gameStarted() && context.maps.NoWinner()){
		
			long start = System.currentTimeMillis();
			if(beat == 5 || beat == 10){
				
				CommunicationTurn();
			}
			
			commands.callMethods(beat%5);
			//System.out.println(units.getUnitX(5) + " " + units.getUnitY(5) + " " 
		///	+ units.getUnitMap(5));
			
			//System.out.println(context.units.getUnitListSize() + " GameEngine");
			
			//reveal map 
			//revealMap();
			//progress unit fights
			long time1 = System.currentTimeMillis();
			context.battles.simulateHit();
			long time2 = System.currentTimeMillis();
			
			//progress unit to tower fights
			context.battles.simulateTowerHit();
			long time3 = System.currentTimeMillis();
			context.buildingAttackList.simulateDestruction();
			long time4 = System.currentTimeMillis();
			//move units
			context.units.moveUnits();
			long time5 = System.currentTimeMillis();
			
			
			
	//		int[] com;
			
//			while((com = context.battles.getUnitsToFollow()) != null){
//				
//				if(!context.units.getUnits(com[0]).isAttacking() && 
//						!context.units.getUnits(com[0]).getRetreat()){
//					MethodParameter parameters = new MethodParameter();
//					parameters.setUnitFollow(com[0], com[1]);
//					//System.out.println(parameters.unitNo + "  " + parameters.unitFollow);
//					commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
//					//this.followUnit(com[0], com[1]);
//				}
//			}
			
			//add resources from farms and mines 
			long time6 = System.currentTimeMillis();
			if(beat == 10){
				
				beat = 0;
				
				resourceBeat ++;
				
				if(resourceBeat == 5 || resourceBeat == 10){
					
					int[] playersGold = new int[context.players.getSize()];
					int[] playersFood = new int[context.players.getSize()];
					
					for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
		
						if(context.buildings.getBuildingType(b).equals(Names.MINE)
								&& resourceBeat == 10){
							
							playersGold[context.buildings.getBuildingPlayer(b)]+=5;
						
						}else if(context.buildings.getBuildingType(b).equals(Names.FARM)){
	
							playersFood[context.buildings.getBuildingPlayer(b)]+=5;
						}
					}
					
					for(int p = 0; p < playersFood.length; p++){
						
						context.players.addPlayerResource(playersFood[p], playersGold[p], p);
					}
					
					if(resourceBeat == 10){
						resourceBeat = 0;
					}
				}
				
				for(int f = 0; f < context.units.getUnitListSize(); f++){
					
					if(context.units.getFollow(f) != -1){
						
						int unitFollow = context.units.getFollow(f);
						
						if(context.units.getMoving(unitFollow) ||
								context.units.isRetreating(unitFollow)){
							
							//System.out.println("RE Follow " + unitFollow);
							MethodParameter parameters = new MethodParameter();
//							parameters.SetMoveUnit(f, (int) context.units.getUnitX(unitFollow),
//									(int) context.units.getUnitY(unitFollow), context.units.getUnitMap(unitFollow),
//									unitFollow);
							parameters.setUnitFollow(f, unitFollow);
							commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
	//						this.moveUnit(f, (int) context.units.getUnitX(unitFollow), (int) context.units.getUnitY(unitFollow),
	//								context.units.getUnitMap(unitFollow),unitFollow);
							
						}
					}
				}
				
				ArrayList<int[]> unitsToRecalculate = context.units.getRecalculated();
				
				for(int r = 0; r < unitsToRecalculate.size(); r++){
					
					if(context.units.getUnits(unitsToRecalculate.get(r)[0]).getRetreat()){
						
						continue;
					}
		
					if(unitsToRecalculate.get(r)[1] == 1){
						
						MethodParameter parameters = new MethodParameter();
						parameters.SetMoveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
								unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4]);
						
						commands.add(MethodCallup.MOVEUNIT,parameters, communicationTurn);
		//				this.moveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
		//						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4]);
					}else{
	//					u,units.get(u).getRecalculate(),target[0],
	//					target[1],target[2],units.get(u).getFollow()
						MethodParameter parameters = new MethodParameter();
						parameters.SetMoveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
								unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4],
								unitsToRecalculate.get(r)[5]);
						
						commands.add(MethodCallup.MOVEUNIT,parameters, communicationTurn);
		//				this.moveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
		//						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4],
		//						unitsToRecalculate.get(r)[5]);
					}
				}
				
				ArrayList<WorkersSite> workerSites = context.sites.findWorkers(context.units);
				HashMap<Integer,int[]> workersWorking = new HashMap<Integer, int[]>();
				
				for(int w = 0; w < workerSites.size(); w++){
					
					int[] distances = distancesFrom(w,
							workerSites.get(w).getWorkers());
					int[] adjWorkers = removeWorkers(workersWorking, workerSites.get(w).getWorkers(),
							distances,w);
					if(adjWorkers.length > 0){
						storeWorkersNowWorking(workersWorking,adjWorkers,distances,w);
					}
				}
				
				for(int[] worker: workersWorking.values()){
					
					MethodParameter parameters = new MethodParameter();
					parameters.setAddWorkerToSite(workerSites.get(worker[1]).getBuildingNo(), 
							workerSites.get(worker[1]).getWorkers());
					commands.add(MethodCallup.ADDWORKERTOSITE, parameters, communicationTurn);
				}
			}
			
			//increment building unit queues progress
			for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
				
				if(!context.buildings.empty(b) && context.buildings.nextUnit(b)){
					
					addUnit(context.buildings.getFinishedUnit(b),context.buildings.getBuilding(b));
				}
			}
			
			//increment building build progress 
			context.sites.checkSites(context.buildings);
			
			if(communicationTurn > 1){
				ArrayList<int[]> newBattles = context.battles.createBattles();
				
				for(int b = 0; b < newBattles.size(); b++){
					
					//System.out.println(newBattles.get(b)[0] + " " + newBattles.get(b)[1] + " GameEngine");
					MethodParameter parameters = new MethodParameter();
					parameters.setUnitAttack(newBattles.get(b)[0], newBattles.get(b)[1]);
					commands.add(MethodCallup.ATTACKUNIT, parameters, communicationTurn);
				}
				
				if(beat % 5 == 0 && context.ais.areAIs()){
					
					context.ais.updateAIs(context.units);
					context.ais.doAICommands(commands,communicationTurn);
				}
				
//				ArrayList<int[]> newWorkerSites = context.units.areWorkersIdle(
//						context.sites.findNewBuilds());
				
//				for(int s = 0; s < newWorkerSites.size(); s++){
//					
//					MethodParameter parameters = new MethodParameter();
//					parameters.setAddWorkerToSite(newWorkerSites.get(s)[0],
//							Matrix.getIntArrayRange(1, newWorkerSites.get(s).length,newWorkerSites.get(s)));
//					commands.add(MethodCallup.ADDWORKERTOSITE, parameters, communicationTurn);
//				}
			}
			
			long time7 = System.currentTimeMillis();
			
//			System.out.println("Loop Time: " + (time1-start) + " " + (time2-time1) + " " + 
//					(time3-time2) + " " + (time4-time3) + " " + (time5-time4) + " " + (time6-time5)
//							+ " " + (time7-time6) + " GameEngine");
			//time2, time 4 time 6
			beat++;
		}
		
		

	}
	
	private int[] distancesFrom(int buildingNo, int[] workers) {
		// TODO Auto-generated method stub
		int[] distances = new int[workers.length];
		
		for(int d = 0; d < distances.length; d++){
			
			distances[d] = (int) (Math.abs(context.sites.getSiteX(buildingNo) 
					- context.units.getUnitX(workers[d])) + Math.abs(context.sites.getSiteY(buildingNo)
							- context.units.getUnitY(workers[d])));
		}
		
		return distances;
	}

	private void storeWorkersNowWorking(HashMap<Integer,int[]> workersWorking,int[] workers,
			int[] distances,int siteID){
		
		for(int w  = 0; w < workers.length; w++){
			
			workersWorking.put(workers[w], new int[]{distances[w],siteID});
		}
		
	}
	
	private int[] removeWorkers(HashMap<Integer,int[]> workersWorking,int[] workers,int[] distance,
			int siteID){
		
		ArrayList<Integer> adjWorkers = new ArrayList<Integer>();
		
		for(int w = 0; w < workers.length; w++){
			
			if(!workersWorking.containsKey(workers[w]) || workersWorking.get(workers[w])[0] > distance[w]){
				adjWorkers.add(workers[w]);
				
				if(workersWorking.containsKey(workers[w])){
					
					workersWorking.remove(workers[w]);
					//workersWorking.put(workers[w], new int[]{distance[w],siteID});
				}
			}
		}
		
		int[] newWorker = new int[adjWorkers.size()];
		
		for(int a = 0; a < adjWorkers.size(); a++){
			
			newWorker[a] = adjWorkers.get(a);
		}
		
		return newWorker;
		
	}
	
	private void addUnit(String unit, Building building){
		
		building.addUnit(unit,context);
	}
	
//	private int[] findSpace(int x, int y, int sizeX, int sizeY, int map){
//		
//		int[] lc = new int[]{sizeX+1,0,-sizeX-1,0,0,sizeY+1,0,-sizeY-1,
//							 sizeX+1,sizeY+1,-sizeX-1,-sizeY-1,sizeX+1,-sizeY-1,
//							 -sizeX-1,sizeY+1
//		};
//		
//		for(int i = 0; i < lc.length; i+=2){
//			
//			if(new CollisionMap(buildings,units,
//					maps.getMap(map)).getCollisionMap()[y+lc[i+1]][x+lc[i]] == 0){
//				
//				return new int[]{x+lc[i],y+lc[i+1]};
//			}
//		}
//		
//		return new int[]{-1,-1};
//	}
	
	private void revealMap(){
		
		//for(int u = 0; u < units.getUnitListSize(); u++){
			
			//if(units.getUnitMoving(u)){
				//players.revealMap((int) units.getUnitX(u), (int) units.getUnitY(u), 
					//	units.getUnitMap(u), units.getUnitPlayer(u),1);
			//}
		//}
		
		for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
			
			if(context.buildings.isBuildlingJustBuilt(b)){
				context.players.revealMap((int)context.buildings.getBuildingX(b), (int)context.buildings.getBuildingY(b),
						context.buildings.getBuildingMap(b), context.buildings.getBuildingPlayer(b),
						context.buildings.getBuildingDiameterX(b));
			}
		}
	}
	

	
	//start game 
	
	public String getGameName(){
		
		return context.gameName;
	}
	
	public String getWhoOwnsWhatMap(){
		
		String mapInfo = "";
		
		for(int m = 0; m < context.maps.getSize(); m++){
			
			mapInfo += " " + m + " " + context.maps.getMap(m).getPlayer();
			
		}
		
		mapInfo = mapInfo.substring(1);
		
		return mapInfo;
	}

	public int getMapOwnedMap(int player){
		
		for(int m = 0; m < context.maps.getSize(); m++){
			
			if(context.maps.getMap(m).getPlayer() == player){
				
				return m;
			}
		}
		
		return -1;
	}
	
	public int getNumberOfMapRows(int map){
		
		return context.maps.getMapWidth(map);
	}
	
	public String getMapRow(int map, int row){
		
		int[] mapRow = context.maps.getMap(map).getRow(row);
		
		String ret = "";
		
		for(int m = 0; m < mapRow.length; m++){
			
			ret += mapRow[m];
		}
		
		return ret;
	}
	
	public String getUnitsOnMap(int map){
		
		String info = "";
		
		
		for(int u = 0; u < context.units.getUnitListSize(); u++){
			
			//System.out.println("unit " + units.getUnitMap(u) + " " + map);
			
			if(context.units.getUnitMap(u) == map){
				
				if(!context.units.getUnitDead(u)){
					
					int moving = 0;
					int attacking = 0;
					
					if(context.units.getUnitMoving(u) && !context.units.getUnitStop(u)){
						
						moving = 1;
					
					}
					
					if(context.units.isAttacking(u)){
						
						attacking = 1;
					}
					
					
					info += u + " " + context.units.getUnitName(u) + " " + context.units.getUnitX(u) +
							" " + context.units.getUnitY(u) + " " + context.units.getUnitPlayer(u) + 
							" " + moving + " " + context.units.getOrientation(u) + " " 
							+ attacking + " " + context.units.getFollow(u) + "\n";
					
					
				}else if(!context.units.hasDeathBeenReported(u)){
					
					info += u + " die\n";
					context.units.setDeathReported(u);
				}
			}
		}
		
		return info;
	}
	
	public String getPlayerResource(int player){
		
		return player + " " + context.players.getPlayersFood(player) + " "
					+ context.players.getPlayersGold(player) + "\n";
	}
	
	public String getBuildingOnMap(int map){
		
		String info = "";
		
		for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
			
			if(!context.buildings.isReservation(b)){
				if(context.buildings.getBuildingMap(b) == map && !context.buildings.isBuildingDestroyed(b)){
					
					String buildingAttack = "-1";
					String buildingAttackX = "-1";
					String buildingAttackY = "-1";
					
					if(context.buildings.getBuilding(b) instanceof Tower){
						
							buildingAttack = new Integer(
									((Tower)context.buildings.getBuilding(b)).getAttacking()).toString();
							if(!buildingAttack.equals("-1")){
								buildingAttackX = new Integer((int)context.units.getUnitX(
										((Tower)context.buildings.getBuilding(b)).getAttacking())).toString();
								buildingAttackY = new Integer((int)context.units.getUnitY(
										((Tower)context.buildings.getBuilding(b)).getAttacking())).toString();
							}
						
					}
	
					info += context.buildings.getBuildingNo(b) + " " + 
							context.buildings.getBuildingType(b) + " " + 
							context.buildings.getBuildingX(b) + " " +
							context.buildings.getBuildingY(b) +  " " +
							context.buildings.getBuildingPlayer(b) 
							+ " " + buildingAttack
							+ " " + buildingAttackX 
							+ " " + buildingAttackY;
					
					if(context.buildings.getBuildingType(b) == Names.WALL){
						
						info += " " + new Float(
								((Wall)context.buildings.getBuilding(b)).getRotationFromCenter()).toString();
					}
					
					info += "\n";
				
				}else if(context.buildings.isBuildingDestroyed(b) 
						&& !context.buildings.getCollapseReported(b)){
					
					info += "collapse " + b + "\n";
					context.buildings.setCollapseReported(b);
				}
			}
		}
		
		info += "sites\n";
		
		for(int s = 0; s < context.sites.size(); s++){
			
			if(context.sites.isOnMap(map, s)){
				
				info += context.sites.getSiteInfo(s);
			}
		}
		
		//System.out.println(info);
		
		return info;
	}
	
	public String getUnitQueues(int player){
		
		
		
		String info = "";
		
		for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
			

			if(context.buildings.getBuildingPlayer(b) == player && context.buildings.getBuildingQueueSize(b) > 0){
				
				info += b + " " + context.buildings.getBuildingQueue(b) + "\n";
			}
		}
		
		return info;
	}
	
	public int getPlayerViewedMap(int player){
		
		return context.players.getPlayerViewedMap(player);
	}
	
	public void parseWayPoints(String input,int passedCommunicationTurn){
		
		int unitNo = new Integer(input.charAt(0)).intValue() - 48;
		
		//remove unitNo and the space at the end of the input string 
		input = input.substring(2, input.length()-1);

		ArrayList<String> numbers = new ParseText(input).getNumbers();
		
		int[] targetX = new int[numbers.size()/3];
		int[] targetY = new int[numbers.size()/3];
		int[] targetMap = new int[numbers.size()/3];
	
		
		for(int t = 0; t < numbers.size(); t+=3){
			
			targetX[t/3] = new Integer(numbers.get(t)).intValue();
			targetY[t/3] = new Integer(numbers.get(t+1)).intValue();
			targetMap[t/3] = new Integer(numbers.get(t+2)).intValue();
		}
		
//		System.out.println("Parse single way points");
//		for(int t = 0; t < targetMap.length; t++){
//			
//			System.out.println(targetX[t] + " " + targetY[t] + " " + targetMap[t]);
//		}
//		System.out.println("Parse single way points");
	
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitWayPoints(unitNo, targetX, targetY, targetMap);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.SETWAYPOINTS, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.SETWAYPOINTS, parameters, passedCommunicationTurn);
		}
		//this.setWayPoints(unitNo,targetX, targetY, targetMap);
	}
	
	public void parseGroupMovement(String inpt,int passedCommunicationTurn){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		int[] unitNos = new int[numbers.size()-3];
		
		int targetX = new Integer(numbers.get(0)).intValue();
		int targetY = new Integer(numbers.get(1)).intValue();
		int targetMap = new Integer(numbers.get(2)).intValue();
		
		for(int g = 3; g < numbers.size(); g++){
			
			unitNos[g-3] = new Integer(numbers.get(g)).intValue();
		}
		
		MethodParameter parameters = new MethodParameter();
		parameters.setGroupMovement(unitNos, targetX, targetY, targetMap);
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.GROUPMOVEMENT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.GROUPMOVEMENT, parameters, passedCommunicationTurn);
		}
		//this.groupMovement(unitNos, targetX, targetY, targetMap);
		
	}
	
	public void parseGroupWayMovement(String inpt, int passedCommunicationTurn){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		ArrayList<Integer> unitNos = new ArrayList<Integer>();
		ArrayList<Integer> targetX = new ArrayList<Integer>();
		ArrayList<Integer> targetY = new ArrayList<Integer>();
		ArrayList<Integer> targetMap = new ArrayList<Integer>();
		
		int n = 0;
		
		while(new Integer(numbers.get(n)).intValue() != -1){
			
			//System.out.println(new Integer(numbers.get(n)).intValue());
			unitNos.add(new Integer(numbers.get(n)));
			n++;
		}
		
		n++;
		
		for(int p = n; p < numbers.size()-2; p+=3){
			
			targetX.add(new Integer(numbers.get(p)));
			targetY.add(new Integer(numbers.get(p+1)));
			targetMap.add(new Integer(numbers.get(p+2)));
		}
		
		MethodParameter parameters = new MethodParameter();
		parameters.setGroupWayPoints(ParseText.toIntArray(unitNos),
				ParseText.toIntArray(targetX), ParseText.toIntArray(targetY), ParseText.toIntArray(targetMap));
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.GROUPWAYPOINTSMOVEMENT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.GROUPWAYPOINTSMOVEMENT, parameters, passedCommunicationTurn);
		}
//		this.groupWayPointsMovement(ParseText.toIntArray(unitNos),
//				ParseText.toIntArray(targetX), ParseText.toIntArray(targetY), ParseText.toIntArray(targetMap));
		
	}
	
	public void parseFollowMovement(String inpt,int passedCommunicationTurn){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		
		int unitNo = new Integer(numbers.get(0)).intValue();
		int unitFollow = new Integer(numbers.get(1)).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitFollow(unitNo, unitFollow);
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.FOLLOWUNIT, parameters, passedCommunicationTurn);
		}
		//this.followUnit(unitNo, unitFollow);
	}
	
	public void parseGroupFollowMovement(String inpt,int passedCommunicationTurn){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt.substring(0, inpt.length()-1));
		
		MethodParameter parameters = new MethodParameter();
		parameters.setGroupFollow(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
				unitNos.get(unitNos.size()-1));
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.GROUPFOLLOW, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.GROUPFOLLOW, parameters, passedCommunicationTurn);
		}
		
//		this.groupFollow(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1))
//				,unitNos.get(unitNos.size()-1));
	}
	
	public void parseGroupAttack(String inpt,int passedCommunicationTurn){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt);
		
		MethodParameter parameters = new MethodParameter();
		parameters.setAttackGroup(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
				unitNos.get(unitNos.size()-1));
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.ATTACKGROUP, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.ATTACKGROUP, parameters, passedCommunicationTurn);
		}
//		this.attackGroup(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
//				unitNos.get(unitNos.size()-1));
	}
	
	private ArrayList<Integer> getUnitNos(String inpt){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();	
		ArrayList<Integer> unitNos = new ArrayList<Integer>();
		
		for(int u = 0; u < numbers.size(); u++){
			
			unitNos.add(new Integer(numbers.get(u)));
		}
		
		return unitNos;
	}
	
	public void parseAttack(String inpt,int passedCommunicationTurn){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		int unitNo = new Integer(numbers.get(0)).intValue();
		int unitAttack = new Integer(numbers.get(1)).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitAttack(unitNo, unitAttack);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.ATTACKUNIT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.ATTACKUNIT, parameters, passedCommunicationTurn);
		}
		//this.attackUnit(unitNo, unitAttack);
	}
	
	public void parseBuildings(String inpt,int passedCommunicationTurn){
		
//		ParseText text = new ParseText(inpt);
//		ArrayList<String> numbers = text.getNumbers();
		
//		numbers.remove(numbers.size()-1);
		
		String[] msgs = inpt.split(" ");
		String name = msgs[msgs.length-1];
		
		int[] unitNos = new int[msgs.length-5];
		
//		System.out.println("GAMEENGINE BB");
//		for(int i = 0; i < msgs.length; i++){
//			
//			System.out.println(msgs[i]);
//		}
//		System.out.println("GAMEENGINE BB");
		
		for(int n = 4; n < msgs.length-1; n++){
			
			if(msgs[n].length() > 0){
				unitNos[n-4] = new Integer(msgs[n]).intValue();
			}
		}
		
//		System.out.println("GAMEENGINE BB UNITNOS");
//		for(int i = 0; i < unitNos.length; i++){
//			
//			System.out.println(unitNos[i]);
//		}
//		System.out.println("GAMEENGINE BB UNITNOS");
//		
		MethodParameter parameters = new MethodParameter();
		parameters.setBuildBuilding(new Float(msgs[0]).intValue(), new Float(msgs[1]).intValue(),
				new Float(msgs[2]).intValue(),new Integer(msgs[3]).intValue(),name,unitNos);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.BUILDBUILDING, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.BUILDBUILDING, parameters, passedCommunicationTurn);
		}
//		this.buildBuilding(new Float(numbers.get(0)).intValue(), new Float(numbers.get(1)).intValue(),
//				new Float(numbers.get(2)).intValue(),player,name,unitNos);
//		
		
	}

	public void parseAddUnitToQueue(String inpt,int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		ParseText text = new ParseText(inpt);
		ArrayList<String> numbers = text.getNumbers();
		String unitType = text.getUnitName();
		
		//System.out.println("enter add unit to queue " + unitType);
		
		MethodParameter parameters = new MethodParameter();
		parameters.setAddUnitToBuildQueue(new Integer(numbers.get(0)).intValue(), unitType,
				new Integer(numbers.get(1)).intValue());
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.ADDUNITTOBUILDINGQUEUE, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.ADDUNITTOBUILDINGQUEUE, parameters, passedCommunicationTurn);
		}
		//this.addUnitToBuildingQueue(new Integer(numbers.get(0)).intValue(), unitType);
		
	}

	public void setNewViewMap(String inpt,int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		String[] msgs = inpt.split(" ");
		int mapNo = new Integer(msgs[0]).intValue();
		int player = new Integer(msgs[1]).intValue();
		MethodParameter parameters = new MethodParameter();
		parameters.setNewViewMap(mapNo, player);
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.NEWVIEWMAP, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.NEWVIEWMAP, parameters, passedCommunicationTurn);
		}
	}
	
	public void parseAttackBuilding(String inpt,int passedCommunicationTurn){
		
		String[] numbers = inpt.split(" ");
		int[] unitNos = new int[numbers.length-1];
		
		for(int u = 0; u < unitNos.length; u++){
			
			unitNos[u] = new Integer(numbers[u]).intValue();
		}
		
		int buildingNo = new Integer(numbers[numbers.length-1]).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.unitNos = unitNos;
		parameters.buildingNo = buildingNo;
		
		if(passedCommunicationTurn != -1){
			
			commands.add(MethodCallup.ATTACKBUILDING, parameters, communicationTurn);
		}else{
			
			commands.add(MethodCallup.ATTACKBUILDING,parameters,passedCommunicationTurn);
		}
	}
	
	public void parseMoveUnit(String inpt,int passedCommunicationTurn){
		
		ArrayList<String> numbers = 
				new ParseText(inpt).getNumbers();
		
		//System.out.println(numbers.get(0));
		//System.out.println("parseMoveUnit GameEngine");
		
		MethodParameter parameters = new MethodParameter();
		parameters.SetMoveUnit(new Integer(numbers.get(0)).intValue(),
				new Integer(numbers.get(1)).intValue(),
				new Integer(numbers.get(2)).intValue(),
				new Integer(numbers.get(3)).intValue());
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.MOVEUNIT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.MOVEUNIT, parameters, passedCommunicationTurn);
		}
	}
	
	public void parseAddWorkerToSite(String inpt, int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		String[] numbers = inpt.split(" ");
		
		int buildingNo = new Integer(numbers[numbers.length-1]).intValue();
		int[] unitNos = new int[numbers.length-1];
		
		for(int n = 0; n < numbers.length-1; n++){
			
			unitNos[n] = new Integer(numbers[n]).intValue();
		}
		
		MethodParameter parameters = new MethodParameter();
		parameters.setAddWorkerToSite(buildingNo,unitNos);
		
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.ADDWORKERTOSITE, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.ADDWORKERTOSITE, parameters, passedCommunicationTurn);
		}
	}

	public int getCommunicationTurnNo() {
		// TODO Auto-generated method stub
		return communicationTurn;
	}

	public void parseTowerAttackUnit(String inpt, int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		String[] parts = inpt.split(" ");
		int unitNo = new Integer(parts[0]).intValue();
		int buildingNo = new Integer(parts[1]).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.setTowerAttack(unitNo, buildingNo);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.TOWERATTACKUNIT, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.TOWERATTACKUNIT, parameters, passedCommunicationTurn);
		}
	}

	public void parseBuildWall(String inpt, int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		
		String[] parts = inpt.split("u");
		String[] wallInfo = parts[0].split(" ");
		String[] units = parts[1].split(" ");
		
		int playerNumber = new Integer(units[units.length-1]);
		int mapNo = new Integer(units[units.length-2]);
		
//		ArrayList<Point> wallSec = new ArrayList<Point>();
//		
//		for(int p = 0; p < points.length-1; p+=2){
//			
//			//System.out.println(points[p] + " " + points[p+1] + " GameEngine");
//			wallSec.add(new Point(new Integer(points[p]),new Integer(points[p+1])));
//		}
		
		int[] workers = new int[units.length-3];

		for(int w = 1; w < workers.length; w++){
			
			workers[w] = new Integer(units[w]);
		}
		
		MethodParameter parameters = new MethodParameter();
		parameters.setBuildWall(new Point(new Float(wallInfo[0]).intValue()
				,new Float(wallInfo[1]).intValue()),new Integer(wallInfo[2]).intValue(),workers,
				mapNo,playerNumber);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.BUILDWALLS, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.BUILDWALLS, parameters, passedCommunicationTurn);
		}
		
	}

	public void saveGame(String saveGame, int passedCommunicationTurn,int player) {
		// TODO Auto-generated method stub
		
		MethodParameter parameters = new MethodParameter();
		parameters.setSaveGame(saveGame,player,playerNo);
		
		if(passedCommunicationTurn != -1){
			commands.add(MethodCallup.SAVEGAME, parameters, communicationTurn);
		}else{
			commands.add(MethodCallup.SAVEGAME, parameters, passedCommunicationTurn);
		}
	}

	public void addAI(String inpt, int passedCommunicationTurn) {
		// TODO Auto-generated method stub
		//System.out.println(inpt + " GameEngine");
		String[] ais = inpt.split(":");
		for(int p = 0; p < ais.length; p++){
			
			System.out.println(ais[p] + " GameEngine");
		}
		//System.out.println(ais.length + " GameEngine");
		for(int a = 0; a < ais.length; a++){
			
			//System.out.println(ais[a] + " GameEngine");
			String[] aiInfo = ais[a].split(" ");
//			for(int i = 0; i < aiInfo.length; i++){
//				
//				System.out.println(aiInfo[i] + " GameEngine");
//			}
			MethodParameter parameters = new MethodParameter();
			parameters.setAddAI(new Integer(aiInfo[0]),new Long(aiInfo[1]),aiInfo[2]);
			
			if(passedCommunicationTurn != -1){
				commands.add(MethodCallup.ADDAI, parameters, communicationTurn);
			}else{
				commands.add(MethodCallup.ADDAI, parameters, passedCommunicationTurn);
			}
		}
		
	}
	
}
