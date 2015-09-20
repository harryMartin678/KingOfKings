package Units;

import java.util.ArrayList;

public class UnitList implements Cloneable {
	
	private ArrayList<Unit> units;
	
	public UnitList(){
		
		units = new ArrayList<Unit>();
	}
	
	@Override
	protected Object clone() {
		// TODO Auto-generated method stub
		UnitList unitsNew = new UnitList();
		
		for(int u = 0; u < this.getUnitListSize(); u++){
			
			unitsNew.addUnit(units.get(u));
		}
		
		return unitsNew;
	}
	
	private void addUnit(Unit unit){
		
		unit.setUnit(units.size());
		units.add(unit);
	}
	
	public void addUnitToBoat(int unitNo, int boatNo){
		
		((Naval) units.get(boatNo)).addUnit(unitNo);
	}
	
	public Unit getUnits(int id){
		
		return units.get(id);
	}
	
	public void attack(int unitNo,int ax, int ay){
		
		units.get(unitNo).attack(ax,ay);
	}
	
	public void stopAttack(int unitNo){
		
		units.get(unitNo).stopAttack();
	}
	
	public boolean isAttacking(int unitNo){
		
		return units.get(unitNo).isAttacking();
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
		
		units.get(units.size()-1).setUnit(units.size()-1);
	}
	

	public void setFollow(int unitNo, int unitFollow){
		
		units.get(unitNo).setFollow(unitFollow);
	}
	
	public void unfollow(int unitNo){
		
		units.get(unitNo).stopFollow();
	}
	
	public int getFollow(int unitNo){
		
		return units.get(unitNo).getFollow();
	}
	
	public void remove(int unitNo){
		
		this.units.remove(unitNo);
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
			
			if(units.get(u).getRecalculate() != 0 && units.get(u).getTarget() != null){
				
				int[] target = units.get(u).getTarget();
				recalculated.add(new int[]{u,units.get(u).getRecalculate(),target[0],
						target[1],target[2],units.get(u).getFollow()});
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
		
		return generalCollision(one,two,1.0f);

		
	}
	
	public boolean getStopped(int unitNo){
		
		return units.get(unitNo).getStop();
	}
	
	private boolean generalCollision(int one, int two,float reqDist){
		
		if(units.get(one).getMap() != units.get(two).getMap()){
			
			return false;
		}
		
		Unit oneUn = units.get(one);
		Unit twoUn = units.get(two);
		
		/*float distance = 
				Math.abs(((float) Math.sqrt((Math.pow((oneUn.getX() - twoUn.getX()),2) + 
						Math.pow((oneUn.getY() - twoUn.getY()),2)))));*/
		float distance = (float) Math.max(Math.abs(oneUn.getX() - twoUn.getX()), 
				Math.abs(oneUn.getY() - twoUn.getY()));
		
		return (distance <= reqDist);
	}
	
	public boolean getUnitDead(int unitNo){
		
		return units.get(unitNo).dead();
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
