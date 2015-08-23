package Units;

import java.util.ArrayList;

public class UnitList {
	
	private ArrayList<Unit> units;
	
	public UnitList(){
		
		units = new ArrayList<Unit>();
	}
	
	public void addUnitToBoat(int unitNo, int boatNo){
		
		((Naval) units.get(boatNo)).addUnit(unitNo);
	}
	
	public String getUnitName(int unitNo){
		
		return units.get(unitNo).getName();
	}
	
	public boolean getMoving(int unitNo){
		
		return units.get(unitNo).getMoving();
	}
	
	public int getOrientation(int unitNo){
		
		return units.get(unitNo).getOrientation();
	}
	
	public int[] getKnownFollow(int unitNo){
		
		return units.get(unitNo).getKnownFollow();
	}
	
	public int getUnitPathSize(int unitNo){
		
		return units.get(unitNo).getPathSize();
	}
	
	public int[] getUnitPathNode(int unitNo, int index){
		
		return units.get(unitNo).getPath(index);
	}
	
	public void addUnit(String unitType,int map, float x, float y, int player){
	
		if(unitType.equals("archer")){
			
			units.add(new Archer());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
				
		}else if(unitType.equals("axeman")){
			
			units.add(new Axeman());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
			
		}else if(unitType.equals("batteringram")){
			
			units.add(new BatteringRam());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
		
		}else if(unitType.equals("lightchariot")){
			
			units.add(new LightChariot());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("heavychariot")){
			
			units.add(new HeavyChariot());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("heavyarcher")){
			
			units.add(new HeavyArcher());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("heavybatteringram")){
			
			units.add(new HeavyBatteringRam());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("servant")){
			
			units.add(new Servant());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("slave")){
			
			System.out.println("add slave");
			units.add(new Slave());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("spearman")){
			
			units.add(new Spearman());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType.equals("swordsman")){
			
			units.add(new Swordsman());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
		}
	}
	
	public void follow(int unitNo,int follow, int followX, int followY, int followMap){
		
		units.get(unitNo).follow(follow,followX, followY,followMap);
	}
	
	public boolean isFollowing(int unitNo){
		
		return (units.get(unitNo).getFollow() != -1);
	}
	
	public int getFollow(int unitNo){
		
		return units.get(unitNo).getFollow();
	}
	
	public boolean correctFollow(int unitNo){
		
		return units.get(unitNo).correctFollow((int) units.get(units.get(unitNo).getFollow()).getX(),
				(int) units.get(units.get(unitNo).getFollow()).getY(),
				units.get(units.get(unitNo).getFollow()).getMap());
	}
	
	public void addPathToUnit(int unitNo,ArrayList<int[]> path){
		
		units.get(unitNo).move();
		units.get(unitNo).setPath(path);
		
	}
	
	public int getUnitMap(int unitNo){
		
		return units.get(unitNo).getMap();
	}
	
	public float getUnitX(int unitNo){
		
		return units.get(unitNo).getX();
	}
	
	public float getUnitY(int unitNo){
		
		return units.get(unitNo).getY();
	}
	
	public void setUnitGroupSpeed(int[] unitNo, float groupSpeed){
		
		for(int u = 0; u < unitNo.length; u++){
			
			units.get(unitNo[u]).setGroupSpeed(groupSpeed);
		}
	}
	
	public ArrayList<int[]> getRecalculated(){
		
		ArrayList<int[]> recalculated = new ArrayList<int[]>();
		
		for(int u = 0; u < units.size(); u++){
			
			if(units.get(u).getRecalculate() && units.get(u).getTarget() != null){
				
				int[] target = units.get(u).getTarget();
				recalculated.add(new int[]{u,target[0],target[1],target[2]});
			}
		}
		
		return recalculated;
	}
	
	public float getSmallestSpeed(int unitNo[]){
		
		float smallestSpeed = Float.MAX_VALUE;
		
		for(int u = 0; u < unitNo.length; u++){
			
			if(units.get(unitNo[u]).getSpeed() < smallestSpeed){
				
				smallestSpeed = units.get(unitNo[u]).getSpeed();
			}
		}
		
		return smallestSpeed;
	}
	
	public boolean collision(int one, int two){
		
		if(units.get(one).getMap() != units.get(two).getMap()){
			
			return false;
		}
		
		Unit oneUn = units.get(one);
		Unit twoUn = units.get(two);
		
		float distance = 
				(float) Math.sqrt((Math.pow((oneUn.getX() - twoUn.getX()),2) + 
						Math.pow((oneUn.getY() - twoUn.getY()),2)));
		
		return (distance < 1);
		
		
		
		
	}
	
	public float getMoveUnitX(int unitNo){
		
		return units.get(unitNo).getMoveUnitX();
	}
	
	public float getMoveUnitY(int unitNo){
		
		return units.get(unitNo).getMoveUnitY();
	}
	
	public int getUnitPlayer(int unitNo){
		
		return units.get(unitNo).getPlayer();
	}
	
	public int getUnitListSize(){
		
		return units.size();
	}
	
	public boolean checkInUnit(int x, int y, int unitNo){
		
		return units.get(unitNo).inUnit(x, y);
	}

	public void moveUnits() {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < units.size(); u++){
			
			if(units.get(u).getMoving()){
				
				units.get(u).followPath(units,u);
			}
		}
	}
	
	public boolean getUnitStop(int unitNo){
		
		return units.get(unitNo).getStop();
	}

	public boolean getUnitMoving(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).getMoving();
	}
	
	///public int[] getNextNode(int unitNo){
		
		
	//}

}
