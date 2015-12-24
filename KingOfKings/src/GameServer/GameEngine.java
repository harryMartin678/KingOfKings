package GameServer;

import java.io.IOException;
import java.util.ArrayList;

import Buildings.Building;
import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.Castle;
import Buildings.Farm;
import Buildings.Names;
import GameClient.ParseText;
import IntermediateAI.FormationMovement;
import IntermediateAI.MapRouteFinder;
import IntermediateAI.Pathfinder;
import Map.CollisionMap;
import Map.Map;
import Map.MapList;
import Player.Diplomacy;
import Player.PlayerList;
import Units.Archer;
import Units.Unit;
import Units.UnitBattleList;
import Units.UnitList;
import Units.Worker;


//composition:
//
public class GameEngine{
	
	
	private GameEngineContext context;
	private int beat;
	private UserCommandList commands;
	private int communicationTurn;
	
	public GameEngine(String mapEntry,int playerNo){
		
		context = new GameEngineContext();
		commands = new UserCommandList(context);
		
		communicationTurn = 0;
		
		context.maps = new MapList(mapEntry);
		context.gameName = mapEntry;
		context.units = new UnitList();
		context.buildings = new BuildingList();
		context.players = new PlayerList(2,500,500);
		context.dip = new Diplomacy(playerNo);
		context.sites = new BuildingProgress();
		context.battles = new UnitBattleList(context.units);
		
		try {
			context.saveGame = new SavedGame("SavedGames/game1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beat = 0;
		
		context.players.showPlayersMaps(context.maps);
		
		
		for(int i = 0; i < playerNo; i++){
			
			context.maps.getMap(i).setPlayer(i+1);
			context.players.setPlayerViewedMap(i,i);
		}
		
		//stub
		context.maps.getMap(1).setPlayer(0);
		context.players.setPlayerViewedMap(0, 1);
		
		
		for(int i = 0; i < context.maps.getSize(); i++){
			
			context.buildings.addBuilding(context.maps.getPlayer(i), i, 
					context.maps.getMapWidth(i)/2, context.maps.getMapHeight(i)/2, 0);
			context.buildings.addBuilding(context.maps.getPlayer(i), i, 
					(context.maps.getMapWidth(i)/2)+4, (context.maps.getMapHeight(i)/2)+4, 1);
			
			context.units.addUnit(Names.SLAVE, i, (context.maps.getMapWidth(i)/2)-4, (context.maps.getMapHeight(i)/2)-4, context.maps.getPlayer(i));
			context.units.addUnit(Names.SLAVE, i, (context.maps.getMapWidth(i)/2)-3, (context.maps.getMapHeight(i)/2)-3, context.maps.getPlayer(i));
			context.units.addUnit(Names.SLAVE, i, (context.maps.getMapWidth(i)/2)+5, (context.maps.getMapHeight(i)/2)+5, 2);
			context.units.addUnit(Names.SLAVE, i, (context.maps.getMapWidth(i)/2)-4, (context.maps.getMapHeight(i)/2)-2, context.maps.getPlayer(i));
		}

		
		Thread onFrame = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					doOnFrame();
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		});
		
		onFrame.start();
		
		commands.add(MethodCallup.SAVEGAME, new MethodParameter(), communicationTurn);
		//this.saveGame();
		
	}
	
	private void CommunicationTurn(){
		
		commands.callMethods(communicationTurn);
		
		communicationTurn++;
	}
	
	public void doOnFrame(){
		
		if(beat == 5 || beat == 10){
			
			CommunicationTurn();
		}
		//move units
		context.units.moveUnits();
		
		//System.out.println(units.getUnitX(5) + " " + units.getUnitY(5) + " " 
	///	+ units.getUnitMap(5));
		
		//reveal map 
		//revealMap();
		//progress unit fights
		context.battles.simulateHit();
		
		//progress unit to tower fights
		context.battles.simulateTowerHit();
		
		//increment building unit queues progress
		for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
			
			if(!context.buildings.empty(b) && context.buildings.nextUnit(b)){
				
				addUnit(context.buildings.getFinishedUnit(b),context.buildings.getBuilding(b));
			}
		}
		
		ArrayList<int[]> unitsToRecalculate = context.units.getRecalculated();
		
		for(int r = 0; r < unitsToRecalculate.size(); r++){

			if(unitsToRecalculate.get(r)[1] == 1){
				
				MethodParameter parameters = new MethodParameter();
				parameters.SetMoveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4]);
				
