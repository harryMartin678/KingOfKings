package Units;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Names;
import GameGraphics.IUnitList;
import Map.GameEngineCollisionMap;
import Util.Matrix;

public class UnitList implements Cloneable,IUnitList {
	
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
		
		unit.setUnitNo(units.size());
		units.add(unit);
		
		GameEngineCollisionMap.addUnit((int)unit.getX(), (int)unit.getY(), unit.getUnitNo(),
				unit.getMap(),unit.isWorker());
	}
	
//	public void addUnitToBoat(int unitNo, int boatNo){
//		
//		((Naval) units.get(boatNo)).addUnit(unitNo);
//	}
	
	public Unit getUnits(int id){
		
		return units.get(id);
	}
	
	public void attack(int unitNo,int ax, int ay){
		
		units.get(unitNo).attack(ax,ay);
	}
	
	public void stopAttack(int unitNo){
		
		units.get(unitNo).stopAttack(false);
	}
	
	public void setDeathReported(int unitNo){
		
		units.get(unitNo).reportedDeath();
	}
	
	public boolean hasDeathBeenReported(int unitNo){
		
		return units.get(unitNo).deathReported();
	}
	
	public void stopAttack(int unitNo,boolean delay){
		
		units.get(unitNo).stopAttack(delay);
	}
	
	public void stopRetreat(int unitNo){
		
		units.get(unitNo).setRetreat(false,false);
	}
	
	public void stopRetreat(int unitNo,boolean delay){
		
		units.get(unitNo).setRetreat(false,delay);
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
		
		if(unitType.equals(Names.ARCHER)){
			
			units.add(new Archer());
				
		}else if(unitType.equals(Names.SPEARMAN)){
			
			units.add(new Spearman());
			
		}else if(unitType.equals(Names.SWORDSMAN)){
			
			units.add(new Swordsman());
			
		}else if(unitType.equals(Names.WORKER)){
			
			units.add(new Worker());
		
		}else if(unitType.equals(Names.GIANT)){
			
			units.add(new Giant());
		
		}else if(unitType.equals(Names.HOUND)){
			
			units.add(new Hound());
		
		}
		
		units.get(units.size()-1).setPlayer(player);
		units.get(units.size()-1).setPos(x, y);
		units.get(units.size()-1).setMap(map);
		
		if(units.size() > 0){
		
			units.get(units.size()-1).setUnitNo(units.size()-1);
		}
		
		GameEngineCollisionMap.addUnit((int)x, (int)y, units.size()-1, map,
				units.get(units.size()-1).isWorker());
	}
	

	public void setFollow(int unitNo, int unitFollow){
		
		//System.out.println(unitNo + " " + this.getUnitName(unitNo) +
			//	" " + unitFollow + " Follow GameEngine");
		units.get(unitNo).setFollow(unitFollow);
	}
	
	public void unfollow(int unitNo){
		
		units.get(unitNo).stopFollow();
	}
	
	public int getFollow(int unitNo){
		
		return units.get(unitNo).getFollow();
	}
	
//	public void remove(int unitNo){
//		
//		this.units.remove(unitNo);
//	}
	
	public void addPathToUnit(int unitNo,ArrayList<int[]> path,boolean cutEndOff){
		
		if(cutEndOff){
			
			path.remove(path.size()-1);
		}
		
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
	
	public boolean collision(int one, int two,int range){
		
		return generalCollision(one,two,range);

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
	
	private boolean IsDead(int follow) {
		// TODO Auto-generated method stub
		return units.get(follow).dead();
	}

	public boolean getUnitStop(int unitNo){
		
		return units.get(unitNo).getStop();
	}

	public boolean getUnitMoving(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).getMoving();
	}
	
	public void printUnits(int map){
		
		for(int u = 0; u < units.size(); u++){
			
			if(units.get(u).getMap() == map){
				System.out.println(units.get(u).getUnitNo() + " " + units.get(u).getX() + " " 
								+ units.get(u).getY() + " " + units.get(u).getName());
			}
		}
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	public void cancelFollow(int unitFollow,int unitNo) {
		// TODO Auto-generated method stub
		units.get(unitFollow).unRegisterFollow(Math.round(units.get(unitNo).getX())
				,Math.round(units.get(unitNo).getY()));
	}

	public boolean getUnitIsIdle(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).idle();
	}

	public boolean isWorker(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).isWorker();
	}

	public boolean isRetreating(int unitFollow) {
		// TODO Auto-generated method stub
		return units.get(unitFollow).getRetreat();
	}

//	public ArrayList<int[]> areWorkersIdle(ArrayList<int[]> workerSites) {
//		// TODO Auto-generated method stub
//		for(int s = 0; s < workerSites.size(); s++){
//			
//			ArrayList<Integer> idleWorkers = new ArrayList<Integer>();
//			
//			idleWorkers.add(workerSites.get(s)[0]);
//			
//			for(int w = 1; w < workerSites.get(s).length; w++){
//				
//				if(this.getUnitIsIdle(workerSites.get(s)[w])){
//					
//					idleWorkers.add(workerSites.get(s)[w]);
//				}
//			}
//			
//			workerSites.remove(s);
//			workerSites.add(s,Matrix.ToIntArray(idleWorkers));
//		}
//		
//		return workerSites;
//	}

	public void addAll(ArrayList<Unit> units) {
		// TODO Auto-generated method stub
		for(int u = 0; u < units.size(); u++){
			
			this.addUnit(units.get(u));
		}
	}
	
	public String getUnitStates(){
		
		String line = ""; 
		
		for(int u = 0; u < this.getUnitListSize(); u++){
			
			line += u + " " + this.getUnitName(u) + " " + this.getUnitX(u) 
					+ " " + this.getUnitY(u) + " " + this.getUnitPlayer(u)
					+ " " + this.getUnitMap(u) + " " + this.getMoving(u)
					+ " " + this.getFollow(u) + " " + this.isAttacking(u)
					+ " " + this.getOrientation(u);
			
			for(int p = 1; p < this.getUnitPathSize(u); p++){
				
				line += " " + this.getUnitPathNode(u, p)[0] + " " + 
						this.getUnitPathNode(u, p)[1];
			}
			
			line += "\n";
		}
		
		return line;
	}

	public ArrayList<Integer> getWorkersOnMap(int mapNo,int playerNo) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = new ArrayList<Integer>();
		
		for(int u = 0; u < units.size(); u++){
			
			if(units.get(u).isWorker() && units.get(u).getMap() == mapNo
					&& units.get(u).getPlayer() == playerNo){
				
				workers.add(units.get(u).getUnitNo());
			}
		}
		
		return workers;
	}

	
	///public int[] getNextNode(int unitNo){
		
		
	//}

}
