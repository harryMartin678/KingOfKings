package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

public class BuildingModel extends Model{

	public BuildingModel(String filename, String folder, int noOfFrames,boolean isSite)
			throws IOException {
		
		super(filename, folder, noOfFrames,3);
		// TODO Auto-generated constructor stub
	}
	
	public BuildingModel(String filename, String folder, int noOfFrames)
			throws IOException {
		super(filename, folder, noOfFrames,2);
	}
	
	public String[] getTexturePaths(){
		
		Colour[] colours = (Colour[]) still.getMaterials().values().toArray();
		
		String[] texturePaths = new String[colours.length];
		
		for(int i = 0; i < colours.length; i++){
			
			texturePaths[i] = colours[i].getTexturePath();
		}
		
		return texturePaths;
	}

//	public boolean anyAbove(float z) {
//		// TODO Auto-generated method stub
//		return super.anyAbove(z, 0,0);
//	}

	

}
