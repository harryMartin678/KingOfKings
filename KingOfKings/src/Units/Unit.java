package Units;

public class Unit {
	
	private int x;
	private int y;
	private int health;
	
	public Unit(){
		
		health = this.getMaxHealth();
	}
	
	public int turnsPerAHit(){
		
		return 1;
	}
	
	public String getName(){
		
		return "Unit";
	}
	
	public void setX(int x){
		
		this.x = x;
	}
	
	public void setY(int y){
		
		this.y = y;
	}
	
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	public void setPos(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	public int getMaxHealth(){
		
		return 0;
	}
	
	public int getSpeed(){
		
		return 0;
	}
	
	public int getAttack(){
		
		return 0;
	}
	
	public int getDefence(){
		
		return 0;
	}
	
	public int getProductivity(){
		
		return 0;
	}
	
	public int getRange(){
		
		return 1;
	}
	
	public int getHealth(){
		
		return health;
	}
	
	public void removeHealth(int hit){
		
		if(health > hit){
			health -= hit;
		}else{
			health = 0;
		}
	}

}
