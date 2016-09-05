package GameGraphics;

import java.util.ArrayList;

import Buildings.Names;

public class Unit {
	
	private float x;
	private float y;
	private String unitType;
	private boolean moving;
	private int currentFrame;
	private boolean forward;
	protected int state;
	private int player;
	private int unitNo;
	private int angle;
	private int attackingUnit;
	private static ArrayList<Unit> MovingUnits = new ArrayList<Unit>();
	
	public static void addUnit(Unit unit){
		
		MovingUnits.add(unit);
	}
	
	public static void removeUnit(Unit unit){
		
		MovingUnits.remove(unit);
	}
	
	public static void AnimateUnits(){
		
		for(int m = 0; m < MovingUnits.size(); m++){
			
			MovingUnits.get(m).changeCurrentFrame();
		}
	}
	
	public Unit(float x, float y,String unitType, int player, int unitNo){
		
		this.x = x;
		this.y = y;
		currentFrame = 0;
		forward = true;
		state = 0;
		this.unitType = unitType;
		this.player = player;
		this.unitNo = unitNo;
		this.attackingUnit = -1;

	}
	
	public boolean fireArrow(){
		
		return (state == 1 && unitType.equals(Names.ARCHER));
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
		
		this.Animating();
		state = 1;
	}
	
	public void stopFiring(){
		
		state = 0;
		this.StopAnimating();
	}
	
	public void die(){
		
		//this.Animating();
		state = 2;
	}
	
	public int getState(){
		
		return state;
	}
	
	public void moving(){
		
	    this.Animating();
		moving = true;
	}
	
	public boolean getMove(){
		
		return moving;
	}
	
	public void stopMoving(){
		
		moving = false;
		this.StopAnimating();
	}
	
	public void setAttack(int unitToAttack){
		
		this.attackingUnit = unitToAttack;
	}
	
	public int getAttacking(){
		
		return this.attackingUnit;
	}
	
	private void Animating(){
		
		if(state == 0 && !moving){
			
			Unit.addUnit(this);
		}
	}
	
	private void StopAnimating(){
		
		if(state == 0 && !moving){
			
			Unit.removeUnit(this);
		}
	}
	
	public boolean inUnit(int x, int y){
		
		return (((int)this.x) - x) == 0 && (((int)this.y) - y) == 0; 
	}
	
	public void changeCurrentFrame(){
		
		if(moving || state > 0){
			
			if(currentFrame >= 100){
				
				forward = false;
			
			}else if(currentFrame == 0){
				
				forward = true;
			}

			
			if(forward) {
				currentFrame += 2;
			}
			else {
				currentFrame -= 2;
			}
			
		}
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

}
