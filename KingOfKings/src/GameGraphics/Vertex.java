package GameGraphics;

public class Vertex extends Info {

	public Vertex(String information) {
		super(information);
		
	}
	
	public float getX(){
		
		return pts.get(0).floatValue();
	}
	
	public float getY(){
		
		return pts.get(1).floatValue();
	}
	
	public float getZ(){
		
		return pts.get(2).floatValue();
	}

}
