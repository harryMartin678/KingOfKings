package GameGraphics.GameScreenComposition;

import Buildings.Names;
import GameGraphics.Building;

public class HoverPanelGraphic {
	
	private String TexturePanel;
	
	private float x;
	private float y;
	
	private double mouseX;
	private double mouseY;
	
	private String name;
	private int foodRequired;
	private int goldRequired;
	
	private float SizeX;
	private float SizeY;
	private float Red;
	private float Green;
	private float Blue;
	
	private int index;
	
	public static final String UnitIconPanel = "UNITICONPANEL";
	public static final String BuildingIconPanel = "BUILDINGICONPANEL";
	
	public HoverPanelGraphic(int index){
		
		this.index = index;
	}
	
	public void SetPos(float x, float y, float SizeX,float SizeY){
		
		this.x = x;
		this.y = y;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
	}
	
	public void SetMousePos(double cornerX,double cornerY){
		
		this.mouseX = cornerX;
		this.mouseY = cornerY;
	}
	
	public void SetColour(float Red,float Green,float Blue){
		
		this.Red =Red;
		this.Green = Green;
		this.Blue = Blue;
	}
	
	public void SetRequirements(String name, int foodRequired,int goldRequired){
		
		this.name = name;
		this.foodRequired = foodRequired;
		this.goldRequired = goldRequired;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getFoodRequired(){
		
		return foodRequired;
	}
	
	public int getGoldRequired(){
		
		return goldRequired;
	}

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
	
	public float getY(){
		
		return y;
		
	}
	
	public double getMouseX(){
		
		return mouseX;
	}
	
	public double getMouseY(){
		
		return mouseY;
	}
	
	public float getSizeX(){
		
		return SizeX;
	}
	
	public float getSizeY(){
		
		return SizeY;
	}
	
	public float getRed(){
		
		return Red;
	}
	
	public float getGreen(){
		
		return Green;
	}
	
	public float getBlue(){
		
		return Blue;
	}

	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	public void SetName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		Buildings.Building building = Building.GetBuildingClass(name);
		
		foodRequired = building.FoodNeeded();
		goldRequired = building.GoldNeeded();
	}

}
