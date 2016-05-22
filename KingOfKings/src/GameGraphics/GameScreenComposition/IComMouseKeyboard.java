package GameGraphics.GameScreenComposition;

public interface IComMouseKeyboard {

	
//	dragBox &&
//	lastDB != null && startDB != null 
//	&& y <= Math.max(lastDB[1],startDB[1]) 
//	&& y >= Math.min(lastDB[1],startDB[1])
//	&& x <= Math.max(lastDB[0],startDB[0]) 
//	&& x >= Math.min(lastDB[0],startDB[0])
	public boolean isInDragBox(int x, int y);
	public void moveUnit(int tx,int ty,int difference);
	//public void setButtonList(ButtonList buttons);
}
