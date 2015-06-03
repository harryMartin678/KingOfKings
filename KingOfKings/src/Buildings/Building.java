package Buildings;

public class Building {
	
	private int x;
	private int y;
	private int hitpoints;
	private int player;
	private int map;
	
	
	public Building(){
		
		hitpoints = this.getMaxHitpoint();
	}
	
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	public int getMaxHitpoint(){
		
		return 0;
	}
	
	public int getHitpoints(){
		
		return hitpoints;
	}
	
	public int getMap(){
		
		return map;
	}
	
	public void setMap(int map){
		
		this.map = map;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public void removeHitpoints(int amount){
		
		hitpoints -= amount;
	}
	
	public int getSizeX(){
		
		return 0;
	}
	
	public int getSizeY(){
		
		return 0;
	}
	
	public boolean inBuilding(int cx, int cy){
		
		return (Math.abs(x-cx) <= this.getSizeX()/2 && Math.abs(y-cy) <= this.getSizeY()/2);
	}
	
	public void setPos(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	

}
