package GameGraphics.GameScreenComposition;

public class HoverPanelGraphic {
	
	private String TexturePanel;
	private String Food;
	private String Gold;
	
	private float x;
	private float y;
	private float SizeX;
	private float SizeY;
	private float Red;
	private float Green;
	private float Blue;
	
	public static final String UnitIconPanel = "UNITICONPANEL";
	public static final String BuildingIconPanel = "BUILDINGICONPANEL";
	
	public HoverPanelGraphic(){
		
		
	}
	
	public void SetPos(float x, float y, float SizeX,float SizeY){
		
		this.x = x;
		this.y = y;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
	}
	
	public void SetColour(float Red,float Green,float Blue){
		
		this.Red =Red;
		this.Green = Green;
		this.Blue = Blue;
	}

}
