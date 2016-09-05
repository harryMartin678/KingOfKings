package GameGraphics.Menu;

import java.awt.Color;
import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import GameGraphics.Face;
import GameGraphics.TextModel;
import GameGraphics.Vertex;
import GameGraphics.GameScreenComposition.TextureRepo;

public class HoverPanel {
	
	private float Left;
	private float Top;
	private TextRenderer text;
	private String Title;
	private String Line1; 
	private String Line2;
	public final static float SizeX = 0.05f;
	public final static float SizeY = 0.075f;

	public HoverPanel(float Left,float Top){
		
		this.Left = Left;
		this.Top = Top;
		text = new TextRenderer(new Font("Arial",Font.BOLD,14));
	}
	
	public void SetText(String Title,String Line1, String Line2){
		
		this.Title = Title;
		this.Line1 = Line1;
		this.Line2 = Line2;
	}
	
	public void DrawHoverPanel(GL2 draw,int ScreenWidth,int ScreenHeight,TextureRepo texture) {
		// TODO Auto-generated method stub
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight, 
				Left, Top, SizeX, SizeY, 0.0f, 1.0f, 0.0f, true,texture);
		//+ (SizeX/2) - (Title.length() * 0.0016f)
		TextDrawer.drawString(draw, Left , Top + (3*(SizeY/4)), 
				1.0f,1.0f, 1.0f,text, Title,ScreenWidth, ScreenHeight);
		TextDrawer.drawString(draw, Left, Top + (2*(SizeY/4)), 
				1.0f,1.0f, 1.0f,text, Line1,ScreenWidth, ScreenHeight);
		TextDrawer.drawString(draw, Left , Top + (SizeY/4), 
				1.0f,1.0f, 1.0f,text, Line2,ScreenWidth, ScreenHeight);
	}
	
	
	

}
