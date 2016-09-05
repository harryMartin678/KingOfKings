package GameGraphics;

import com.jogamp.opengl.glu.GLU;

import GameGraphics.GameScreenComposition.Display;
import Util.Vector3;

public class MousePicker {

	
	public MousePicker(){

	}
	
	
	public Vector3 CalculateMouseRay(int x, int y,int ScreenWidth,int ScreenHeight){
		
	    float zero[] = new float[4];// wx, wy, wz;// returned xyz coords
	    float one[] = new float[4];
		
		GLU glu = new GLU();
		glu.gluUnProject((float) x, (float) Display.viewport[3] - y - 1, -1.0f, //
				  Display.modelview, 0,
	              Display.projection, 0, 
	              Display.viewport, 0, 
	              zero, 0);
		glu.gluUnProject((float) x, (float) Display.viewport[3] - y - 1, 0.0f, //
				  Display.modelview, 0,
	              Display.projection, 0, 
	              Display.viewport, 0, 
	              one, 0);

		
		return new Vector3(one[0] - zero[0],one[1] - zero[1],one[2] - zero[2]);
	}
	
	public boolean IsSelected(float[] bBox,Vector3 ray,float x, float y){
		
//		System.out.println(x + " " + y + " MousePicker2");
		float distance = Math.abs(((bBox[4]- 35.0f) + (bBox[5] - 35.0f))/2)/ ray.z;
//		System.out.println(distance + " MousePicker2");
		Vector3 scaledRay = ray.Multiply(-distance);
//		
//		System.out.println("MinX: " + scaledRay.x + " " + (bBox[1] + x) + " MaxX: " 
//				+ scaledRay.x + " " + (bBox[0] + x) + " MinY: " + scaledRay.y + " " + (bBox[3] + y) 
//				+ " MaxY: " + scaledRay.y + " " + (bBox[2] + y) + " MinZ: " + 
//				scaledRay.z + " " + (bBox[5] - 35.0f) + " MaxZ: " + scaledRay.z + " " + (bBox[4]- 35.0f)
//				+ " MousePicker2");
//		
//		System.out.println((scaledRay.x >= (bBox[1] + x) && scaledRay.x <= (bBox[0] + x))
//				+ " MousePicking");
		
		return scaledRay.x >= (bBox[1] + x) && scaledRay.x <= (bBox[0] + x) 
				&& scaledRay.y >= (bBox[3] + y) && scaledRay.y <= (bBox[2] + y) 
				&& scaledRay.z >= (bBox[5] - 35.0f) && scaledRay.z <= (bBox[4]- 35.0f);
	}
	

	
}
