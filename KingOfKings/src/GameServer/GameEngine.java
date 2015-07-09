package GameServer;

import java.io.IOException;
import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.Castle;
import Buildings.Farm;
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
	
	private long time;
	private int intialSize;
	private boolean shownTime = false;
	
	public GameEngine(String mapEntry,int playerNo){
		
		maps = new MapList(mapEntry);
		gameName = mapEntry;
		units = new UnitList();
		buildings = new BuildingList();
		players = new PlayerList(2,500,500);
		dip = new Diplomacy(playerNo);
		sites = new BuildingProgress();
		battles = new UnitBattleList();
		
		try {
			saveGame = new SavedGame("SavedGames/game1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		beat = 0;
		
		players.showPlayersMaps(maps);

		time = System.currentTimeMillis();
		
		
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
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)+5, (maps.getMapHeight(i)/2)+5, maps.getPlayer(i));
		}
		
		///this.setWayPoints(0, new int[]{0,15,15,15,2}, new int[]{0,15,15,15,15}, new int[]{2,4,3,4,0});
		
		new CollisionMap(buildings,units,maps.getMap(0));
		
		intialSize = buildings.getBuildingsSize();
		
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
		
		//System.out.println(units.getUnitX(5) + " " + units.getUnitY(5));
		
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
				
				if(units.isFollowing(f) && units.correctFollow(f)){
					
					this.follow(f, units.getFollow(f));
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
		
		for(int u = 0; u < units.getUnitListSize(); u++){
			
			if(units.getUnitMoving(u)){
				players.revealMap((int) units.getUnitX(u), (int) units.getUnitY(u), 
						units.getUnitMap(u), units.getUnitPlayer(u),1);
			}
		}
		
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(buildings.isBuildlingJustBuilt(b)){
				players.revealMap(buildings.getBuildingX(b), buildings.getBuildingY(b),
						buildings.getBuildingMap(b), buildings.getBuildingPlayer(b),
						buildings.getBuildingDiameterX(b));
			}
		}
	}
	
	public static void main(String[] args) {
		
		new GameEngine("game1",2);
	}

	@Override
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap) {
		// TODO Auto-generated method stub
		
		System.out.println(targetX + " " + targetY + " " + targetMap);
		System.out.println(units.getUnitMap(unitNo) + " " + units.getUnitX(unitNo) 
				+ " " + units.getUnitY(unitNo));
		//add a path to move to the unit 
		units.addPathToUnit(unitNo, 
				new MapRouteFinder(units, buildings, maps
				).getPath((int) units.getUnitX(unitNo),(int) units.getUnitY(unitNo),targetX, targetY,
						units.getUnitMap(unitNo),targetMap));

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
		
			totalPath.addAll(new MapRouteFinder(units,buildings,maps).getPath(
					pointsX[t],pointsY[t],
					pointsX[t+1],pointsY[t+1],mapsNo[t],mapsNo[t+1]));
				
		}
		
		//add the path to the unit 
		units.addPathToUnit(unitNo, totalPath);
	}
	
	@Override
	public void groupMovement(int[] unitNo, int targetX, int targetY, int targetMap) {
		// TODO Auto-generated method stub
		
		int unitRowSize = (int) Math.sqrt((double) unitNo.length);
		int row = 0;
		
		ArrayList<int[]> orgPath = new MapRouteFinder(units, buildings,maps
				).getPath((int) units.getUnitX(unitNo[0]), (int) units.getUnitY(unitNo[0]),
						targetX, targetY, units.getUnitMap(unitNo[0]), targetMap);
		
		for(int u = 0; u < unitNo.length; u++){
			
			if(row == unitRowSize){
				
				row = 0;
			}
			
			units.addPathToUnit(unitNo[u], new FormationMovement(maps,units,buildings
					).getPath(orgPath, row));
			
			row++;
		}
	}
	
	@Override
	public void follow(int firstUnit, int secondUnit) {
		// TODO Auto-generated method stub
		units.follow(firstUnit,secondUnit,(int) units.getUnitX(secondUnit)
				,(int) units.getUnitX(firstUnit), units.getUnitMap(firstUnit));
		
		this.moveUnit(firstUnit, (int) units.getUnitX(secondUnit), (int) units.getUnitX(firstUnit),
				units.getUnitMap(firstUnit));
	}

	@Override
	public void attackUnit(int unitNo, int targetNo) {
		// TODO Auto-generated method stub
		
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
	public void newViewMap(int mapNo) {
		// TODO Auto-generated method stub
		
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
				
				int moving = 0;
				
				if(units.getUnitMoving(u)){
					
					moving = 1;
				}
				
				info += u + " " + units.getUnitName(u) + " " + units.getUnitX(u) +
						" " + units.getUnitY(u) + " " + units.getUnitPlayer(u) + 
						" " + moving + " " + units.getOrientation(u) + "\n";
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

	
	
}
