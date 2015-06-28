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
	private int follow;
	private int followX;
	private int followY;
	private int followMap;
	
	public static float SPEED_CONSTANT = 50.0f;
	
	public Unit(){
		
		health = this.getMaxHealth();
		moving = false;
		follow = -1;
	}
	
	public void stopFollow(){
		
		follow = -1;
	}
	
	public void follow(int unitNo, int followX, int followY, int followMap){
		
		follow = unitNo;
		this.followX = followX;
		this.followY = followY;
		this.followMap = followMap;
	}
	
	public int getFollow(){
		
		return follow;
	}
	
	public boolean correctFollow(int followX, int followY, int followMap){
		
		return (this.follow == -1 || (this.followX == followX && this.followY == followY
							&& this.followMap == followMap));
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
		
		/*System.out.println("START////////");
		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
		}
		System.out.println("END//////");*/
		
	}
	
	public boolean dead(){
		
		return (health == 0);
	}
	
	
	//change the orientation of the unit
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
	
	public int getPathSize(){
		
		if(path == null){
			
			return 0;
		}
		
		return path.size();
	}
	
	public int[] getPath(int index){
		
		return path.get(index);
	}
	
	public int[] getKnownFollow(){
		
		return new int[]{followX, followY, followMap};
	}
	
	//gets a unit to follow an path 
	public void followPath(){
		
		//if the unit has moved to the final node then stop
		if(path.size() == 1){
			
			moving = false;
			
		}else{
			
			//move in the direction of the next node
			float vectorX = path.get(1)[0] - path.get(0)[0];
			float vectorY = path.get(1)[1] - path.get(0)[1];
			
			float tempX = x + vectorX * ((float) this.getSpeed()/SPEED_CONSTANT);
			float tempY = y + vectorY * ((float) this.getSpeed()/SPEED_CONSTANT);
			
			//if the unit is passed the node in either the x or y direction
			if(Math.abs(tempX-path.get(0)[0]) > Math.abs(path.get(1)[0]-path.get(0)[0])
					|| Math.abs(tempY-path.get(0)[1]) > 
							Math.abs(path.get(1)[1]-path.get(0)[1])){
				
				//if it's a transtion to another map
				if(path.get(1)[0] == -1){
					
					this.map = path.get(1)[1];
					path.remove(1);
				}
				
				//then go to the next node 
				x = path.get(1)[0];
				y = path.get(1)[1];
				
				//remove the last node to repeat the process
				path.remove(0);
				
				//change the orientation
				if(path.size() > 1){
					setOrientation(path.get(0)[0],path.get(0)[1],
							path.get(1)[0],path.get(1)[1]);
				}

			}else{

				//else just move the unit according to velocity
				x = tempX;
				y = tempY;
			}

		}
	}

}
