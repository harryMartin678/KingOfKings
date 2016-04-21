package GameGraphics;

import java.util.ArrayList;

public class Face extends Info {

	private boolean IsTextured;
	private ArrayList<Integer> TextureVertices;
	
	public Face(String information) {
		super(true);
		if(information.contains("/")){
			IsTextured = true;
			TextureVertices = new ArrayList<Integer>();
			pts = new ArrayList<Float>();
			AddTexturedPoints(information);
			
		}else{
			IsTextured = false;
			super.DoConstructor(information);
		}
			
		
		
	}
	
	private void AddTexturedPoints(String info) {
		// TODO Auto-generated method stub
		String[] pairs = info.split(" ");
		
		//start at 1 to ignore the descriptor char 
		for(int p = 1; p < pairs.length; p++){
			
			String[] numbers = pairs[p].split("/");
			pts.add(new Float(numbers[0]));
			TextureVertices.add(new Integer(numbers[1]));
		}
	}

	public int getFace(int index){
		
		return pts.get(index).intValue();
	}
	
	public int getTextureFace(int index){
		
		return TextureVertices.get(index).intValue();
	}
	
	public int getSize(){
		
		return pts.size();
	}
	
	public boolean IsTextured(){
		
		return IsTextured;
	}

}
