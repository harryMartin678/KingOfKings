package GameGraphics;

public class ChariotUnit extends Unit {

	public ChariotUnit(float x, float y, String unitType,int player, int unitNo) {
		super(x, y, unitType,player,unitNo);
		// TODO Auto-generated constructor stub
	}
	
	public void fireRight(){
		
		state = 3;
	}
	
}
