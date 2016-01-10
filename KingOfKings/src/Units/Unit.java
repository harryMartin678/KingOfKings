package Units;

import java.util.ArrayList;

import GameClient.ParseText;

public class Unit {
	
	private float x;
	private float y;
	private int map;
	private int health;
	private int player;
	private ArrayList<int[]> path;
	private int angle;
	private boolean moving;
	private int follow;
	private int unitNo;
	private boolean isAttack;
	
	private float groupSpeed;
	private boolean stop;
	private int recalculate;
	
	public static float SPEED_CONSTANT = 100.0f;
	
	public Unit(){
		
		health = this.getMaxHealth();
		moving = false;
		follow = -1;
		groupSpeed = -1;
		stop = false;
		isAttack = false;
	}
	
	public void attack(int ax, int ay){
		
		this.setOrientation(this.x,this.y, ax, ay);
		isAttack = true;
	}
	
	public void stopAttack(){
		
		isAttack = false;
	}
	
	public boolean isAttacking(){
		
		return isAttack;
	}
	
	public int getUnitNo(){
		
		return unitNo;
	}
	
	public void setUnit(int unitNo){
		
		this.unitNo = unitNo;
	}
	
	public void stopFollow(){
		
		follow = -1;
	}
	
	public void follow(int unitNo, int followX, int followY, int followMap){
		
		follow = unitNo;
	}
	
	public int getFollow(){
		
		return follow;
	}
	
	public float getMoveUnitX(){
		
		if(path != null && path.size() > 2 && path.get(1)[1] != -1){
			
			return (float) path.get(1)[0];
		}else{
			
			return x;
		}
	}
	
	public float getMoveUnitY(){
		
		if(path != null && path.size() > 2 && path.get(1)[1] != -1){
			
			return (float) path.get(1)[1];
		}else{
			
			return y;
		}
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
		
		groupSpeed = -1;
		
		recalculate = 0;
		
		System.out.println("START////////");
		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
		}
		System.out.println("END//////");
		
	}
	
	public boolean dead(){
		
		return (health == 0);
	}
	
	public int getOrientation(){
		
		return angle;
	}
	
	
	//change the orientation of the unit
	private void setOrientation(float x, float y, float targetX, float targetY){
		
		if(targetX - x == 1 && targetY - y == 0){
			
			angle = 90;
			
		}else if(targetX - x == -1 && targetY - y == 0){
			
			angle = 270;
		
		}else if(targetX - x == 0 && targetY - y == 1){
			
			angle = 180;
		
		}else if(targetX - x == 0 && targetY - y == -1){
			
			angle = 0;
		
		}else if(targetX - x == 1 && targetY - y == -1){
			
			angle = 45;
		
		}else if(targetX - x == 1 && targetY - y == 1){
			
			angle = 315;
		
		}else if(targetX - x == -1 && targetY - y == 1){
			
			angle = 225;
		
		}else if(targetX - x == -1 && targetY - y == -1){
			
			angle = 135;
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
	
	public void setGroupSpeed(float groupSpeed){
		
		this.groupSpeed = groupSpeed;
	}
	
	public void setFollow(int unitNo){
		
		this.follow = unitNo;
	}
	
	//get the target it's moving to and don't recalculate
	public int[] getTarget(){
		
		if(path.size() == 0){
			
			return null;
		}else{
			
			if(recalculate != 0){
				
				recalculate = 0;
			}
			return new int[]{path.get(0)[0],path.get(0)[1],map};
		}
	}
	
	
	
	public int getRecalculate(){
		
		return recalculate;
	}
	
	
	
	//gets a unit to follow an path 
	public void followPath(ArrayList<Unit> units, int ownNo){
		
		//if the unit has moved to the final node then stop
		if(path.size() < 2){

			moving = false;
			//end of group movement if this is a group movement
			groupSpeed = -1;
			
		}else{

			stop = false;
			
			//if there is a unit in front of this unit
			for(int u = 0; u < units.size(); u++){
				
				//ignore itself
				if(u == ownNo){
					
					continue;
				}
				
				//don't worry if it's the destination
				if(path.size() == 0){
					
					moving = false;
					break;
					
				}
			
				//if there is a unit in front of it
				if(!units.get(u).dead() && path.get(1)[0] ==  ParseText.round(units.get(u).getX())
						&& path.get(1)[1] == ParseText.round(units.get(u).getY())){
					
					if(units.get(u).getMoving()){
						
						stop = true;
						
					}else{
						
						//if it's close to the destination then stop
						//if(path.size() == 2){
							
							//path.clear();
							//moving = false;
							
						//else find another route 
						//}else{
							
							if(u == follow){
								recalculate = 2;
							}else{
								recalculate = 1;
							}
						//}
						
						
					}
				}
			}
			
			//if the unit hasn't stopped then keep going 
			if(!stop){
			
				//move in the direction of the next node
				float vectorX = path.get(1)[0] - path.get(0)[0];
				float vectorY = path.get(1)[1] - path.get(0)[1];
				
				float speed;
				
				if(groupSpeed == -1){
					
					speed = ((float) this.getSpeed()/SPEED_CONSTANT);
					
				}else{
					speed = groupSpeed/SPEED_CONSTANT;
				}
				
				float tempX = x + vectorX * speed;
				float tempY = y + vectorY * speed;
				
				//if the unit is passed the node in either the x or y direction
				if(Math.abs(tempX-path.get(0)[0]) > Math.abs(path.get(1)[0]-path.get(0)[0])
						|| Math.abs(tempY-path.get(0)[1]) > 
								Math.abs(path.get(1)[1]-path.get(0)[1])){
					
					//then go to the next node 
					x = path.get(1)[0];
					y = path.get(1)[1];
						
					//remove the last node to repeat the process
					path.remove(0);
					
					//change the orientation
					if(path.size() > 1){
						setOrientation(path.get(0)[0],path.get(0)[1],
						path.get(1)[0],path.get(1)[1]);
						
						//if it's a transtion to another map
						if(path.get(1)[0] == -1){
							
							this.map = path.get(1)[1];
							
							path.remove(1);
							path.remove(0);
							
							x = path.get(0)[0];
							y = path.get(0)[1];
							
						}
					}

				}else{
	
					//else just move the unit according to velocity
					x = tempX;
					y = tempY;
					
					//change the orientation
					if(path.size() > 1){
						setOrientation(path.get(0)[0],path.get(0)[1],
								path.get(1)[0],path.get(1)[1]);
					}
				}
			}

		}
	}


	public boolean getStop() {
		// TODO Auto-generated method stub
		return stop;
	}
	

}
