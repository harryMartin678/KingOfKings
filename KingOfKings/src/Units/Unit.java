package Units;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Mine;
import Buildings.Names;
import GameServer.AddUnitModule;
import Map.GameEngineCollisionMap;

public class Unit {
	
	private float x;
	private float y;
	private int map;
	private int health;
	private int player;
	private ArrayList<float[]> path;
	private int angle;
	private boolean moving;
	private int follow;
	private int unitNo;
	private boolean isAttack;
	private int delayAttack;
	private int delayRetreat;
	private boolean deathReported;
	
	//private boolean changed;
	
	private float groupSpeed;
	private boolean stop;
	private int recalculate;
	
	private boolean retreat;
	
	private AddUnitModule addUnit;
	private HashMap<Integer,int[]> isTaken;
	
	public static float SPEED_CONSTANT = 200.0f;
	
	public Unit(){
		
		health = this.getMaxHealth();
		path = new ArrayList<float[]>();
		isTaken = new HashMap<Integer,int[]>();
		addUnit = new AddUnitModule();
		moving = false;
		follow = -1;
		groupSpeed = -1;
		stop = false;
		isAttack = false;
		retreat = false;
		delayAttack = 0;
		delayRetreat = 0;
	}
	
//	public boolean IsChanged(){
//		
//		return changed;
//	}
//	
//	public void SetUnChanged(){
//		
//		changed = false;
//	}
//	
//	private void SetChanged(){
//		
//		changed = true;
//	}
	
	
	public boolean idle(){
		
		return !isAttack && !moving && !dead();
	}
	
	public void attack(int angle){
		
		this.angle = angle;
		isAttack = true;
	}
	
	public void attack(int ax, int ay){
		
		//if(delayAttack == 10){
			//System.out.println(x + " " + y + " " + ax + " " + ay + " Unit");
			this.setOrientation(this.x,this.y, ax, ay);
			//System.out.println(this.x + " " + this.y + " " + ax + " " + ay + " Unit");
			isAttack = true;
		//	delayAttack = 0;
		//}else{
		//	delayAttack++;
		//}
		
	}
	
	public int[] getFreeSpace(float attackX,float attackY){
		
		//this shouldn't look like this
		//or maybe it should
		Mine hack = new Mine(0);
		//if(path.size() <= 1){
			hack.setPos(Math.round(this.x), Math.round(this.y));
//		}else{
//			hack.setPos((int)path.get(1)[0], (int)path.get(1)[1]);
//		}
		addUnit.setBuilding(hack);
		ArrayList<int[]> takenList = new ArrayList<int[]>(isTaken.values());
		
//		if(path.size() > 1){
//			
//			takenList.add(new int[]{(int)path.get(1)[0],(int)path.get(1)[1]});
//		}
		
		int[] pos = addUnit.getFreeSpace(map, Math.round(attackX), Math.round(attackY), 
				takenList);
		isTaken.put(unitNo, pos);
		
//		float nextstepx = attackX;
//		float nextstepy = attackY;
//		
//		if(path.size() > 1){
//			
//			nextstepx = path.get(1)[0];
//			nextstepy = path.get(1)[1];
//		}
//		
//		//system.out.println(pos[0] + " " + pos[1] + " unit");
//		System.out.println("attacker pos: " + this.x + " " + this.y + " victim space: " + hack.getX() 
//				+ " " + hack.getY()+ " next step: " + nextstepx + " " + nextstepy + " position: " 
//				+ pos[0] + " " + pos[1] + " " + this.getName());
		return pos;
	}
	
	public void unRegisterFollow(int unitNo){
		
		isTaken.remove(unitNo);
	}
	
	public void stopAttack(boolean delay){
		if(delayAttack == 5 || !delay){
			isAttack = false;
			delayAttack = 0;
		}else{
			delayAttack++;
		}
	}
	
	public boolean isAttacking(){
		
		return isAttack;
	}
	
