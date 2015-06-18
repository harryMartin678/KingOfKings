package Player;

import java.util.ArrayList;

import Map.MapList;

public class PlayerList {
	
	private Player[] players;
	
	public PlayerList(int playerNo, int initialFood, int initialGold){
		
		players = new Player[playerNo];
		
		for(int i = 0; i < players.length; i++){
			
			players[i] = new Player(i,initialFood, initialGold);
		}
	}
	
	public void revealMap(int x, int y, int mapNo, int player, int size){
		
		players[player].showTiles(x, y, mapNo,size);
	}
	
	public void showPlayersMaps(MapList maps){
		
		for(int p = 0; p < players.length; p++){
			
			players[p].addMaps(maps);
		}
	}
	
	public int getSize(){
		
		return players.length;
	}
	
	public void addPlayerResource(int dFood, int dGold, int player){
		
		players[player].changeFood(dFood);
		players[player].changeGold(dGold);
	}

}
