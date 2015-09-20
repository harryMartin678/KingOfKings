package GameServer;

import java.io.IOException;
import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.Castle;
import Buildings.Farm;
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

public class GameEngine implements Commands {
	
	private MapList maps;
	private UnitList units;
	private BuildingList buildings;
	private PlayerList players;
	private Diplomacy dip;
	private Pathfinder pf;
	private BuildingProgress sites;
	private UnitBattleList battles;
	private SavedGame saveGame;
	private String gameName;
	
	private int beat;

	
	public GameEngine(String mapEntry,int playerNo){
		
		maps = new MapList(mapEntry);
		gameName = mapEntry;
		units = new UnitList();
		buildings = new BuildingList();
		players = new PlayerList(2,500,500);
		dip = new Diplomacy(playerNo);
		sites = new BuildingProgress();
		battles = new UnitBattleList(units);
		
		try {
			saveGame = new SavedGame("SavedGames/game1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beat = 0;
		
		players.showPlayersMaps(maps);
		
		
		for(int i = 0; i < playerNo; i++){
			
			maps.getMap(i).setPlayer(i+1);
			players.setPlayerViewedMap(i,i);
		}
		
		//stub
		maps.getMap(1).setPlayer(0);
		players.setPlayerViewedMap(0, 1);
		
		
		for(int i = 0; i < maps.getSize(); i++){
			
			buildings.addBuilding(maps.getPlayer(i), i, 
					maps.getMapWidth(i)/2, maps.getMapHeight(i)/2, 0);
			buildings.addBuilding(maps.getPlayer(i), i, 
					(maps.getMapWidth(i)/2)+4, (maps.getMapHeight(i)/2)+4, 1);
			
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)-4, (maps.getMapHeight(i)/2)-4, maps.getPlayer(i));
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)-3, (maps.getMapHeight(i)/2)-3, maps.getPlayer(i));
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)+5, (maps.getMapHeight(i)/2)+5, 2);
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)-4, (maps.getMapHeight(i)/2)-2, maps.getPlayer(i));
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
		
		this.saveGame();
		
	}
	
	public void doOnFrame(){
		
		//move units
		units.moveUnits();
		
		//System.out.println(units.getUnitX(5) + " " + units.getUnitY(5) + " " 
	///	+ units.getUnitMap(5));
		
		//reveal map 
		revealMap();
		//progress unit fights
		battles.simulateHit();
		
		//progress unit to tower fights
		battles.simulateTowerHit();
		
		//increment building unit queues progress
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(!buildings.empty(b) && buildings.nextUnit(b)){
				
				addUnit(buildings.getFinishedUnit(b),buildings.getBuildingX(b),
						buildings.getBuildingY(b),buildings.getBuildingDiameterX(b),
						buildings.getBuildingDiameterY(b),buildings.getBuildingPlayer(b),
						buildings.getBuildingMap(b));
			}
		}
		
		ArrayList<int[]> unitsToRecalculate = units.getRecalculated();
		
		for(int r = 0; r < unitsToRecalculate.size(); r++){

			if(unitsToRecalculate.get(r)[1] == 1){
				this.moveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4]);
			}else{
				this.moveUnit(unitsToRecalculate.get(r)[0], unitsToRecalculate.get(r)[2],
						unitsToRecalculate.get(r)[3], unitsToRecalculate.get(r)[4],
						unitsToRecalculate.get(r)[5]);
			}
		}
		
		int[] com;
		
		while((com = battles.getUnitsToFollow()) != null){
			
			this.followUnit(com[0], com[1]);
		}
		
		//add resources from farms and mines 
		if(beat == 10){
			
			beat = 0;
			
			int[] playersGold = new int[players.getSize()];
			int[] playersFood = new int[players.getSize()];
			
			for(int b = 0; b < buildings.getBuildingsSize(); b++){

				if(buildings.getBuildingType(b).equals("mine")){
					
					playersGold[buildings.getBuildingPlayer(b)]++;
				
				}else if(buildings.getBuildingType(b).equals("farm")){
					
					playersFood[buildings.getBuildingPlayer(b)]++;
				}
			}
			
			
			for(int p = 0; p < playersFood.length; p++){
				
				players.addPlayerResource(playersFood[p], playersGold[p], p);
			}
			
			for(int f = 0; f < units.getUnitListSize(); f++){
				
				if(units.getFollow(f) != -1){
					
					int unitFollow = units.getFollow(f);
					
					if(units.getMoving(unitFollow)){
						
						System.out.println("Follow");
						this.moveUnit(f, (int) units.getUnitX(unitFollow), (int) units.getUnitY(unitFollow),
								units.getUnitMap(unitFollow),unitFollow);
						
					}
				}
			}
		}
		//increment building build progress 
		sites.checkSites(buildings);
		
		beat++;

	}
	
	private void addUnit(String unit, int x, int y, int sizeX, int sizeY, int player, int map){
		
		int[] pos = findSpace(x,y,sizeX,sizeY,map);
		
		//add check for no space for unit 
		
		units.addUnit(unit, map, pos[0],pos[1], player);
	}
	
	private int[] findSpace(int x, int y, int sizeX, int sizeY, int map){
		
		int[] lc = new int[]{sizeX+1,0,-sizeX-1,0,0,sizeY+1,0,-sizeY-1,
							 sizeX+1,sizeY+1,-sizeX-1,-sizeY-1,sizeX+1,-sizeY-1,
							 -sizeX-1,sizeY+1
		};
		
		for(int i = 0; i < lc.length; i+=2){
			
			if(new CollisionMap(buildings,units,
					maps.getMap(map)).getCollisionMap()[y+lc[i+1]][x+lc[i]] == 0){
				
				return new int[]{x+lc[i],y+lc[i+1]};
			}
		}
		
		return new int[]{-1,-1};
	}
	
	private void revealMap(){
		
		//for(int u = 0; u < units.getUnitListSize(); u++){
			
			//if(units.getUnitMoving(u)){
				//players.revealMap((int) units.getUnitX(u), (int) units.getUnitY(u), 
					//	units.getUnitMap(u), units.getUnitPlayer(u),1);
			//}
		//}
		
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(buildings.isBuildlingJustBuilt(b)){
				players.revealMap(buildings.getBuildingX(b), buildings.getBuildingY(b),
						buildings.getBuildingMap(b), buildings.getBuildingPlayer(b),
						buildings.getBuildingDiameterX(b));
			}
		}
	}
	

	@Override
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap) {
		// TODO Auto-generated method stub
		//add a path to move to the unit 
		
		units.unfollow(unitNo);
		units.addPathToUnit(unitNo, 
				new MapRouteFinder(units, buildings, maps
				).getPath((int) units.getMoveUnitX(unitNo),(int) units.getMoveUnitY(unitNo)
						,targetX, targetY,units.getUnitMap(unitNo),targetMap));

	}
	
	
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap,int ignoreUnit){
		// TODO Auto-generated method stub
		
		//add a path to move to the unit 
		units.addPathToUnit(unitNo, 
				new MapRouteFinder(units, buildings, maps,ignoreUnit
				).getPath((int) units.getMoveUnitX(unitNo),(int) units.getMoveUnitY(unitNo)
						,targetX, targetY,units.getUnitMap(unitNo),targetMap));

	}
	
	@Override
	public void groupFollow(int[] unitNo, int unitFollow) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			units.setFollow(unitNo[u], unitFollow);
			this.moveUnit(unitNo[u], (int) units.getUnitX(unitFollow), (int) units.getUnitY(unitFollow),
					(int) units.getUnitMap(unitFollow), unitFollow);
		}
		
	}
	
	@Override
	public void followUnit(int unitNo, int unitFollow) {
		// TODO Auto-generated method stub
		
		units.setFollow(unitNo,unitFollow);
		
		this.moveUnit(unitNo, (int) units.getUnitX(unitFollow), (int) units.getUnitY(unitFollow),
				units.getUnitMap(unitFollow),unitFollow);
		
	}
	
	
	

	@Override
	public void unitInBoat(int unitNo, int boatNo) {
		// TODO Auto-generated method stub
		units.addUnitToBoat(unitNo, boatNo);
	}

	@Override
	public void setWayPoints(int unitNo, int[] targetX, int[] targetY, int[] targetMap) {
		// TODO Auto-generated method stub
		
		ArrayList<int[]> totalPath = new ArrayList<int[]>();
		
		//get the x points in the way points path in an array
		int[] pointsX = new int[targetX.length+1];
		
		pointsX[0] = (int) units.getUnitX(unitNo);
		
		for(int i = 1; i < targetX.length+1; i++){
			
			pointsX[i] = targetX[i-1];
		}
		
		//get the y points in the way points path in an array
		int[] pointsY = new int[targetY.length+1];
		
		pointsY[0] = (int) units.getUnitY(unitNo);
		
		for(int i = 1; i < targetY.length+1; i++){
			
			pointsY[i] = targetY[i-1];
		}
		
		//get the maps to travel to in an array 
		int[] mapsNo = new int[targetMap.length+1];
		
		mapsNo[0] = units.getUnitMap(unitNo);
		
		for(int i = 1; i < mapsNo.length; i++){
			
			mapsNo[i] = targetMap[i-1];
		}

		
		//combine paths for each path
		for(int t = 0; t < mapsNo.length-1; t++){
		
			ArrayList<int[]> next = new MapRouteFinder(units,buildings,maps).getPath(
					pointsX[t],pointsY[t],
					pointsX[t+1],pointsY[t+1],mapsNo[t],mapsNo[t+1]);
			
			next.remove(next.size()-1);
			
			totalPath.addAll(next);
				
		}
		
		
		
		for(int p = 0; p < pointsX.length; p++){
			
			System.out.println(pointsX[p] + " " + pointsY[p] + " " + mapsNo[p]);
		}
		
		//add the path to the unit 
		units.addPathToUnit(unitNo, totalPath);
	}
	
	@Override
	public void groupMovement(int[] unitNo, int targetX, int targetY, int targetMap) {
		// TODO Auto-generated method stub
		
		//int unitRowSize = (int) Math.sqrt((double) unitNo.length);
		//int row = 0;
		
		//ArrayList<int[]> orgPath = new MapRouteFinder(units, buildings,maps
		//		).getPath((int) units.getUnitX(unitNo[0]), (int) units.getUnitY(unitNo[0]),
		//				targetX, targetY, units.getUnitMap(unitNo[0]), targetMap);
		//
	//	for(int u = 0; u < unitNo.length; u++){
			
			//if(row == unitRowSize){
				
			//	row = 0;
			//}
			
		//	units.addPathToUnit(unitNo[u], new FormationMovement(maps,units,buildings
				//	).getPath(orgPath, row));
			
			//row++;
		//}
		
		for(int u = 0; u < unitNo.length; u++){
			
			moveUnit(unitNo[u], targetX, targetY, targetMap);
		}
		
		units.setUnitGroupSpeed(unitNo, units.getSmallestSpeed(unitNo));
	}
	
	@Override
	public void groupWayPointsMovement(int[] unitNo, int[] targetX,
			int[] targetY, int[] targetMap) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			this.setWayPoints(unitNo[u], targetX, targetY, targetMap);
		}
		
		units.setUnitGroupSpeed(unitNo, units.getSmallestSpeed(unitNo));
		
	}

	

	@Override
	public void attackUnit(int unitNo, int targetUnit) {
		// TODO Auto-generated method stub
		this.followUnit(unitNo, targetUnit);
		battles.addBattle(unitNo, targetUnit);
	}
	
	@Override
	public void attackGroup(int[] unitNo, int targetUnit) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < unitNo.length; u++){
			
			this.followUnit(unitNo[u], targetUnit);
			battles.addBattle(unitNo[u], targetUnit);
		}
	}

	@Override
	public void attackBuilding(int unitNo, int buildingNo) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void buildBuilding(int x, int y, int buildingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUnitToBuildingQueue(int buildingNo, int unitType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUnitFromBuildingQueue(int buildingNo, int queueNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackUnitFromTower(int towerNo, int unitNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestedUnitInformation(int unitNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestedBuildingInformation(int buildingNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMapInformation(int mapNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGraphicalFramePos(int playerNo, int frameX, int frameY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyBuilding(int buildingNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newViewMap(int mapNo,int player) {
		// TODO Auto-generated method stub
		
		players.setPlayerViewedMap(player, mapNo);
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		try {
			saveGame.save(units, buildings, players, gameName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addMessageToChat(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alliance(int player1, int player2) {
		// TODO Auto-generated method stub
		players.setAllied(player1, player2);
	}

	@Override
	public void atWar(int player1, int player2) {
		// TODO Auto-generated method stub
		players.setAtWar(player1, player2);
	}
	
	//start game 
	
	public String getGameName(){
		
		return gameName;
	}
	
	public String getWhoOwnsWhatMap(){
		
		String mapInfo = "";
		
		for(int m = 0; m < maps.getSize(); m++){
			
			mapInfo += " " + m + " " + maps.getMap(m).getPlayer();
			
		}
		
		mapInfo = mapInfo.substring(1);
		
		return mapInfo;
	}

	public int getMapOwnedMap(int player){
		
		for(int m = 0; m < maps.getSize(); m++){
			
			if(maps.getMap(m).getPlayer() == player){
				
				return m;
			}
		}
		
		return -1;
	}
	
	public int getNumberOfMapRows(int map){
		
		return maps.getMapWidth(map);
	}
	
	public String getMapRow(int map, int row){
		
		int[] mapRow = maps.getMap(map).getRow(row);
		
		String ret = "";
		
		for(int m = 0; m < mapRow.length; m++){
			
			ret += mapRow[m];
		}
		
		return ret;
	}
	
	public String getUnitsOnMap(int map){
		
		String info = "";
		
		for(int u = 0; u < units.getUnitListSize(); u++){
			
			if(units.getUnitMap(u) == map){
				
				if(!units.getUnitDead(u)){
					
					int moving = 0;
					int attacking = 0;
					
					if(units.getUnitMoving(u) && !units.getUnitStop(u)){
						
						moving = 1;
					
					}
					
					if(units.isAttacking(u)){
						
						attacking = 1;
					}
					
					info += u + " " + units.getUnitName(u) + " " + units.getUnitX(u) +
							" " + units.getUnitY(u) + " " + units.getUnitPlayer(u) + 
							" " + moving + " " + units.getOrientation(u) + " " +
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
		
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(buildings.getBuildingMap(b) == map){
				
				info += b + " " + buildings.getBuildingType(b) + " " 
						+ buildings.getBuildingX(b) + " " +
						buildings.getBuildingY(b) + "\n";
			}
		}
		
		return info;
	}
	
	public int getPlayerViewedMap(int player){
		
		return players.getPlayerViewedMap(player);
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
	
		
		this.setWayPoints(unitNo,targetX, targetY, targetMap);
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
		
		
		this.groupMovement(unitNos, targetX, targetY, targetMap);
		
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
		
		
		this.groupWayPointsMovement(ParseText.toIntArray(unitNos),
				ParseText.toIntArray(targetX), ParseText.toIntArray(targetY), ParseText.toIntArray(targetMap));
		
	}
	
	public void parseFollowMovement(String inpt){
		
		ArrayList<String> numbers = new ParseText(inpt).getNumbers();
		
		int unitNo = new Integer(numbers.get(0)).intValue();
		int unitFollow = new Integer(numbers.get(1)).intValue();
		this.followUnit(unitNo, unitFollow);
	}
	
	public void parseGroupFollowMovement(String inpt){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt.substring(0, inpt.length()-1));
		
		this.groupFollow(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1))
				,unitNos.get(unitNos.size()-1));
	}
	
	public void parseGroupAttack(String inpt){
		
		ArrayList<Integer> unitNos = getUnitNos(inpt);
		
		this.attackGroup(ParseText.toIntArray(unitNos.subList(0, unitNos.size()-1)),
				unitNos.get(unitNos.size()-1));
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
		
		this.attackUnit(unitNo, unitAttack);
	}


	
}