	public int getUnitNo(){
		
		return unitNo;
	}
	
	public void setRetreat(boolean retreat,boolean delay){
		if(delayRetreat == 5 || !delay){
			//System.out.println(retreat + " " + this.getName() + " retreat Unit");
			this.retreat = retreat;
			delayRetreat = 0;
		}else{
			delayRetreat ++;
		}
	}
	
	public boolean getRetreat(){
		
		return retreat;
	}
	
	public void setUnitNo(int unitNo){
		
		this.unitNo = unitNo;
	}
	
	public void stopFollow(){
		
		follow = -1;
		//System.out.println("STOP FOLLOW " + this.getName() + " Unit");
	}
	
	public void follow(int unitNo, int followX, int followY, int followMap){
		
		follow = unitNo;
	}
	
	public int getFollow(){
		
		return follow;
	}
	
	public float getMoveUnitX(){
		
		if(path != null && path.size() > 2 && path.get(1)[1] != -1){
			
			return path.get(1)[0];
		}else{
			
			return x;
		}
	}
	
	public float getMoveUnitY(){
		
		if(path != null && path.size() > 2 && path.get(1)[1] != -1){
			
			return path.get(1)[1];
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
	
	public boolean exitBattle(){
		
		return follow == -1 && path.size() > 0 && !isAttacking();
	}
	
	public void removeHealth(int hit){
		
		//System.out.println(unitNo + " unitNo " + health + " remove health unit");
		if(!dead()){
			if(health > hit){
				health -= hit;
			}else{
				health = 0;
			}
			
			if(dead()){
				
				GameEngineCollisionMap.removeUnit(this.unitNo, this.map);
			}
		}
	}
	
	public void setPath(ArrayList<int[]> path){
		
		if(this.follow == -1){
			
			isAttack = false;
		}
		
		this.path.clear();
		this.path.add(new float[]{x,y});
		for(int p = 1; p < path.size(); p++){
			
			this.path.add(new float[]{path.get(p)[0],path.get(p)[1]});
		}
		
		groupSpeed = -1;
		
		recalculate = 0;
		
		isTaken.clear();
		
//		System.out.println("START//////// " + this.getName());
//		for(int i = 0; i < path.size(); i++){
//			
//			System.out.println(this.path.get(i)[0] + " " + this.path.get(i)[1]);
//		}
//		System.out.println("END//////");
		
	}
	
	public boolean dead(){
		
		return (health == 0);
	}
	
	public int getOrientation(){
		
		return angle;
	}
	
	
	//change the orientation of the unit
	private void setOrientation(float x, float y, float targetX, float targetY){
		
//		if(targetX - x == 1 && targetY - y == 0){
//			
//			angle = 90;
//			
//		}else if(targetX - x == -1 && targetY - y == 0){
//			
//			angle = 270;
//		
//		}else if(targetX - x == 0 && targetY - y == 1){
//			
//			angle = 180;
//		
//		}else if(targetX - x == 0 && targetY - y == -1){
//			
//			angle = 0;
//		
//		}else if(targetX - x == 1 && targetY - y == -1){
//			
//			angle = 45;
//		
//		}else if(targetX - x == 1 && targetY - y == 1){
//			
//			angle = 315;
//		
//		}else if(targetX - x == -1 && targetY - y == 1){
//			
//			angle = 225;
//		
//		}else if(targetX - x == -1 && targetY - y == -1){
//			
//			angle = 135;
//		}
		
		//System.out.println((targetX - x ) + " " + (targetY - y) +  " in unit");
		//SetChanged();
		if(targetX - x > 0 && targetY - y == 0){
			
			//System.out.println("90 unit");
			angle = 90;
			
		}else if(targetX - x < 0 && targetY - y == 0){
			
			//System.out.println("270 unit");
			angle = 270;
		
		}else if(targetX - x == 0 && targetY - y > 0){
			
			//System.out.println("180 unit");
			angle = 180;
		
		}else if(targetX - x == 0 && targetY - y < 0){
			
			//System.out.println("0 unit");
			angle = 0;
		
		}else if(targetX - x > 0 && targetY - y < 0){
			
			//System.out.println("45 unit");
			angle = 45;
		
		}else if(targetX - x > 0 && targetY - y > 0){
			
			//System.out.println("135 unit");
			angle = 135;
		
		}else if(targetX - x < 0 && targetY - y > 0){
			
			//System.out.println("225 unit");
			angle = 225;
		
		}else if(targetX - x < 0 && targetY - y < 0){
			
			//System.out.println("135 unit");
			angle = 315;
		}
	}
	
	public void move(){
		
		//SetChanged();
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
		
		return new int[]{(int)path.get(index)[0],(int)path.get(index)[1]};
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
			//return new int[]{(int)path.get(0)[0],(int)path.get(0)[1],map};
			return new int[]{(int)path.get(path.size()-1)[0],
					(int)path.get(path.size()-1)[1],map};
		}
	}
	

	public int getRecalculate(){
		
		return recalculate;
	}
	
	//gets a unit to follow an path 
	public void followPath(ArrayList<Unit> units, int ownNo){
		
		if(!retreat){
			//if the unit has moved to the final node then stop
			if(path.size() < 2){
	
				moving = false;
				//end of group movement if this is a group movement
				groupSpeed = -1;
				
			}else if(path.size() == 0){
				
				moving = false;
				
			}else{
	
				stop = false;
				
				int isUnitInFront = GameEngineCollisionMap.IsUnitInFront(ownNo,
						new int[]{ ((int)path.get(1)[0] - (int)path.get(0)[0]),
								 ((int)path.get(1)[1] - (int)path.get(0)[1])},map);
				
				if(isUnitInFront != -1 && !units.get(isUnitInFront).dead()){
					
					//System.out.println(isUnitInFront + " IsUnitInFront Unit");
//					if(isUnitInFront == ownNo){
//						
//						System.out.println(((path.get(1)[0] - path.get(0)[0])) + " " +
//								(path.get(1)[1] - path.get(0)[1]) + " IsOwn Unit");
//						System.out.println(path.get(1)[0] + " " + path.get(0)[0] + " xs Unit");
//						System.out.println(path.get(1)[1] + " " + path.get(0)[1] + " ys Unit");
//					}
					//System.out.println(isUnitInFront + " " + ownNo + " InFront Unit");
					if(units.get(isUnitInFront).getMoving()){
						
						stop = true;
						
					}else{
						
						if(isUnitInFront == follow){
							recalculate = 2;
						}else{
							recalculate = 1;
						}
						
					}
				}
				
				//this needs to use the spatial map, very INEFFICIENT 
				//if there is a unit in front of this unit
//				for(int u = 0; u < units.size(); u++){
//					
//					//ignore itself
//					if(u == ownNo){
//						
//						continue;
//					}
//					
//					//don't worry if it's the destination
//					if(path.size() == 0){
//						
//						moving = false;
//						break;
//						
//					}
//					
//					//if there is a unit in front of it
//					if(!units.get(u).dead() && path.get(1)[0] ==  ParseText.round(units.get(u).getX())
//							&& path.get(1)[1] == ParseText.round(units.get(u).getY())){
//						//System.out.println("unit stop " + this.getUnitNo() + " unit");
//						if(units.get(u).getMoving()){
//
//							stop = true;
//							
//						}else{
//							
//							//if it's close to the destination then stop
//							//if(path.size() == 2){
//								
//								//path.clear();
//								//moving = false;
//								
//							//else find another route 
//							//}else{
//								
//								if(u == follow){
//									recalculate = 2;
//								}else{
//									recalculate = 1;
//								}
//							//}
//							
//							
//						}
//					}
//				}
				
				//if the unit hasn't stopped then keep going 
				if(!stop){
				
					//move in the direction of the next node
					float vectorX = path.get(1)[0] - path.get(0)[0];
					float vectorY = path.get(1)[1] - path.get(0)[1];
					
					//System.out.println(vectorX + " " + vectorY + " Unit");
					
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
								
								this.map = (int)path.get(1)[1];
								
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
			
		if(!stop || retreat){
			GameEngineCollisionMap.moveUnit(ownNo, Math.round(x), Math.round(y), map,isWorker());
		}
	}
	
	//CollisionMap map,
	public void Retreat(float rx,float ry){
		
		//int[][] areaCanWalk = map.getCollisionMap();
		float[] directions = new float[]{0,1,0,-1,1,0,-1,0,-1,-1,1,-1,-1,1,1,1};
		
		int directionIndex = -1;
		float distance = Float.MIN_VALUE;
		
		for(int d = 0; d < directions.length; d+=2){
			
			float tempDist = (float) Math.sqrt(Math.abs(((int)this.getX() + (int)directions[d])-rx)
					 + Math.abs(((int)this.getY() + (int)directions[d+1]) - ry));
			
			int nx = (int)this.getX() + (int)directions[d];
			int ny = (int)this.getY() + (int)directions[d+1];
			
			if(nx >= 0 && ny >= 0 && nx < GameEngineCollisionMap.getSizeX(this.map) &&
					ny < GameEngineCollisionMap.getSizeY(this.map) &&
					GameEngineCollisionMap.getTile(nx,ny,this.map) == 0
					&& tempDist > distance){
				
				distance = tempDist;
				directionIndex = d;
				
			}
		}
		
		if(directionIndex != -1){
			
			float speed = ((float) this.getSpeed()/SPEED_CONSTANT);
			
			x = x + (directions[directionIndex] * speed);
			y = y + (directions[directionIndex+1] * speed);
			
			retreat = true;
		}
		
	}
	
	public int goldNeeded(){
		
		return 0;
	}
	
	public int foodNeeded(){
		
		return 0;
	}


	public boolean getStop() {
		// TODO Auto-generated method stub
		return stop;
	}
	
	public static Unit GetUnit(String name){
		
		if(Names.ARCHER.equals(name)){
			
			return new Archer();
			
		}else if(Names.SPEARMAN.equals(name)){
			
			return new Spearman();
			
		}else if(Names.SWORDSMAN.equals(name)){
			
			return new Swordsman();
			
		}else if(Names.GIANT.equals(name)){
			
			return new Giant();
		
		}else if(Names.HOUND.equals(name)){
			
			return new Hound();
		
		}else if(Names.WORKER.equals(name)){
			
			return new Worker();
		}
		
		return new Unit();
	}

	public void chase(float targetX, float targetY) {
		// TODO Auto-generated method stub
		
		this.setOrientation(x, y, targetX, targetY);
		float directX = targetX - x;
		float directY = targetY - y;
		float speed = ((float) this.getSpeed()/SPEED_CONSTANT);

			
		float vectorX = (float) (directX * (speed / Math.sqrt(Math.pow(directX,2) + Math.pow(directY,2))));
		float vectorY = (float) (directY * (speed / Math.sqrt(Math.pow(directX,2) + Math.pow(directY,2))));
		
		//float limitX = x + (directX / )
		
//		if(Math.abs((x + vectorX) - targetX) > 1 ||
//				Math.abs((y + vectorY) - targetY) > 1){
//			
//			
//		}

	}

	public boolean isWorker() {
		// TODO Auto-generated method stub
		return this.getName().equals("Unit:"+Names.WORKER);
	}

	public void reportedDeath() {
		// TODO Auto-generated method stub
		deathReported = true;
	}

	public boolean deathReported() {
		// TODO Auto-generated method stub
		return deathReported;
	}

	public void setAngle(int angle) {
		// TODO Auto-generated method stub
		this.angle = angle;
	}

	public void setRecalculate(boolean recaluate) {
		// TODO Auto-generated method stub
		this.recalculate = recalculate;
	}
	

}
