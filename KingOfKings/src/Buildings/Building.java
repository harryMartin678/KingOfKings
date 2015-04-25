package Buildings;

public class Building {
	
	private int x;
	private int y;
	private int hitpoints;
	
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
	
	public void removeHitpoints(int amount){
		
		hitpoints -= amount;
	}
	
	public void setPos(int x, int y){
		
		this.x = x;
		this.y = y;
	}

}
