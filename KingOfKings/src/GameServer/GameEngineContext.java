package GameServer;

import AI.AIHandler;
import Buildings.BuildingAttackList;
import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.TowerBattles;
import IntermediateAI.Pathfinder;
import Map.MapList;
import Player.Diplomacy;
import Player.PlayerList;
import Units.UnitBattleList;
import Units.UnitList;

public class GameEngineContext {
	
	public MapList maps;
	public UnitList units;
	public BuildingList buildings;
	public PlayerList players;
	public Diplomacy dip;
	public Pathfinder pf;
	public BuildingProgress sites;
	public UnitBattleList battles;
	public SavedGame saveGame;
	public String gameName;
	public BuildingAttackList buildingAttackList;
	public AIHandler ais;
	//public TowerBattles towerBattles;

}
