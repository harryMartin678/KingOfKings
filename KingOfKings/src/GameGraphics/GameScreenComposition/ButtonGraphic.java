package GameGraphics.GameScreenComposition;

public class ButtonGraphic {
	
	private String TexturePath;
	private float x;
	private float y;
	private float SizeX;
	private float SizeY;
	private float sx;
	private float sy;
	private float lx;
	private float ly;
	private float Red;
	private float Green;
	private float Blue;
	private String Name;
	private int Delay = 0;
	
	private boolean Selected;
	
	public ButtonGraphic(){
		
		Selected = false;
	}
	
	public void SetPosSize(float x, float y, float SizeX, float SizeY){
		
		this.x = x;
		this.y = y;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
	}
	
	public void SetMousePosSize(float sx, float sy, float lx, float ly){
		
		this.sx = sx;
		this.sy = sy;
		this.lx = lx;
		this.ly = ly;
	}
	
	public void SetTexture(String TexturePath){
		
		this.TexturePath = TexturePath;
	}
	
	public void SetName(String Name){
		
		this.Name = Name;
	}
	
	public void SetColour(float r,float g,float b){
		
		this.Red = r;
		this.Green = g;
		this.Blue = b;
	}
	
	public float GetX(){
		
		return x;
	}
	
	public float GetY(){
		
		return y;
	}
	
	public void IsDrawn(){
		
		//System.out.println(Red + " " + Green + " " + Blue + " " + Delay + " ButtonGraphic");
		if(Selected){
			
			if(Delay <= 0){
				
				//System.out.println("UnSelected ButtonGraphic");
				SetUnSelected();
			}
			Red += 0.16f;
			Green += 0.16f;
			Delay-= 16;
		}
	}
	
	public float GetSizeX(){
		
		return SizeX;
	}
	
	public float GetSizeY(){
		
		return SizeY;
	}
	
	public float GetRed(){
		
		return Red;
	}
	
	public float GetGreen(){
		
		return Green;
	}
	
	public float GetBlue(){
		
		return Blue;
	}
	
	public String GetTexturePath(){
		
		return TexturePath;
	}
	
	public String GetName(){
		
		return Name;
	}
	
	public void SetSelected(){
		
		assert(!Selected);
		Delay = 80;
		Red = 0.2f;
		Green = 0.2f;
		Selected = true;
	}
	
	public void SetUnSelected(){
		
		assert(Selected);
		Selected = false;
	}
	
	public boolean IsSelected(float mxt, float myt){
		
		return mxt < lx && mxt > sx 
				&& myt < ly && myt > sx;
	}

}
