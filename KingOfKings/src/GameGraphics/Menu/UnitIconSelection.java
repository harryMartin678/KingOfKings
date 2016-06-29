package GameGraphics.Menu;

public class UnitIconSelection {

	private boolean isUnitCreatorSelected;
	private int UnitNo;
	
	public UnitIconSelection(boolean isUnitCreatorSelected,int UnitNo){
		
		this.isUnitCreatorSelected = isUnitCreatorSelected;
		this.UnitNo = UnitNo;
	}
	
	public boolean isUnitCreatorSelected(){
		
		return isUnitCreatorSelected;
	}
	
	public int NoOfUnits(){
		
		return UnitNo;
	}
}
