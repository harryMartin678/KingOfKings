package GameServer;

import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.Castle;
import Buildings.Farm;
import IntermediateAI.MapPathFinder;
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
	
	private int beat;
	
	private long time;
	private int intialSize;
	private boolean shownTime = false;
	
	public GameEngine(String mapEntry,int playerNo){
		
		maps = new MapList(mapEntry);
		units = new UnitList();
		buildings = new BuildingList();
		players = new PlayerList(2,500,500);
		dip = new Diplomacy(playerNo);
		sites = new BuildingProgress();
		battles = new UnitBattleList();
		
		beat = 0;
		
		players.showPlayersMaps(maps);

		time = System.currentTimeMillis();
		
		Worker creator = new Worker();
		Worker one = new Worker();
		one.build(0);
		creator.build(0);
		sites.addSite(new Farm(0),creator);
		sites.addWorker(one);
		
		
		for(int i = 0; i < maps.getSize(); i++){
			
			buildings.addBuilding(maps.getPlayer(i), i, 
					maps.getMapWidth(i)/2, maps.getMapHeight(i)/2, 0);
			buildings.addBuilding(maps.getPlayer(i), i, 
					(maps.getMapWidth(i)/2)+4, (maps.getMapHeight(i)/2)+4, 1);
			
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)-4, (maps.getMapHeight(i)/2)-4, maps.getPlayer(i));
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)-3, (maps.getMapHeight(i)/2)-3, maps.getPlayer(i));
			units.addUnit("slave", i, (maps.getMapWidth(i)/2)+5, (maps.getMapHeight(i)/2)+5, maps.getPlayer(i));
		}
		
		this.moveUnit(0, 15, 15,4);
		
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
		
	}
	
	public void doOnFrame(){
		
		//move units
		units.moveUnits();
	
		//System.out.println(units.getUnitX(0) +" " + units.getUnitY(0) + " " + units.getUnitMap(0));
		
		//reveal map 
		revealMap();
		//progress unit fights
		battles.simulateHit();
		
		//progress unit to tower fights
		battles.simulateTowerHit();
		
		//increment building unit queues progress
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(buildings.nextUnit(b)){
				
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
		
		//add a path to move to the unit 
		units.addPathToUnit(unitNo, 
				new MapPathFinder(maps,units,buildings).getPath(
						(int) units.getUnitX(unitNo),(int) units.getUnitY(unitNo),
						units.getUnitMap(unitNo)+1,targetX,targetY,targetMap,0));

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
		
		for(int t = 0; t < targetMap.length; t++){
			
			totalPath.addAll(new MapPathFinder(maps,units,buildings).getPath(
						(int) units.getUnitX(unitNo),(int) units.getUnitY(unitNo),
						units.getUnitMap(unitNo)+1,targetX[t],targetY[t],targetMap[t],0));
		}
		
		units.addPathToUnit(unitNo, totalPath);
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
		
	}

	@Override
	public void addMessageToChat(String message) {
		// TODO Auto-generated method stub
		
	}
	
}
