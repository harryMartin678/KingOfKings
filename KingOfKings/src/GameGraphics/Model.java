package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

public class Model {
	
	private ArrayList<Frame> frames;
	private ArrayList<Frame> fireFrames;
	private int shapeNo;
	private int currentIndex;
	private boolean moving;
	
	public Model(String filename, String folder, int noOfFrames) throws IOException{
		
		frames = new ArrayList<Frame>();
		fireFrames = new ArrayList<Frame>();
		shapeNo = 0;
		currentIndex = -1;
		moving = false;
		
		//load all of the model's frames 
		for(int i = 1; i <= noOfFrames; i++){
			
			frames.add(new Frame(filename+new Integer(i).toString(),folder));
		}
		
		for(int f = 1; f <= noOfFrames; f++){
			
			fireFrames.add(new Frame(filename+new Integer(f).toString()+"f",folder));
		}
	}
	
	
	public Face popFace(int currentFrame, boolean firing){
		
		if(!firing){
			//if the last face was the last face in the shape 
			if(frames.get(currentFrame).getShapeFaceSize(shapeNo) == currentIndex+1){
				
				shapeNo++;
				currentIndex = 0;
				
			}else{
				
				currentIndex ++;
			}
			//if this is the last shape then return null 
			if(frames.get(currentFrame).getNoOfShapes() == shapeNo){
				
				shapeNo = 0;
				currentIndex = 0;
				
				return null;
			}
	
			return frames.get(currentFrame).getFace(shapeNo, currentIndex);
		}else{
			
			//if the last face was the last face in the shape 
			if(fireFrames.get(currentFrame).getShapeFaceSize(shapeNo) == currentIndex+1){
				
				shapeNo++;
				currentIndex = 0;
				
			}else{
				
				currentIndex ++;
			}
			//if this is the last shape then return null 
			if(fireFrames.get(currentFrame).getNoOfShapes() == shapeNo){
				
				shapeNo = 0;
				currentIndex = 0;
				
				return null;
			}
	
			return fireFrames.get(currentFrame).getFace(shapeNo, currentIndex);
		}
	}
	
	public Vertex getVertex(int index,int currentFrame,boolean firing){
		
		if(!firing){
			return frames.get(currentFrame).getVertex(shapeNo, index);
		}else{
			return fireFrames.get(currentFrame).getVertex(shapeNo, index);
		}
	}
	
	public Colour getColour(int currentFrame, boolean firing){
		if(!firing){
			return frames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else{
			return fireFrames.get(currentFrame).getColour(shapeNo,currentIndex);
		}
	}
	
	
	
	/*public static void main(String[] args) {
		
		long time = System.currentTimeMillis();
		try {
			Model model = new Model("bodyLowPoly","Models",1);
			Face face = model.popFace();
			
			for(int i = 0; i < face.getSize(); i++){
				
				System.out.print(face.getFace(i) + " ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - time);
	}*/

}
