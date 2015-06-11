package GameGraphics;

public class ChariotUnit extends Unit {

	public ChariotUnit(float x, float y, String unitType,int player) {
		super(x, y, unitType,player);
		// TODO Auto-generated constructor stub
	}
	
	public void fireRight(){
		
		state = 3;
	}
	
}
