package GameServer;

import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.Castle;
import Buildings.Farm;
import IntermediateAI.Pathfinder;
import Map.CollisionMap;
import Map.Map;
import Map.MapList;
import Player.Diplomacy;
import Player.PlayerList;
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
			
			units.addUnit(0, i, (maps.getMapWidth(i)/2)-4, (maps.getMapHeight(i)/2)-4, maps.getPlayer(i));
			units.addUnit(0, i, (maps.getMapWidth(i)/2)-3, (maps.getMapHeight(i)/2)-3, maps.getPlayer(i));
			units.addUnit(0, i, (maps.getMapWidth(i)/2)+5, (maps.getMapHeight(i)/2)+5, maps.getPlayer(i));
		}
		
		this.moveUnit(0, 15, 18);
		
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
	
		//progress unit fights
		
		//progress unit to tower fights
		
		//increment building unit queues progress
		
		//add resources from farms and mines 
		
		//increment building build progress 
		sites.checkSites(buildings);
		
		if(buildings.getBuildingsSize() > intialSize && !shownTime){
			
			System.out.println(System.currentTimeMillis() - time);
			shownTime = true;
		}
	}
	
	public static void main(String[] args) {
		
		new GameEngine("game1",2);
	}

	@Override
	public void moveUnit(int unitNo, int targetX, int targetY) {
		// TODO Auto-generated method stub
		units.addPathToUnit(unitNo, 
				new Pathfinder(new CollisionMap(buildings,units,
						maps.getMap(units.getUnitMap(unitNo))).getCollisionMap()).getPath(
						(int) units.getUnitX(unitNo),(int) units.getUnitY(unitNo),targetX,targetY));
	}

	@Override
	public void unitInBoat(int unitNo, int boatNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWayPoints(int unitNo, ArrayList<int[]> positions) {
		// TODO Auto-generated method stub
		
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
