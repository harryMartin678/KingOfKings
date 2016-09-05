package GameGraphics;

import java.util.ArrayList;

public class Vertex extends VertexTex {

	public Vertex(String information) {
		super(information);
		
	}
	
	public Vertex(float x, float y, float z){
		
		pts = new ArrayList<Float>();
		
		pts.add(x);
		pts.add(y);
		pts.add(z);
	}
	
	public float getZ(){
		
		return pts.get(2).floatValue();
	}
	
	public Vertex OffsetVertex(float[] offset){
		
		return new Vertex(pts.get(0) + offset[0],pts.get(1) + offset[1],
				pts.get(2) + offset[2]);
	}

}
