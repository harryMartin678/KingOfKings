package GameGraphics;

public class Unit {
	
	private float x;
	private float y;
	private String unitType;
	private boolean moving;
	private int currentFrame;
	private boolean forward;
	private boolean firing;
	
	public Unit(float x, float y,String unitType){
		
		this.x = x;
		this.y = y;
		currentFrame = 0;
		forward = true;
		firing = false;
		this.unitType = unitType;
	}
	
	public void setFiring(){
		
		firing = true;
	}
	
	public void stopFiring(){
		
		firing = false;
	}
	
	public boolean getFiring(){
		
		return firing;
	}
	
	public void moving(){
		
		moving = true;
	}
	
	public void stopMoving(){
		
		moving = false;
	}
	
	public void changeCurrentFrame(){
		
		if(moving || firing){
			
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
