package GameGraphics;

public class ChariotUnit extends Unit {

	public ChariotUnit(float x, float y, String unitType) {
		super(x, y, unitType);
		// TODO Auto-generated constructor stub
	}
	
	public void fireRight(){
		
		state = 3;
	}
	
}
