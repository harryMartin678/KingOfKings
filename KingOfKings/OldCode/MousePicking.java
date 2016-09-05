package GameGraphics;

import GameGraphics.GameScreenComposition.Display;
import Util.Matrix;
import Util.Vector3;
import Util.Vector4;

public class MousePicking {
	
	private Vector3 origin;
	//private float[] direction;
	private Vector3 direction;
	
	float right[]    = { 1, 0, 0, 0 };
	float up[]       = { 0, 1, 0, 0 };
	float forward[]  = { 0, 0, 1, 0 };
	float position[] = { 0, 0, 0, 1 };
	 
	float ViewMatrix[][] = {
	    {   right[0],    right[1],    right[2],    right[3] }, // First column
	    {      up[0],       up[1],       up[2],       up[3] }, // Second column
	    { forward[0],  forward[1],  forward[2],  forward[3] }, // Third column
	    {position[0], position[1], position[2], position[3] }  // Forth column
	};

	public MousePicking(float[] point, int ScreenWidth,int ScreenHeight){
		
		float x = ((2.0f * point[0]) / ScreenWidth) - 1.0f;
		float y = 1.0f - (2.0f * point[1]) / ScreenHeight;
		float z = -1.0f;
		
		Vector3 ray = new Vector3(x,y,z);
		Vector4 rayClip = new Vector4(x,y,z,1.0f);
		float n = 3.0f;
		float f = 600.0f;
		float l = 0.0f;
		float b = 0.0f;
		float r = ScreenWidth;
		float t = ScreenHeight;
		////45,((float)ScreenWidth/(float)ScreenHeight),3,600
		double[][] proj = new double[][]{
				{(2 * n)/(r - l),0,(r + l)/(r - l),0},
				{0,(2*n)/(t-b),(t + b)/(t - b),0},
				{0,0,-(f + n)/(f - n),(-2*f*n)/(f - n)},
				{0,0,-1,0}
			};
		
		Matrix projMatrix = new Matrix(proj);
		projMatrix.InverseMatrix();
		Matrix rayClipMatrix = new Matrix(rayClip.getMatrixVertical());
		Matrix rayeye = projMatrix.times(rayClipMatrix);
		
		Matrix viewMatrix = new Matrix(ViewMatrix);
		viewMatrix.InverseMatrix();
		Matrix raywor = viewMatrix.times(rayeye);
		Vector3 rayworvec = new Vector3();
		rayworvec.x = (float) raywor.getValue(0, 0);
		rayworvec.y = (float) raywor.getValue(1, 0);
		rayworvec.z = (float) raywor.getValue(2, 0);
		rayworvec.Normalize();
		rayworvec.Print("Ray World: ");
		origin = new Vector3(0.0f, 0.0f, 10.0f);
		direction = rayworvec;
		
	}
	
	public float[] CrossProduct(float[] a,float[] b){
		
		return new float[]{a[1] * b[2] - a[2] * b[1],
						   a[2] * b[1] - a[0] * b[2],
						   a[0] * b[1] - a[1] * b[0]};
	}
	
	public boolean IsSelected(float[] bBox,float x, float y,float z){
		
//		end.Print("End");
//		origin.Print("Origin");
//		System.out.println("MaxX: " + (bBox[0] + x) + " MinX: " + (bBox[1] + x)
//				+ " MaxY: " + (bBox[2] + y) + " MinY: " + (bBox[3] + y) +
//				" MaxZ: " + (bBox[4] + z) + " MinZ: " + (bBox[5] + z) + " MousePicker");
//		return CheckLineBox(new Vector3(bBox[0] + x,bBox[2] + y,bBox[4] + z),
//				new Vector3(bBox[1] + x,bBox[3] + y,bBox[5] + z),end,origin);
//		return RayInBox(new Vector3(bBox[0] + x,bBox[2] + y,bBox[4] + z),
//				new Vector3(bBox[1] + x,bBox[3] + y,bBox[5] + z));
		return RayInCube(bBox);
	}
	
