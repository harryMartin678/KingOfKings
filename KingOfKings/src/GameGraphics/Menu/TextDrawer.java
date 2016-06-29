package GameGraphics.Menu;

import java.awt.Color;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

public class TextDrawer {

	public static void drawString(GL2 draw, float x, float y,float r, 
			float g, float b,TextRenderer font, String msg,
			int ScreenWidth,int ScreenHeight){
	draw.glLoadIdentity();
	font.beginRendering(ScreenWidth,ScreenHeight);

	font.setColor(new Color(r,g,b));
	font.setSmoothing(true);
	font.draw(msg, (int)(ScreenWidth * x), (int)(ScreenHeight * y));
	
	font.endRendering();
	font.flush();
	
}
}
