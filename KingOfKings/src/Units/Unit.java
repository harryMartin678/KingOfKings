package Units;

import java.util.ArrayList;

public class Unit {
	
	private int x;
	private int y;
	private int map;
	private int health;
	private int player;
	private ArrayList<int[]> path;
	private boolean move;
	
	public Unit(){
		
		health = this.getMaxHealth();
		move = false;
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
	
	public int getMap(){
		
		return map;
	}
	
	public void setMap(int map){
		
		this.map = map;
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
	
	public int getPlayer(){
		
		return player;
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public void removeHealth(int hit){
		
		if(health > hit){
			health -= hit;
		}else{
			health = 0;
		}
	}
	
	public void setPath(ArrayList<int[]> path){
		
		this.path = path;
	}
	
	public void moving(){
		
		move = true;
	}
	
	public void stop(){
		
		move = false;
	
	}

}
