package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

public class Rectangle extends MenuShape {


	
	public Rectangle(float CenterX, float CenterY, float SizeX, float SizeY,
			float Red, float Green, float Blue,int index) {
		super(CenterX, CenterY, SizeX, SizeY, Red, Green, Blue,index);
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle(){
		
		super();
	}
	
	@Override
	public void Draw(GL2 draw,int ScreenWidth,int ScreenHeight) {
		// TODO Auto-generated method stub
		super.Draw(draw,ScreenWidth,ScreenHeight);
		drawMenuQuad(draw,ScreenWidth,ScreenHeight,CenterX,CenterY,SizeX,
				SizeY,Red,Green,Blue,true);
	}
	
	public void DrawWithoutCreation(GL2 draw, int ScreenWidth, int ScreenHeight, float CenterX, float CenterY,
			float SizeX, float SizeY,float Red,float Green, float Blue,boolean Fill) {
		// TODO Auto-generated method stub
		//System.out.println(CenterX + " Rectangle");
		drawMenuQuad(draw, ScreenWidth, ScreenHeight, 
				CenterX, CenterY, SizeX, SizeY, Red, Green, Blue,Fill);
	}

	private void drawMenuQuad(GL2 draw,int ScreenWidth,int ScreenHeight,float CenterX,
			float CenterY,float SizeX,float SizeY,float Red,float Green,float Blue,
			boolean Fill){
		
		draw.glLoadIdentity();
		
		float[] pos = FromRelToAbs(CenterX, CenterY, ScreenWidth, ScreenHeight);
		float[] Size = FromRelToAbs(SizeX,SizeY,ScreenWidth,ScreenHeight);
		
		//draw.glTranslatef(0.0f, 0.0f, -20.0f);
		//draw.glTranslatef(pos[0],pos[1], -1.0f);
		draw.glColor3f(Red, Green, Blue);
		//draw.glRotatef(90.0f, 1, 0, 0);
		//draw.glScalef(Size[0], Size[1], 1.0f);
		//System.out.println(S + " " + pos[1] + " Rectangle");
		//draw.glNormal3f(0.0f, 0.0f, 0.0f);
		if(Fill){
			draw.glBegin(draw.GL_QUADS);
		}else{
			draw.glBegin(draw.GL_LINE_LOOP);
		}
		
			draw.glVertex2f(pos[0], pos[1]);
			draw.glVertex2f(pos[0], pos[1]+Size[1]);
			draw.glVertex2f(pos[0]+Size[0], pos[1]+Size[1]);
			draw.glVertex2f(pos[0]+Size[0], pos[1]);
		draw.glEnd();
	}
	

}
