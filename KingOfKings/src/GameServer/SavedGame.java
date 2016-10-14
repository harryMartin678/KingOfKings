package GameServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Buildings.Building;
import Buildings.BuildingList;
import Player.PlayerList;
import Units.UnitList;

public class SavedGame {
	
	private File file;
	private BufferedWriter writer;
	
	public SavedGame() throws IOException{
		
		
		
	
	}
	
	public void save(GameEngineContext context,String filename,
			int playerNo,int noOfPlayers) 
			throws IOException{
		
		System.out.println("SavedGames/" + filename+".sav" + " SavedGame");
		file = new File("SavedGames/" + filename+".sav");
		writer = new BufferedWriter(new FileWriter(file));
		writer.write(filename + "\n");
		writer.write(playerNo + "\n");
		writer.write(noOfPlayers + "\n");
		writer.write("u\n");
		writer.write(context.units.getUnitStates());
		
		writer.write("b\n");
		writer.write(context.buildings.getBuildingStates());
		
		writer.write("bt\n");
		writer.write(context.battles.getBattleStates());
		
		writer.write("bp\n");
		writer.write(context.sites.getSiteStates());
		
		writer.write("bk\n");
		writer.write(context.buildingAttackList.getBuildAttackStates());
		
		writer.write("p\n");
		writer.write(context.players.getPlayerStates());
		
		writer.close();
	}

}
