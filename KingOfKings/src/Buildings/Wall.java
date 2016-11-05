package Buildings;

public class Wall extends Defence {

	private float angleFromCenter;
	
	public Wall(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.WALL;
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2000;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+10;
	}
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX()+1;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY()+1;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		//turn down later
		return super.FoodNeeded()+10;
	}
	
//	public void calRotationFromCenter(float cx, float cy){
//		
//		float dx = this.getX() - cx;
//		float dy = this.getY() - cy;
//		
//		this.angleFromCenter =  (float)(Math.acos(dx / Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) 
//				* (180.0f/Math.PI));		
//
//	}
	
	public void setRotationFromCenter(float angle){
		
		this.angleFromCenter = angle;
	}
	

	public float getRotationFromCenter() {
		// TODO Auto-generated method stub
		return angleFromCenter;
	}
}
