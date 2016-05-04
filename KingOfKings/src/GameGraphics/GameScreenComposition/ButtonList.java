package GameGraphics.GameScreenComposition;

import java.util.HashMap;

public class ButtonList {

	protected HashMap<String,ButtonGraphic> buttons;
	private HashMap<String,ButtonGroup> buttonGroups;
	
	public ButtonList(){
		
		buttons = new HashMap<String,ButtonGraphic>();
		buttonGroups = new HashMap<String, ButtonGroup>();
	}
	
	public void AddButton(float x, float y,float SizeX, float SizeY,
			String TexturePath, String Name){
		
		ButtonGraphic button = new ButtonGraphic();
		button.SetPosSize(x, y, SizeX, SizeY);
		button.SetName(Name);
		button.SetTexture(TexturePath);
		button.SetColour(1.0f, 1.0f, 1.0f);
		buttons.put(Name,button);
	}
	
	public void AddButtonGroup(String GroupName){
		
		ButtonGroup group = new ButtonGroup(GroupName);
		buttonGroups.put(GroupName, group);
	}
	
	public ButtonGroup GetGroup(String GroupName){
		
		return buttonGroups.get(GroupName);
	}
	
	public void SetMouse(String Name, float mx, float my,float mSizeX,float mSizeY){
		
		ButtonGraphic button = buttons.get(Name);
		button.SetMousePosSize(mx, my, mSizeX, mSizeY);
	}
	
	public ButtonGraphic GetButton(String Name){
		
		return buttons.get(Name);
	}
	
	public String GetSelected(float mx, float my){
		
		ButtonGraphic[] ButtonsArray = (ButtonGraphic[]) buttons.values().toArray();
		for(int b = 0; b < ButtonsArray.length; b++){
			
			if(ButtonsArray[b].IsSelected(mx, my)){
				
				return ButtonsArray[b].GetName();
			}
		}
		
		return null;
	}
	
	public void SelectedByIndex(int index){
		
		((ButtonGraphic)buttons.values().toArray()[index]).SetSelected();
	}

	public void Selected(String mapName) {
		// TODO Auto-generated method stub
		 buttons.get(mapName).SetSelected();
	}
}
