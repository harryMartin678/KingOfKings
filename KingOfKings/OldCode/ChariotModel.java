package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

//holds a chariot model with the addition animation of a right side firing 
public class ChariotModel extends Model {
	
	private ArrayList<Frame> rightFiringFrames;
	
	public ChariotModel(String filename, String folder, int noOfFrames) throws IOException{
		
		super(filename,folder,noOfFrames,0);
		
		rightFiringFrames = new ArrayList<Frame>();
		
		//load all of the model's right side firing frames 
		for(int f = 1; f <= noOfFrames; f++){
					
			rightFiringFrames.add(new Frame(filename+new Integer(f).toString()+"fr",folder));
		}
	}
	
	@Override
	public Face popFace(int currentFrame, int state) {
		// TODO Auto-generated method stub
		if(state == 0){
			
			return popFaceBody(currentFrame,frames);
		
		}else if(state == 1){
			
			return popFaceBody(currentFrame,fireFrames);
		
		}else if(state == 2){
			
			return popFaceBody(currentFrame,deathFrames);
		}else{
			
			return popFaceBody(currentFrame,rightFiringFrames);
		}
	} 
	
	@Override
	public Vertex getVertex(int index, int currentFrame, int state) {
		// TODO Auto-generated method stub
		if(state == 0){
			return frames.get(currentFrame).getVertex(shapeNo, index);
		}else if(state == 1){
			return fireFrames.get(currentFrame).getVertex(shapeNo, index);
		}else if(state == 2){
			return deathFrames.get(currentFrame).getVertex(shapeNo, index);
		}else{
			return rightFiringFrames.get(currentFrame).getVertex(shapeNo, index);
		}
	}
	
	@Override
	public Colour getColour(int currentFrame, int state) {
		// TODO Auto-generated method stub
		if(state == 0){
			return frames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else if(state == 1){
			return fireFrames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else if(state == 2){
			return deathFrames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else{
			return rightFiringFrames.get(currentFrame).getColour(shapeNo,currentIndex);
		}
	}

}