	private boolean RayInCube(float[] bBox){
		
		Vector3 one = new Vector3(bBox[0],bBox[2],bBox[5]);
		Vector3 two = new Vector3(bBox[0],bBox[2],bBox[4]);
		Vector3 three = new Vector3(bBox[1],bBox[2],bBox[4]);
		Vector3 four = new Vector3(bBox[1],bBox[2],bBox[5]);
		Vector3 five = new Vector3(bBox[1],bBox[3],bBox[5]);
		Vector3 six = new Vector3(bBox[1],bBox[3],bBox[4]);
		Vector3 seven = new Vector3(bBox[0],bBox[3],bBox[4]);
		Vector3 eight = new Vector3(bBox[0],bBox[3],bBox[5]);
		//top bottom front back right left
		return RayPlaneIntersection(one,two,three,four) || RayPlaneIntersection(five,six,seven,eight)
				|| RayPlaneIntersection(six,seven,three,two) || RayPlaneIntersection(four, one, five,eight)
				|| RayPlaneIntersection(four, three, five,six) || RayPlaneIntersection(one, two, eight,seven);
	}
	
	private boolean RayInBox(Vector3 max,Vector3 min){
		
		Vector3 dirfrac = new Vector3();
		// r.dir is unit direction vector of ray
		dirfrac.x = 1.0f / direction.x;
		dirfrac.y = 1.0f / direction.y;
		dirfrac.z = 1.0f / direction.z;
		// lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
		// r.org is origin of ray
		float t1 = (min.x - origin.x)*dirfrac.x;
		float t2 = (max.x - origin.x)*dirfrac.x;
		float t3 = (min.y - origin.y)*dirfrac.y;
		float t4 = (max.y - origin.y)*dirfrac.y;
		float t5 = (min.z - origin.z)*dirfrac.z;
		float t6 = (max.z - origin.z)*dirfrac.z;

		float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
		
		tmax = -tmax;
		
		System.out.println("tmin: " + tmin + " tmax: " + tmax );

		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behing us
		if (tmax < 0)
		{
		   // t = tmax;
		    return false;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
		    //t = tmax;
		    return false;
		}

		//t = tmin;
		return true;
		
		
	}
	
	private boolean RayPlaneIntersection(Vector3 cor1, Vector3 cor2, Vector3 cor3,Vector3 cor4){
		
		Matrix planeMatrix = new Matrix(new double[][]{
			
			{cor1.x,cor1.y,cor3.z,1},
			{cor2.x,cor2.y,cor2.z,1},
			{cor3.x,cor3.y,cor3.z,1},
			{cor4.x,cor4.y,cor4.z,1}
		});
		
		Matrix planeParameters = planeMatrix.GaussianElimination(new double[]{0,0,0,0});
		
		float t = (float) (-(origin.Dot(planeParameters.ToVector3()) + planeParameters.getValue(0, 4)) / 
				(direction.Dot(planeParameters.ToVector3())));
		
		// Get a point on the plane.
//	    Vector3 p = ray.Start + t * ray.Dir;
//
//	    // Does the ray intersect the plane inside or outside?
//	    Vector3 planeToRayStart = ray.Start - p;
//	    double dot = Vector3.Dot (planeToRayStart, Norm);
//	    if (dot > 0) {
//	        result.Inside = false;
//	    } else {
//	        result.Inside = true;
//	    }
		
		Vector3 p = origin.Add(direction.Multiply(t));
		
		Vector3 planeToRayStart = origin.Subtract(p);
		
		float dot = planeToRayStart.Dot(planeParameters.ToVector3());
		
		if(dot > 0){
			
			return false;
		}else{
			
			return true;
		}
	}
	
