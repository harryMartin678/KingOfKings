package GameServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Building;
import Buildings.BuildingDestruction;
import Buildings.BuildingSite;
import Player.Player;
import Units.Battle;
import Units.Unit;
import Units.Worker;

public class LoadGame {
	
	private ArrayList<Unit> units;
	private HashMap<Integer,Integer> UNoToIndex;
	private HashMap<Integer,Integer> BNoToIndex;
	private ArrayList<Building> buildings;
	private ArrayList<Battle> battles;
	private ArrayList<BuildingSite> sites;
	private ArrayList<Player> players;
	private ArrayList<BuildingDestruction> destruction;

	public LoadGame(String loadGame) throws IOException{
		
		File file = new File("SavedGames/" + loadGame + ".sav");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		units = new ArrayList<Unit>();
		UNoToIndex = new HashMap<Integer, Integer>();
		BNoToIndex = new HashMap<Integer, Integer>();
		buildings = new ArrayList<Building>();
		battles = new ArrayList<Battle>();
		sites = new ArrayList<BuildingSite>();
		players = new ArrayList<Player>();
		destruction = new ArrayList<BuildingDestruction>();
		
		loadUnits(reader);
		loadBuildings(reader);
		loadBattles(reader);
		loadSites(reader);
		loadDestruction(reader);
		loadPlayers(reader);
		
	}

	private void loadUnits(BufferedReader reader) throws IOException{
		
		boolean isUnits = false;
		
		String line;
		while(!(line = reader.readLine()).equals("b")){

			if(line.equals("u")){
				
				isUnits = true;
				continue;
			
			}else if(isUnits){
				
				units.add(parseUnitLine(line));
				UNoToIndex.put(units.get(units.size()-1).getUnitNo(), units.size()-1);
			}
		
		}
	}
	
	
	//add path, orientation
	private Unit parseUnitLine(String line){
		
		String[] info = line.split(" ");
		Unit unit = Unit.GetUnit(info[1].split(":")[1]);
		unit.setUnitNo(new Integer(info[0]).intValue());
		unit.setPos(new Float(info[2]).floatValue(), new Float(info[3]).floatValue());
		unit.setPlayer(new Integer(info[4]).intValue());
		unit.setMap(new Integer(info[5]).intValue());
		if(new Boolean(info[6]).booleanValue()){
			unit.move();
		}
		unit.setFollow(new Integer(info[7]).intValue());
		if(new Boolean(info[8]).booleanValue()){
			
			unit.attack(new Integer(info[9]).intValue());
		
		}else{
			
			unit.setAngle(new Integer(info[9]).intValue());
		}
		//unit.setRecalculate(new Boolean(info[10]).booleanValue());
		
		ArrayList<int[]> path = new ArrayList<int[]>();
		
		for(int p = 10; p < info.length; p+=2){
			
			path.add(new int[]{new Integer(info[p]).intValue(),
					new Integer(info[p+1]).intValue()});
		}
		
		if(path.size() > 0){
			
			unit.setPath(path);
		}
		
		return unit;
	}
	
	public ArrayList<Unit> getUnits(){
		
		return units;
	}
	
	private void loadBuildings(BufferedReader reader) throws IOException{
		
		String line;
		while(!(line = reader.readLine()).equals("bt")){
			
			buildings.add(parseBuildingLine(line));
			BNoToIndex.put(buildings.get(buildings.size()-1).getBuildingNo(),
					buildings.size()-1);
		}
	}
	
	
	//dieing?
	private Building parseBuildingLine(String line) {
		// TODO Auto-generated method stub
		String[] info = line.split(" ");
		
		Building building = GameGraphics.Building.GetBuildingClass(info[1]);
		
		building.setPos(new Float(info[2]).intValue(), new Float(info[3]).intValue());
		building.setMap(new Integer(info[4]).intValue());
		building.setPlayer(new Integer(info[5]).intValue());
		
		for(int i = 6; i < info.length; i+=2){
			
			building.loadQueueMember(info[i],new Integer(info[i+1]).intValue());
		}
		
		return building;
	}
	
	public ArrayList<Building> getBuildings(){
		
		System.out.println(buildings.size() + " LoadGame");
		return buildings;
	}
	
	private void loadBattles(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		
		String line;
		
		while(!(line = reader.readLine()).equals("bp")){
			
			battles.add(parseBattleLine(line));
		}
	}

//	battles.get(bt).getOneID() + " " + battles.get(bt).getTwoID() +
//	" " + battles.get(bt).getCount() + "\n";

	private Battle parseBattleLine(String line) {
		// TODO Auto-generated method stub
		String[] info = line.split(" ");

		return new Battle(units.get(UNoToIndex.get(new Integer(info[0]).intValue())),
				units.get(UNoToIndex.get(new Integer(info[1]).intValue())),new Integer(info[2]).intValue());
	}
	
	public ArrayList<Battle> getBattles(){
		
		return battles;
	}
	
	private void loadSites(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		
		String line;
		
		while(!(line = reader.readLine()).equals("bk")){
			
			sites.add(parseSite(line));
		}
	}

	private BuildingSite parseSite(String line) {
		// TODO Auto-generated method stub
		String[] info = line.split(" ");
		BuildingSite site = new BuildingSite(GameGraphics.Building.GetBuildingClass(info[0]),
				 new Integer(info[3]).intValue(),parseCreators(info,4));
		
		site.setPos(new Float(info[1]).floatValue(),new Float(info[2]).floatValue());
		
		return site;
	}

	private ArrayList<Worker> parseCreators(String[] info, int offset) {
		// TODO Auto-generated method stub
		ArrayList<Worker> creators = new ArrayList<Worker>();
		
		for(int c = offset; c < info.length; c++){
			
			creators.add((Worker)units.get(UNoToIndex.get(new Integer(info[c]).intValue())));
		}
		
		return creators;
	}
	
	public ArrayList<BuildingSite> getSites(){
		
		System.out.println(sites.size() + " LoadGame");
		return sites;
	}
	
	private void loadDestruction(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		String line;
		while(!(line = reader.readLine()).equals("p")){
			
			destruction.add(parseDestruction(line));
		}
	}
	
	private BuildingDestruction parseDestruction(String line) {
		// TODO Auto-generated method stub
		String[] info = line.split(" ");
		
		return new BuildingDestruction(buildings.get(BNoToIndex.get(new Integer(info[0]).intValue())),
				units.get(UNoToIndex.get(new Integer(info[1]).intValue())));
	}
	
	public ArrayList<BuildingDestruction> getDestructions(){
		
		return destruction;
	}

	private void loadPlayers(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		String line;
		
		while((line = reader.readLine()) != null){
			
			players.add(parsePlayer(line));
		}
	}

	private Player parsePlayer(String line) {
		// TODO Auto-generated method stub
		String[] info = line.split(" ");
		
		return new Player(new Integer(info[0]).intValue(),new Integer(info[1]).intValue(),
				new Integer(info[2]).intValue());
	}
	
	public ArrayList<Player> getPlayers(){
		
		return players;
	}
	
	
}
