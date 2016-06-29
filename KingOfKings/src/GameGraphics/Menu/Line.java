package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

public class Line {

	private float X1;
	private float X2;
	private float Y1;
	private float Y2;
	
	public Line(float X1,float X2,float Y1,float Y2){
		
		this.X1 = X1;
		this.X2 = X2;
		this.Y1 = Y1;
		this.Y2 = Y2;
	}
	
	public void DrawLine(GL2 draw,int ScreenWidth,int ScreenHeight){
		
		draw.glLoadIdentity();
		//draw.glTranslatef(centerX, centerY, z);
		draw.glColor3f(1.0f,1.0f,1.0f);
		
		draw.glBegin(draw.GL_LINE_LOOP);

			draw.glVertex2d(X1 * ScreenWidth, Y1 * ScreenHeight);
			draw.glVertex2d(X2 * ScreenWidth, Y2 * ScreenHeight);
		draw.glEnd();
	}
}
