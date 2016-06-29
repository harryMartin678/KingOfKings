package GameGraphics.Menu;

import com.jogamp.opengl.GL2;

public class Circle extends MenuShape {
	
	private boolean Fill;
	private float radius;

	public Circle(float CenterX, float CenterY, float SizeX, float SizeY,
			float Red, float Green, float Blue,boolean Fill,float radius) {
		super(CenterX, CenterY, SizeX, SizeY, Red, Green, Blue,0);
		// TODO Auto-generated constructor stub
		this.Fill = Fill;
		this.radius = radius;
	}
	
	public Circle(){
		
		super();
	}

	@Override
	public void Draw(GL2 draw,int ScreenWidth,int ScreenHeight) {
		// TODO Auto-generated method stub
		super.Draw(draw,ScreenWidth,ScreenHeight);
		DrawCircle(draw,ScreenWidth,ScreenHeight);
	}
	
	public void DrawWithoutCreation(GL2 draw, int ScreenWidth, int ScreenHeight, float CenterX,
			float CenterY,float radius,float Red,float Green,float Blue,boolean Fill) {
		// TODO Auto-generated method stub
		DrawCircleCode(draw, ScreenWidth, ScreenHeight,
				Red, Green, Blue,CenterX, CenterY, radius, Fill);
	}

	private void DrawCircle(GL2 draw,int ScreenWidth,int ScreenHeight){
//			x = x * ScreenWidth;
//			y = y * ScreenHeight;
		 	//float[] pos = FromRelToAbs(CenterX, CenterY, ScreenWidth, ScreenHeight);
//			radius = radius * Math.sqrt(Math.pow(ScreenHeight,2) + Math.pow(ScreenWidth,2));
			//System.out.println(x + " " + y + " " + radius + " " + ScreenWidth +  " DisplayMapDiagram");
			DrawCircleCode(draw, ScreenWidth, ScreenHeight,
					Red, Green, Blue,
					CenterX, CenterY, radius, Fill);
		}
	
	private void DrawCircleCode(GL2 draw,int ScreenWidth,int ScreenHeight,float Red,float Green
			,float Blue,float CenterX,float CenterY,float radius,boolean Fill){
		
		draw.glLoadIdentity();
		//draw.glTranslatef((float)x, (float)y, z);
		draw.glColor3f(Red, Green, Blue);
		if(Fill){
			
			draw.glBegin(draw.GL_POLYGON);
			
		}else{
			
			draw.glBegin(draw.GL_LINE_LOOP);
		}
		
		 
		   for (double i=0.0; i < 360.0; i++)
		   {
			   draw.glVertex2d(((Math.cos(i)*radius) + CenterX) * ScreenWidth,
					   ((Math.sin(i)*radius) + CenterY) * ScreenHeight);
		   }
		 
	   draw.glEnd();
	}

}
