package Player;

import java.util.ArrayList;

import Map.MapList;

public class PlayerList {
	
	private Player[] players;
	private Diplomacy dip;
	
	public PlayerList(int playerNo, int initialFood, int initialGold){
		
		players = new Player[playerNo];
		
		dip = new Diplomacy(playerNo);
		
		for(int i = 0; i < players.length; i++){
			
			players[i] = new Player(i,initialFood, initialGold);
		}
	}
	
	public boolean allied(int player1, int player2){
		
		return dip.allied(player1, player2);
	}
	
	public void setAllied(int player1, int player2){
		
		dip.alliance(player1, player2);
	}
	
	public void setAtWar(int player1, int player2){
		
		dip.atWar(player1, player2);
	}
	
	public void revealMap(int x, int y, int mapNo, int player, int size){
		
		players[player].showTiles(x, y, mapNo,size);
	}
	
	public void showPlayersMaps(MapList maps){
		
		for(int p = 0; p < players.length; p++){
			
			players[p].addMaps(maps);
		}
	}
	
	public int getPlayersGold(int playerNo){
		
		return players[playerNo].getGold();
	}
	
	public int getPlayersFood(int playerNo){
		
		return players[playerNo].getFood();
	}
	
	public int getSize(){
		
		return players.length;
	}
	
	public void addPlayerResource(int dFood, int dGold, int player){
		
		players[player].changeFood(dFood);
		players[player].changeGold(dGold);
	}

}
