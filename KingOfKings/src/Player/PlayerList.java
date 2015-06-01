package Player;

import java.util.ArrayList;

public class PlayerList {
	
	private Player[] players;
	
	public PlayerList(int playerNo, int initialFood, int initialGold){
		
		players = new Player[playerNo];
		
		for(int i = 0; i < players.length; i++){
			
			players[i] = new Player(i,initialFood, initialGold);
		}
	}

}
