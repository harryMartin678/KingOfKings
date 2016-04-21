package GameGraphics;

public class VertexTex extends Info {

	public VertexTex(String information) {
		super(information);
		// TODO Auto-generated constructor stub
	}
	
	public float getX(){
		
		return pts.get(0).floatValue();
	}
	
	public float getY(){
		
		return pts.get(1).floatValue();
	}

}
