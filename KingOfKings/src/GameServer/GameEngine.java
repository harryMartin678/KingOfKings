package GameServer;

import java.util.ArrayList;

import Buildings.BuildingList;
import Map.Map;
import Map.MapList;
import Player.Diplomacy;
import Player.PlayerList;
import Units.UnitList;

public class GameEngine {
	
	private MapList maps;
	private UnitList units;
	private BuildingList buildings;
	private PlayerList players;
	private Diplomacy dip;
	
	public GameEngine(String mapEntry,int playerNo){
		
		maps = new MapList(mapEntry);
		units = new UnitList();
		buildings = new BuildingList();
		players = new PlayerList(2,500,500);
		dip = new Diplomacy(playerNo);
		
		for(int i = 0; i < maps.getSize(); i++){
			
			buildings.addBuilding(maps.getPlayer(i), i, 
					maps.getMapWidth(i)/2, maps.getMapHeight(i)/2, 0);
			buildings.addBuilding(maps.getPlayer(i), i, 
					(maps.getMapWidth(i)/2)+4, (maps.getMapHeight(i)/2)+4, 1);
			
			units.addUnit(0, i, (maps.getMapWidth(i)/2)-4, (maps.getMapHeight(i)/2)-4, maps.getPlayer(i));
			units.addUnit(0, i, (maps.getMapWidth(i)/2)-3, (maps.getMapHeight(i)/2)-3, maps.getPlayer(i));
			units.addUnit(0, i, (maps.getMapWidth(i)/2)+5, (maps.getMapHeight(i)/2)+5, maps.getPlayer(i));
		}
		
	}
	
	public static void main(String[] args) {
		
		new GameEngine("game1",2);
	}

}
