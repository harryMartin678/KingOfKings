package GameGraphics.Menu;

import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import GameGraphics.GameScreenComposition.TextureRepo;

public class ResourcePanel {
	
	private TextRenderer text;
	private float CenterX;
	private float CenterY;
	private String texturePath;
	
	public ResourcePanel(float CenterX, float CenterY,String texturePath){
		
		text = new TextRenderer(new Font("Arial",Font.BOLD,20));
		this.CenterX = CenterX;
		this.CenterY = CenterY;
		this.texturePath = texturePath;
	}

	public void DrawPanel(GL2 draw,int ScreenWidth, int ScreenHeight,TextureRepo textures,
			int resource){
		
		float SizeX = 0.075f;
		float SizeY = 0.025f;
		
		new Rectangle( CenterX, CenterY, SizeX, SizeY,
				1.0f, 1.0f, 1.0f,0,texturePath).Draw(draw, ScreenWidth, ScreenHeight, textures);
		
		TextDrawer.drawString(draw, CenterX + (3*SizeX/8), CenterY + (SizeY/4), 0.0f, 0.0f, 0.0f, text,
				new Integer(resource).toString(), ScreenWidth, ScreenHeight);
	}

}
