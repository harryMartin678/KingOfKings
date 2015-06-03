package Units;

import java.util.ArrayList;

public class Unit {
	
	private float x;
	private float y;
	private int map;
	private int health;
	private int player;
	private ArrayList<int[]> path;
	private float angle;
	private boolean moving;
	
	public static float SPEED_CONSTANT = 50.0f;
	
	public Unit(){
		
		health = this.getMaxHealth();
		moving = false;
	}
	
	public int turnsPerAHit(){
		
		return 1;
	}
	
	public int getSizeX(){
		
		return 0;
	}
	
	public int getSizeY(){
		
		return 0;
	}
	
	public String getName(){
		
		return "Unit";
	}
	
	public boolean inUnit(int cx, int cy){
		
		return (Math.abs(x - cx) <= this.getSizeX()/2 && Math.abs(y - cy) <= this.getSizeY()/2);
	}
	
	
	public float getX(){
		
		return x;
	}
	
	public float getY(){
		
		return y;
	}
	
	public int getMap(){
		
		return map;
	}
	
	public void setMap(int map){
		
		this.map = map;
	}
	
	public void setPos(float x, float y){
		
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
	
	
	private void setOrientation(float x, float y, float targetX, float targetY){
		
		if(targetX - x == 1 && targetY - y == 0){
			
			angle = 0;
			
		}else if(targetX - x == -1 && targetY - y == 0){
			
			angle = 180;
		
		}else if(targetX - x == 0 && targetY - y == 1){
			
			angle = 270;
		
		}else if(targetX - x == 0 && targetY - y == -1){
			
			angle = 90;
		
		}else if(targetX - x == 1 && targetY - y == -1){
			
			angle = 45;
		
		}else if(targetX - x == 1 && targetY - y == 1){
			
			angle = 315;
		
		}else if(targetX - x == -1 && targetY - y == 1){
			
			angle = 225;
		
		}
	}
	
	public void move(){
		
		moving = true;
	}
	
	public boolean getMoving(){
		
		return moving;
	}
	
	public void followPath(){
		
		if(path.size() == 1){
			
			moving = false;
			
		}else{
			
			float vectorX = path.get(path.size()-2)[0] - path.get(path.size()-1)[0];
			float vectorY = path.get(path.size()-2)[1] - path.get(path.size()-1)[1];
			
			float tempX = x + vectorX * ((float) this.getSpeed()/SPEED_CONSTANT);
			float tempY = y + vectorY * ((float) this.getSpeed()/SPEED_CONSTANT);
			
			if(Math.abs(tempX-path.get(path.size()-1)[0]) > Math.abs(path.get(path.size()-2)[0]-path.get(path.size()-1)[0])
					|| Math.abs(tempY-path.get(path.size()-1)[1]) > 
							Math.abs(path.get(path.size()-2)[1]-path.get(path.size()-1)[1])){
				
				x = path.get(path.size()-2)[0];
				y = path.get(path.size()-2)[1];
				
				path.remove(path.size()-1);
				
				if(path.size() > 1){
					setOrientation(path.get(path.size()-1)[0],path.get(path.size()-1)[1],
							path.get(path.size()-2)[0],path.get(path.size()-2)[1]);
				}

			}else{

				x = tempX;
				y = tempY;
			}

		}
	}

}
