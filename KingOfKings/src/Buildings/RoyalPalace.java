package Buildings;

public class RoyalPalace extends Building {
	
	 @Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2500;
	}
	 
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX() + 4;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY() + 4;
	}


}
