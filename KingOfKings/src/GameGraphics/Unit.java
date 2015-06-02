package GameGraphics;

public class Unit {
	
	private float x;
	private float y;
	private String unitType;
	private boolean moving;
	private int currentFrame;
	private boolean forward;
	protected int state;
	
	public Unit(float x, float y,String unitType){
		
		this.x = x;
		this.y = y;
		currentFrame = 0;
		forward = true;
		state = 0;
		this.unitType = unitType;
	}
	
	public void setFiring(){
		
		state = 1;
	}
	
	public void stopFiring(){
		
		state = 0;
	}
	
	public void die(){
		
		state = 2;
	}
	
	public int getState(){
		
		return state;
	}
	
	public void moving(){
		
		moving = true;
	}
	
	public void stopMoving(){
		
		moving = false;
	}
	
	public void changeCurrentFrame(){
		
		if(moving || state > 0){
			
			if(currentFrame == 2){
				
				forward = false;
			
			}else if(currentFrame == 0){
				
				forward = true;
			}

			
			if(forward) {
				currentFrame ++;
			}
			else {
				currentFrame--;
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
