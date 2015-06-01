package Player;

public class Player {
	
	private int food;
	private int gold;
	private int playerNo;
	private int frameX;
	private int frameY;
	
	public Player(int playerNo, int food, int gold){
		
		this.playerNo = playerNo;
		this.food = food;
		this.gold = gold;
	}
	
	public void setFramePos(int frameX, int frameY){
		
		this.frameX = frameX;
		this.frameY = frameY;
	}
	
	public int getFrameX(){
		
		return frameX;
	}
	
	public int getFrameY(){
		
		return frameY;
	}
	
	public int getFood(){
		
		return food;
	}
	
	public int getGold(){
		
		return gold;
	}
	
	public void changeFood(int dFood){
		
		this.food += dFood;
	}
	
	public void changeGold(int dGold){
		
		this.gold += dGold;
	}
	
	public int getPlayerNo(){
		
		return playerNo;
	}

}
