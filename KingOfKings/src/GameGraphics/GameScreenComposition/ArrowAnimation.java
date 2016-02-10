package GameGraphics.GameScreenComposition;

public class ArrowAnimation {

	private float arrowX;
	private float arrowY;
	private float targetX;
	private float targetY;
	private int unitNo;
	private float startX;
	private float startY;
	
	private final float speed;

	
	public ArrowAnimation(float startX, float startY,float targetX,float targetY,
			int unitNo){
		
		arrowX = startX;
		arrowY = startY;
		
		this.startX = startX;
		this.startY = startY;
		
		this.targetX = targetX;
		this.targetY = targetY;
		this.unitNo = unitNo;
		
		speed = 0.1f;
	}
	
	public boolean finished(){
		
		return (arrowX >= targetX && arrowY >= targetY);
	}
	
	public boolean isUnitsArrow(int unitNo){
		
		return (this.unitNo == unitNo);
	}
	
	
	public void progress(){
		
		arrowX += speed;
		arrowY += speed;
		
		if(targetX - arrowX < speed || targetY - arrowY < speed){
			
			arrowX = targetX;
			arrowY = targetY;
		}
	}
	
	public float getArrowX(){
		
		return arrowX;
	}
	
	public float getArrowY(){
		
		return arrowY;
	}
	
	public int getAngle(){
		
		int angle = 0;
		
		if(targetX - startX > 0 && targetY - startY == 0){
			
			angle = 90;
			
		}else if(targetX - startX < 0 && targetY - startY == 0){
			
			angle = 270;
		
		}else if(targetX - startX == 0 && targetY - startY > 0){
			
			angle = 180;
		
		}else if(targetX - startX == 0 && targetY - startY < 0){
			
			angle = 0;
		
		}else if(targetX - startX > 0 && targetY - startY < 0){
			
			angle = 45;
		
		}else if(targetX - startX > 0 && targetY - startY > 0){
			
			angle = 315;
		
		}else if(targetX - startX < 0 && targetY - startY > 0){
			
			angle = 225;
		
		}else if(targetX - startX < 0 && targetY - startY < 0){
			
			angle = 135;
		}
		
		return angle;
	}
}
