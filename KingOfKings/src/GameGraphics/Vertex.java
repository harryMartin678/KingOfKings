package GameGraphics;

public class Vertex extends VertexTex {

	public Vertex(String information) {
		super(information);
		
	}
	
	public float getZ(){
		
		return pts.get(2).floatValue();
	}

}
