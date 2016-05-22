package GameGraphics;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class BoundingBox {

	private double maxX;
	private double minX;
	private double maxY;
	private double minY;
	private double maxZ;
	private double minZ;
	
	private double tmaxX;
	private double tminX;
	private double tmaxY;
	private double tminY;
	private double tmaxZ;
	private double tminZ;
	
	
	public BoundingBox(ArrayList<Shape> shapes){
		
		for(int s = 0; s < shapes.size();s++){
			for(int v = 0; v < shapes.get(s).getNoOfVertices(); v++){
				
				maxX = Math.max(shapes.get(s).getVertex(v).getX(), maxX);
				minX = Math.min(shapes.get(s).getVertex(v).getX(), minX);
				maxY = Math.max(shapes.get(s).getVertex(v).getY(), maxY);
				minY = Math.min(shapes.get(s).getVertex(v).getY(), minY);
				maxZ = Math.max(shapes.get(s).getVertex(v).getZ(), maxZ);
				minZ = Math.min(shapes.get(s).getVertex(v).getZ(), minZ);
			}
		}
		
	}
	
	public void translate(float tx, float ty){
		
		tmaxX = maxX + tx;
		tminX = minX + tx;
		tminY = minY + ty;
		tmaxY = maxY + ty;
	}
	
	public void scale(float sx, float sy){
		
		tmaxX = maxX * sx;
		tminX = minX * sx;
		tminY = minY * sy;
		tmaxY = maxY * sy;
		minZ  = minZ * sy;
		tmaxZ = maxX * sy;
	}
	
	public void rotate(float angleX){
		
		
	}
	
	public boolean inBB(int mx,int my,GL2 gl,GLU glu,float z,
			float sx,float sy,float tx,float ty){
		
		translate(tx,ty);
		scale(sx,sy);
		double[] wcoord = GetOGLPos(mx,my,gl,glu,z);
		
		return wcoord[0] <= maxX && wcoord[0] >= minX && 
				wcoord[1] <= maxY && wcoord[1] >= maxY
				&& wcoord[2] <= maxZ && wcoord[2] >= minZ;
	}
	
	private double[] GetOGLPos(int x, int y,GL2 gl, GLU glu,float z)
	{
		 int viewport[] = new int[4];
	     double mvmatrix[] = new double[16];
	     double projmatrix[] = new double[16];
	     int realy = 0;// GL y coord pos
	     double wcoord[] = new double[4];// wx, wy, wz;// returned xyz coords
				
				
		 gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
         gl.glGetDoublev(GL2.GL_MODELVIEW, mvmatrix, 0);
         gl.glGetDoublev(GL2.GL_PROJECTION, projmatrix, 0);
         /* note viewport[3] is height of window in pixels */
         realy = viewport[3] - (int) y - 1;
         System.out.println("Coordinates at cursor are (" + x + ", " + realy);
         glu.gluUnProject((double) x, (double) realy, z, //
             mvmatrix, 0,
             projmatrix, 0,
             viewport, 0,
             wcoord, 0);
         
         return wcoord;
	}
	
	
	
	
}
