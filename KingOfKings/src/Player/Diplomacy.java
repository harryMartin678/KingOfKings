package Player;

public class Diplomacy {
	
	private boolean[][] diplomacy;
	
	public Diplomacy(int playerNo){
		
		diplomacy = new boolean[playerNo][playerNo-1];
		
	}
	
	public void alliance(int player1, int player2){
		
		diplomacy[player1][player2-1] = true;
		diplomacy[player2][player1-1] = true;
	}
	
	public void atWar(int player1, int player2){
		
		diplomacy[player1][player2-1] = false;
		diplomacy[player2][player1-1] = false;
	}
	
	public boolean allied(int player1,int player2){
		
		return diplomacy[player1][player2-1];
	}
	
	

}
