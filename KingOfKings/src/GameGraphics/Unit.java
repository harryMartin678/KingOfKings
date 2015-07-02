package GameGraphics;

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
	
	public Unit(float x, float y,String unitType, int player, int unitNo){
		
		this.x = x;
		this.y = y;
		currentFrame = 0;
		forward = true;
		state = 0;
		this.unitType = unitType;
		this.player = player;
		this.unitNo = unitNo;

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
	
	public boolean getMove(){
		
		return moving;
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
