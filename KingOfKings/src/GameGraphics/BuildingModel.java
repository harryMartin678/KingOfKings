package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

public class BuildingModel extends Model{

	public BuildingModel(String filename, String folder, int noOfFrames)
			throws IOException {
		super(filename, folder, noOfFrames,1);
		// TODO Auto-generated constructor stub
	}
	
	public String[] getTexturePaths(){
		
		ArrayList<Colour> colours = frames.get(0).getMaterials();
		
		String[] texturePaths = new String[colours.size()];
		
		for(int i = 0; i < colours.size(); i++){
			
			texturePaths[i] = colours.get(i).getTexturePath();
		}
		
		return texturePaths;
	}
	

}
