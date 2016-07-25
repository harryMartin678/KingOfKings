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
		
		return (arrowX > targetX - 0.1 && arrowX < targetX + 0.1 && 
					arrowY > targetY - 0.1 && arrowY < targetY + 0.1);
	}
	
	public boolean isUnitsArrow(int unitNo){
		
		return (this.unitNo == unitNo);
	}
	
	
	public void progress(){
		
		if(targetX > startX){
			
			arrowX += speed;
		}else{
			arrowX -= speed;
		}
		
		if(targetY > startY){
			arrowY += speed;
		}else{
			arrowY -= speed;
		}
			
		//System.out.println(targetX + " " + targetY + " " + arrowX + " " + arrowY + " AnimationArrow");
		if(Math.abs(targetX - arrowX) < speed || Math.abs(targetY - arrowY) < speed){
			
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
			
			angle = 180;
		
		}else if(targetX - startX == 0 && targetY - startY > 0){
			
			angle = 90;
		
		}else if(targetX - startX == 0 && targetY - startY < 0){
			
			angle = 270;
		
		}else if(targetX - startX > 0 && targetY - startY < 0){
			
			angle = 315;
		
		}else if(targetX - startX > 0 && targetY - startY > 0){
			
			angle = 45;
		
		}else if(targetX - startX < 0 && targetY - startY > 0){
			
			angle = 135;
		
		}else if(targetX - startX < 0 && targetY - startY < 0){
			
			angle = 225;
		}
		
		return angle;
	}
}
