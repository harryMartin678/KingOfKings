package GameGraphics.GameScreenComposition;

public class ButtonGroup extends ButtonList {
	
	private String GroupName;
	
	public ButtonGroup(String GroupName){
		
		this.GroupName = GroupName;
	}
	
	public String getGroupName(){
		
		return GroupName;
	}
	
	public void Clear(){
		
		this.buttons.clear();
	}
	
	public void AreDrawn(){
		
		Object[] buttonList = buttons.values().toArray();
		
		for(int l = 0; l < buttonList.length; l++){
			
			((ButtonGraphic)buttonList[l]).IsDrawn();
		}
	}

	public int Size() {
		// TODO Auto-generated method stub
		return buttons.size();
	}

}
