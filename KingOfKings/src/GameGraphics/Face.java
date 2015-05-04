package GameGraphics;

public class Face extends Info {

	public Face(String information) {
		super(information);
		
	}
	
	public int getFace(int index){
		
		return pts.get(index).intValue();
	}
	
	public int getSize(){
		
		return pts.size();
	}

}
