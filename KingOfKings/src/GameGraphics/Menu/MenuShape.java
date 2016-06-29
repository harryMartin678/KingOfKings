package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

public class MenuShape {
	
	protected float CenterX;
	protected float CenterY;
	protected float SizeX;
	protected float SizeY;
	protected float Red;
	protected float Green;
	protected float Blue;
	
	private boolean Selected;
	private int MAXDELAY = 10;
	private int Delay = MAXDELAY;
	
	private float diffRed;
	private float diffGreen;
	private float diffBlue;
	
	protected int index;

	
	public MenuShape(float CenterX,float CenterY,float SizeX,float SizeY,float Red,float Green
			,float Blue,int index){
		
		this.CenterX = CenterX;
		this.CenterY = CenterY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.Red = Red;
		this.Green = Green;
		this.Blue = Blue;
		this.index = index;
		
		Selected = false;
	}
	
	public MenuShape(){
		
		
	}
	
	public int getIndex(){
		
		return index;
	}
	
	
	public float getSizeX(){
		
		return SizeX;
	}
	
	public float getSizeY(){
		
		return SizeY;
	}
	
	public float getCenterX(){
		
		return CenterX;
	}
	
	public float getCenterY(){
		
		return CenterY;
	}

	
	public boolean InMouse(float x, float y){
		
		return x <= CenterX + SizeX && x >= CenterX && 
				y <= CenterY + SizeY && y >= CenterY;  
	}
	
	public void IsDrawn(){
		
		if(Selected){
			
			//System.out.println(Red + " " + Green + " " + Blue + " " + Delay + " MenuShape");
			if(Delay <= 0){
				
				//System.out.println("UnSelected ButtonGraphic");
				SetUnSelected();
			}
			Red += diffRed/MAXDELAY;
			Green += diffGreen/MAXDELAY;
			Blue += diffBlue/MAXDELAY;
			Delay--;
		}
	}
	
	public void Draw(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		
	}
	
	public void DrawWithoutCreation(GL2 draw, int ScreenWidth, int ScreenHeight,
			float CenterX,float CenterY,float SizeX,float SizeY){
		
		
	}
	
	public float[] FromRelToAbs(double x, double y,int ScreenWidth, int ScreenHeight){
		
		return new float[]{(float) (x * (float)ScreenWidth), (float) (y * (float)ScreenHeight)};
	}
	
	public float FromRelToAbsInd(double value,int convert){
		
		return (float) (value * convert);
	}
	
	public void SetSelected(){
		
		assert(!Selected);
		Delay = MAXDELAY;
		float newRed = 0.0f;
		float newGreen = 0.0f;
		float newBlue = 1.0f;
		diffRed = Red - newRed;
		diffGreen = Green - newGreen;
		diffBlue = Blue - newBlue;
		
		Red = newRed;
		Green = newGreen;
		Blue = newBlue;
//		Red -= 0.8f;
//		Green -= 0.8f;
		Selected = true;
	}
	
	public void SetUnSelected(){
		
		assert(Selected);
		Selected = false;
	}
	

}