	private boolean CheckLineBox(Vector3 B1, Vector3 B2, Vector3 L1, Vector3 L2)
	{
		
		Vector3 Hit = new Vector3();
	    if (L2.x < B1.x && L1.x < B1.x) return false;
	    if (L2.x > B2.x && L1.x > B2.x) return false;
	    if (L2.y < B1.y && L1.y < B1.y) return false;
	    if (L2.y > B2.y && L1.y > B2.y) return false;
	    if (L2.z < B1.z && L1.z < B1.z) return false;
	    if (L2.z > B2.z && L1.z > B2.z) return false;
	    if (L1.x > B1.x && L1.x < B2.x &&
	        L1.y > B1.y && L1.y < B2.y &&
	        L1.z > B1.z && L1.z < B2.z)
	    {
	        Hit.SetValue(L1);
	        return true;
	    }
	    if ((GetIntersection(L1.x - B1.x, L2.x - B1.x, L1, L2, Hit) && InBox(Hit, B1, B2, 1))
	      || (GetIntersection(L1.y - B1.y, L2.y - B1.y, L1, L2, Hit) && InBox(Hit, B1, B2, 2))
	      || (GetIntersection(L1.z - B1.z, L2.z - B1.z, L1, L2, Hit) && InBox(Hit, B1, B2, 3))
	      || (GetIntersection(L1.x - B2.x, L2.x - B2.x, L1, L2, Hit) && InBox(Hit, B1, B2, 1))
	      || (GetIntersection(L1.y - B2.y, L2.y - B2.y, L1, L2, Hit) && InBox(Hit, B1, B2, 2))
	      || (GetIntersection(L1.z - B2.z, L2.z - B2.z, L1, L2, Hit) && InBox(Hit, B1, B2, 3)))
	        return true;

	    return false;
	}
	
	private boolean GetIntersection(float fDst1, float fDst2,
			Vector3 P1, Vector3 P2, Vector3 Hit)
	{
	    if ((fDst1 * fDst2) >= 0.0f) return false;
	    if (fDst1 == fDst2) return false;
	    Hit.SetValue(P1.Add(P2.Subtract(P1)).Multiply((-fDst1 / (fDst2 - fDst1))));
	    return true;
	}
	
	private boolean InBox(Vector3 Hit, Vector3 B1, Vector3 B2, int Axis)
	{
	    if (Axis == 1 && Hit.z > B1.z && Hit.z < B2.z &&
	    		Hit.y > B1.y && Hit.y < B2.y) return true;
	    if (Axis == 2 && Hit.z > B1.z && Hit.z < B2.z &&
	    		Hit.x > B1.x && Hit.x < B2.x) return true;
	    if (Axis == 3 && Hit.x > B1.x && Hit.x < B2.x &&
	    		Hit.y > B1.y && Hit.y < B2.y) return true;
	    return false;
	}
	
	
	
	


}

//float[] viewpoint = new float[]{Display.ViewMatrix[1][0] - Display.ViewMatrix[0][0]
//,Display.ViewMatrix[1][1] - Display.ViewMatrix[0][1],
//Display.ViewMatrix[1][2] - Display.ViewMatrix[0][2]};
//
//float[] up = new float[]{Display.ViewMatrix[2][0],Display.ViewMatrix[2][1],
//			Display.ViewMatrix[2][2]};
//
//float[] h = CrossProduct(viewpoint, up);
//
//float[] v = CrossProduct(h, viewpoint);
//
//float vlength = (float) (Math.tan(Math.PI/4) * 3);
//float hlength = vlength * ((float)ScreenWidth/(float)ScreenHeight);
//
//System.out.println("vlength: " + vlength + " hlength: " + hlength);
//
//h[0] *= hlength;
//h[1] *= hlength;
//h[2] *= hlength;
//
//System.out.println("h: " + h[0] + " " + h[1] + " " + h[2] + " MousePicker");
//
//v[0] *= vlength;
//v[1] *= vlength;
//v[2] *= vlength;
//
//System.out.println("v: " + v[0] + " " + v[1] + " " + v[2] + " MousePicker");
//
//float x = (point[0] - (ScreenWidth/2)) / (ScreenWidth/2);
//float y = (point[1] - (ScreenHeight/2)) / (ScreenHeight/2);
//
//origin = new Vector3(Display.ViewMatrix[0][0] + (viewpoint[0]*3)
//+ (h[0] * x) + (v[0] * y),Display.ViewMatrix[0][1] + (viewpoint[1]*3)
//+ (h[1] * x) + (v[1] * y),Display.ViewMatrix[0][2] + (viewpoint[2]*3)
//+ (h[2] * x) + (v[2] * y));
//
////direction = new float[]{origin[0] - Display.ViewMatrix[0][0], 
////	origin[1] - Display.ViewMatrix[0][1],origin[2] - Display.ViewMatrix[0][2]};
//end = new Vector3(Display.ViewMatrix[0][0],Display.ViewMatrix[0][1],
//Display.ViewMatrix[0][2]);