				commands.add(MethodCallup.MOVEUNIT,parameters, communicationTurn);
//				this.moveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
//						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4]);
			}else{
				
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
		
		int[] com;
		
		while((com = context.battles.getUnitsToFollow()) != null){
			
			MethodParameter parameters = new MethodParameter();
			parameters.setUnitFollow(com[0], com[1]);
			commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
			//this.followUnit(com[0], com[1]);
		}
		
		//add resources from farms and mines 
		if(beat == 10){
			
			beat = 0;
			
			int[] playersGold = new int[context.players.getSize()];
			int[] playersFood = new int[context.players.getSize()];
			
			for(int b = 0; b < context.buildings.getBuildingsSize(); b++){

				if(context.buildings.getBuildingType(b).equals("mine")){
					
					playersGold[context.buildings.getBuildingPlayer(b)]++;
				
				}else if(context.buildings.getBuildingType(b).equals("farm")){
					
					playersFood[context.buildings.getBuildingPlayer(b)]++;
				}
			}
			
			
			for(int p = 0; p < playersFood.length; p++){
				
				context.players.addPlayerResource(playersFood[p], playersGold[p], p);
			}
			
			for(int f = 0; f < context.units.getUnitListSize(); f++){
				
				if(context.units.getFollow(f) != -1){
					
					int unitFollow = context.units.getFollow(f);
					
					if(context.units.getMoving(unitFollow)){
						
						MethodParameter parameters = new MethodParameter();
						parameters.SetMoveUnit(f, (int) context.units.getUnitX(unitFollow),
								(int) context.units.getUnitY(unitFollow), context.units.getUnitMap(unitFollow),
								unitFollow);
						commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
//						this.moveUnit(f, (int) context.units.getUnitX(unitFollow), (int) context.units.getUnitY(unitFollow),
//								context.units.getUnitMap(unitFollow),unitFollow);
						
					}
				}
			}
		}
		//increment building build progress 
		context.sites.checkSites(context.buildings);
		
		beat++;

	}
	
	
	private void addUnit(String unit, Building building){
		
		int[] pos = building.getFreeSpace(new CollisionMap(context.buildings,context.units,
				context.maps.getMap(building.getMap())), building.getX() + building.getSizeX()+1, 
					building.getY() + building.getSizeY() + 1, new ArrayList<int[]>());
		
		System.out.println(unit + " " + pos[0] + " " + pos[1]);
		//add check for no space for unit 
		
		context.units.addUnit(unit, building.getMap(), pos[0],pos[1], building.getPlayer());
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
				context.players.revealMap(context.buildings.getBuildingX(b), context.buildings.getBuildingY(b),
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
							" " + moving + " " + context.units.getOrientation(u) + " " +
							 attacking + "\n";
					
					
				}else{
					
					info += u + " die\n";
				}
			}
		}
		
		return info;
	}
	
	public String getBuildingOnMap(int map){
		
		String info = "";
		
		for(int b = 0; b < context.buildings.getBuildingsSize(); b++){
			
			if(context.buildings.getBuildingMap(b) == map){
				
				info += b + " " + context.buildings.getBuildingType(b) + " " 
						+ context.buildings.getBuildingX(b) + " " +
						context.buildings.getBuildingY(b) +  " " +
						context.buildings.getBuildingPlayer(b) + "\n";
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
	
	public void parseWayPoints(String input, int player){
		
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
	
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitWayPoints(unitNo, targetX, targetX, targetMap);
		
		commands.add(MethodCallup.SETWAYPOINTS, parameters, communicationTurn);
		//this.setWayPoints(unitNo,targetX, targetY, targetMap);
	}
	
	public void parseGroupMovement(String inpt){
		
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
		
		commands.add(MethodCallup.GROUPMOVEMENT, parameters, communicationTurn);
		//this.groupMovement(unitNos, targetX, targetY, targetMap);
		
	}
	
	public void parseGroupWayMovement(String inpt){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		ArrayList<Integer> unitNos = new ArrayList<Integer>();
		ArrayList<Integer> targetX = new ArrayList<Integer>();
		ArrayList<Integer> targetY = new ArrayList<Integer>();
		ArrayList<Integer> targetMap = new ArrayList<Integer>();
		
		int n = 0;
		
		while(new Integer(numbers.get(n)).intValue() != -1){
			
			System.out.println(new Integer(numbers.get(n)).intValue());
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
		commands.add(MethodCallup.GROUPWAYPOINTSMOVEMENT, parameters, communicationTurn);
//		this.groupWayPointsMovement(ParseText.toIntArray(unitNos),
//				ParseText.toIntArray(targetX), ParseText.toIntArray(targetY), ParseText.toIntArray(targetMap));
		
	}
	
	public void parseFollowMovement(String inpt){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		
		int unitNo = new Integer(numbers.get(0)).intValue();
		int unitFollow = new Integer(numbers.get(1)).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitFollow(unitNo, unitFollow);
		
		commands.add(MethodCallup.FOLLOWUNIT, parameters, communicationTurn);
		//this.followUnit(unitNo, unitFollow);
	}
	
	public void parseGroupFollowMovement(String inpt){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt.substring(0, inpt.length()-1));
		
		MethodParameter parameters = new MethodParameter();
		parameters.setGroupFollow(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
				unitNos.get(unitNos.size()-1));
		
		commands.add(MethodCallup.GROUPFOLLOW, parameters, communicationTurn);
		
//		this.groupFollow(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1))
//				,unitNos.get(unitNos.size()-1));
	}
	
	public void parseGroupAttack(String inpt){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt);
		
		MethodParameter parameters = new MethodParameter();
		parameters.setAttackGroup(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
				unitNos.get(unitNos.size()-1));
		
		commands.add(MethodCallup.ATTACKGROUP, parameters, communicationTurn);
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
	
	public void parseAttack(String inpt){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		int unitNo = new Integer(numbers.get(0)).intValue();
		int unitAttack = new Integer(numbers.get(1)).intValue();
		
		MethodParameter parameters = new MethodParameter();
		parameters.setUnitAttack(unitNo, unitAttack);
		
		commands.add(MethodCallup.ATTACKUNIT, parameters, communicationTurn);
		//this.attackUnit(unitNo, unitAttack);
	}
	
	public void parseBuildings(String inpt, int player){
		
		ParseText text = new ParseText(inpt);
		ArrayList<String> numbers = text.getNumbers();
		String name = text.getUnitName();
		
		int[] unitNos = new int[numbers.size()-2];
		
//		for(int i = 0; i < numbers.size(); i++){
//			
//			System.out.println(numbers.get(i));
//		}
		
		for(int n = 3; n < numbers.size(); n++){
			
			if(numbers.get(n).length() > 0){
				unitNos[n-3] = new Integer(numbers.get(n)).intValue();
			}
		}
		
		MethodParameter parameters = new MethodParameter();
		parameters.setBuildBuilding(new Float(numbers.get(0)).intValue(), new Float(numbers.get(1)).intValue(),
				new Float(numbers.get(2)).intValue(),player,name,unitNos);
		
		commands.add(MethodCallup.BUILDBUILDING, parameters, communicationTurn);
//		this.buildBuilding(new Float(numbers.get(0)).intValue(), new Float(numbers.get(1)).intValue(),
//				new Float(numbers.get(2)).intValue(),player,name,unitNos);
//		
		
	}

	public void parseAddUnitToQueue(String inpt, int p) {
		// TODO Auto-generated method stub
		ParseText text = new ParseText(inpt);
		ArrayList<String> numbers = text.getNumbers();
		String unitType = text.getUnitName();
		
		System.out.println("enter add unit to queue " + unitType);
		
		MethodParameter parameters = new MethodParameter();
		parameters.setAddUnitToBuildQueue(new Integer(numbers.get(0)).intValue(), unitType);
		
		commands.add(MethodCallup.ADDUNITTOBUILDINGQUEUE, parameters, communicationTurn);
		//this.addUnitToBuildingQueue(new Integer(numbers.get(0)).intValue(), unitType);
		
	}

	public void setNewViewMap(int mapNo, int player) {
		// TODO Auto-generated method stub
		MethodParameter parameters = new MethodParameter();
		parameters.setNewViewMap(mapNo, player);
		
		commands.add(MethodCallup.NEWVIEWMAP, parameters, communicationTurn);
	}
	
	public void parseMoveUnit(String inpt){
		
		ArrayList<String> numbers = 
				new ParseText(inpt).getNumbers();
		
		System.out.println(numbers.get(0));
		
		MethodParameter parameters = new MethodParameter();
		parameters.SetMoveUnit(new Integer(numbers.get(0)).intValue(),
				new Integer(numbers.get(1)).intValue(),
				new Integer(numbers.get(2)).intValue(),
				new Integer(numbers.get(3)).intValue());
		
		commands.add(MethodCallup.MOVEUNIT, parameters, communicationTurn);
	}


	
}
