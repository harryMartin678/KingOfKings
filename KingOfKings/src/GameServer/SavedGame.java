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
	
	public SavedGame(String filename) throws IOException{
		
		file = new File(filename+".sav");
		
	
	}
	
	public void save(UnitList units, BuildingList buildings,PlayerList players,String gameName) throws IOException{
		
		writer = new BufferedWriter(new FileWriter(file));
		writer.write(gameName + "\n");
		writer.write("u\n");
		
		for(int u = 0; u < units.getUnitListSize(); u++){
			
			String line = u + " " + units.getUnitName(u) + " " + units.getUnitX(u) 
					+ " " + units.getUnitY(u) + " " + units.getUnitPlayer(u)
					+ " " + units.getUnitMap(u) + " " + units.getMoving(u)
					+ " " + units.getFollow(u) + " " + units.getKnownFollow(u)[0]
					+ " " + units.getKnownFollow(u)[1] + " " + units.getKnownFollow(u)[2];
			
			for(int p = 0; p < units.getUnitPathSize(u); p++){
				
				line = line + " " + units.getUnitPathNode(u, p)[0] + " " + units.getUnitPathNode(u, p)[1];
			}
			
			writer.write(line + "\n");
		}
		
		writer.write("b\n");
		
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			 String line = b + " " + buildings.getBuildingType(b) + " " + buildings.getBuildingX(b)
					 + " " + buildings.getBuildingY(b) + " " + buildings.getBuildingMap(b);
			 
			 for(int q = 0; q < buildings.getUnitQueueSize(b); q++){
				 
				 line = line + " " + buildings.getUnitQueueItem(b, q)[0] + " "
						 + buildings.getUnitQueueItem(b, q)[1];
			 }
			 
			 writer.write(line + "\n");
		}
		
		writer.write("p\n");
		
		for(int p = 0; p < players.getSize(); p++){
			
			String line = p + " " + players.getPlayersFood(p) + " " + players.getPlayersGold(p);
			writer.write(line + "\n");
		}
		
		
		writer.close();
	}

}
