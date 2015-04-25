package Map;

public class Feature {
	
	private int sizeX;
	private int sizeY;
	private int type;
	
	public Feature(int sizeX, int sizeY, int type){
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.type = type;
	}
	
	public int getSizeX(){
		
		return sizeX;
	}
	
	public int getSizeY(){
		
		return sizeY;
	}
	
	public int getType(){
		
		return type;
	}

}
