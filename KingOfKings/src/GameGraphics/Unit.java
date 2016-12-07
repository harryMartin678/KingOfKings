package GameGraphics;

import java.util.ArrayList;

import Buildings.Names;
import GameGraphics.Unit.GraphicalState;
import GameGraphics.GameScreenComposition.IComUnitList;

public class Unit {
	
	private float x;
	private float y;
	private String unitType;
	private boolean moving;
	private boolean delayedMoving;
	private boolean delayedFiring;
	private int currentFrame;
	private boolean forward;
	private boolean firing;
	private boolean dieing;
	private int finishedDieing;
	private int player;
	private int unitNo;
	private int angle;
	private int attackingUnit;
	public static ArrayList<Integer> MovingUnits = new ArrayList<Integer>();
	
	public static void addUnit(Unit unit){
		
		MovingUnits.add(unit.getUnitNo());
	}
	
	public static void removeUnit(Unit unit){
		
		MovingUnits.remove(new Integer(unit.getUnitNo()));
	}
	
	public static void ClearAnimateUnits() {
		// TODO Auto-generated method stub
		MovingUnits.clear();
	}
	
	public static void AnimateUnits(IComUnitList units){
		
		units.begin();
		for(int m = 0; m < MovingUnits.size(); m++){
			
			Unit unit = units.getUnitByUnitNo(MovingUnits.get(m));
			
			if(unit != null){
				
				unit.changeCurrentFrame();
			}

		}
		units.end();
	}
	
	public Unit(float x, float y,String unitType, int player, int unitNo){
		
		this.x = x;
		this.y = y;
		currentFrame = 0;
		forward = true;
		this.unitType = unitType;
		this.player = player;
		this.unitNo = unitNo;
		this.attackingUnit = -1;

	}
	
	public boolean fireArrow(){
		
		return (firing && unitType.equals(Names.ARCHER));
	}
	
	public void setAngle(int angle){
		
		this.angle = angle;
	}
	
	public int getAngle(){
		
		return angle;
	}
	
	public int getUnitNo(){
		
		return unitNo;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public void setFiring(){
		
		if(!firing){
			
			addUnit(this);
			firing = true;
		}
		
	}
	
	public void stopFiring(){
		
		if(firing){
			
			this.StopAnimating();
		}
		firing = false;
		//currentFrame = 0;
		
	}
	
	public void die(){
		
		if(!dieing){
			
			addUnit(this);
			dieing = true;
		}
		//this.Animating();
		//state = 2;
	}
	
	public boolean getDieingFinished(){
		
		return finishedDieing == 5;
	}
	
//	public int getState(){
//		
//		return state;
//	}
	
	public void setDelayedMoving(){
		
		delayedMoving = true;
	}
	
	public void setDelayedFiring(){
		
		delayedFiring = true;
	}
	
	public void moving(){

		if(!moving){
			
			Unit.addUnit(this);
			moving = true;
		}
	}
	
	public boolean getMove(){
		
		return moving;
	}
	
	public void stopMoving(){
		
		if(moving){
			this.StopAnimating();
		}
		moving = false;
	}
	
	public void setAttack(int unitToAttack){
		
		this.attackingUnit = unitToAttack;
	}
	
	public int getAttacking(){
		
		return this.attackingUnit;
	}
	

	
	private void StopAnimating(){
		
		currentFrame = 0;
		Unit.removeUnit(this);
	}
	
	public boolean inUnit(int x, int y){
		
		return (((int)this.x) - x) == 0 && (((int)this.y) - y) == 0; 
	}
	
	public void changeCurrentFrame(){
		
		if((moving || firing || dieing) && finishedDieing == 0){

			if(currentFrame == 90){
				
				forward = false;
			
			}else if(currentFrame == 0){
				
				if(dieing){
					
					finishedDieing ++;
				}
				
				forward = true;
				
			}

			if(finishedDieing == 0){
				if(forward) {

					currentFrame += 10;
				}
				else{

					currentFrame -= 10;
				}
			}
			
		}else{
			
			finishedDieing ++;
		}
	}
	
	public int getState(){
		
		if(firing){
			
			return 1;
			
		}else if(dieing){
			
			return 2;
		}
		
		return 0;
	}
	
	public int getCurrentFrame(){
		
		return currentFrame;
	}
	
	public float getX(){
		
		return x;
	}
	
	public float getY(){
		
		return y;
	}
	
	public String getUnitType(){
		
		return unitType;
	}

	
	public class GraphicalState{
		
		public int CurrentFrame;
		public boolean Moving;
		public boolean Firing;
		public boolean Dieing;
		public int LastCurrentFrame;
		public boolean Forward;
	}

	public GraphicalState getGraphicalState() {
		// TODO Auto-generated method stub
		GraphicalState gState = new GraphicalState();
		gState.CurrentFrame = currentFrame;
		gState.Firing = firing;
		gState.Dieing = dieing;
		gState.Moving = moving;
		gState.Forward = forward;
		return gState;
	}
	
	public void setGraphicalState(GraphicalState gState){
		
		currentFrame = gState.CurrentFrame;
		gState.Firing = firing;
		gState.Dieing = dieing;
		moving = gState.Moving;
		forward = gState.Forward;
		
		if(delayedMoving){
			
			this.moving();
		
		}else{
			
			this.stopMoving();
		}
		
		if(delayedFiring){
			
			this.setFiring();
		
		}else{
			
			this.stopFiring();
		}
	}

	public void updateState() {
		// TODO Auto-generated method stub
		if(delayedMoving){
			
			this.moving();
		
		}else{
			
			this.stopMoving();
		}
		
		if(delayedFiring){
			
			this.setFiring();
		
		}else{
			
			this.stopFiring();
		}
	}

	public boolean hasDied() {
		// TODO Auto-generated method stub
		return dieing;
	}

	public boolean isBusy() {
		// TODO Auto-generated method stub
		return !(moving || firing || dieing);
	}

	

}
