package GameGraphics.Menu;

import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import GameGraphics.GameScreenComposition.TextureRepo;

public class PopupInfoBox {
	
	private float CenterX;
	private float CenterY;
	private float SizeX;
	private float SizeY;
	private String Title;
	private String Text;
	private TextRenderer font;
	private Rectangle exitButton;

	public PopupInfoBox(float CenterX,float CenterY,float SizeX,float SizeY,
			String Title,String Text){
		
		this.CenterX = CenterX;
		this.CenterY = CenterY;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		this.Title = Title;
		this.Text = Text;
		font = new TextRenderer(new Font("Arial",Font.BOLD,20));
		
		exitButton = new Rectangle(CenterX + (17*(SizeX/18)),CenterY+(17*(SizeY/18)),
				0.015f,0.03f,  1.0f, 0.0f,0.0f,0,null);
	}
	
	public void DrawPopupInfo(GL2 draw,int ScreenWidth,int ScreenHeight,TextureRepo textures){
		
		new Rectangle().DrawWithoutCreation(draw, ScreenWidth, ScreenHeight, CenterX, CenterY,
				SizeX, SizeY, 1.0f, 1.0f, 1.0f, true,textures);
		
		
		
		TextDrawer.drawString(draw, CenterX + (4*(SizeX/10)), CenterY+(19*(SizeY/20)),
				0.0f, 0.0f, 0.0f,font, Title, ScreenWidth, ScreenHeight);
		
		String[] lines = Text.split("\n");
		
		for(int l = 0; l < lines.length; l++){
			
			TextDrawer.drawString(draw, CenterX + (SizeX/10), 
					CenterY + (5 * (SizeY/6)) - (0.05f*l),
					0.0f, 0.0f, 0.0f, font,lines[l], ScreenWidth, ScreenHeight);
		}
		
		//System.out.println((CenterX + SizeX) + " PopupInfoBox");
		//exit button
//		new Rectangle().DrawWithoutCreation(draw,ScreenWidth, ScreenHeight,
//				CenterX + (17*(SizeX/18)),CenterY+(17*(SizeY/18)), 0.015f,0.03f,  1.0f, 0.0f,
//				0.0f, true);
		exitButton.Draw(draw, ScreenWidth, ScreenHeight,textures);
		
		
	}
	
	
	public boolean ClosePopup(float mx, float my){
		
		return exitButton.InMouse(mx, my);
	}
}
