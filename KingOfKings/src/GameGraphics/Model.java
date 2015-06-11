package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

//holds a model and all of it's animation frames 
public class Model {
	
	protected ArrayList<Frame> frames;
	protected ArrayList<Frame> fireFrames;
	protected ArrayList<Frame> deathFrames;
	protected int shapeNo;
	protected int currentIndex;
	private boolean moving;
	protected float sizeX;
	protected float sizeY;
	protected float sizeZ;
	protected float angle;
	
	public Model(String filename, String folder, int noOfFrames, boolean building) throws IOException{
		
		frames = new ArrayList<Frame>();
		fireFrames = new ArrayList<Frame>();
		deathFrames = new ArrayList<Frame>();
		shapeNo = 0;
		currentIndex = -1;
		moving = false;
		
		sizeX = 0.2f;
		sizeY = 0.2f;
		sizeZ = 0.2f;
		angle = 0.0f;
		
		//load all of the model's moving frames 
		for(int i = 1; i <= noOfFrames; i++){
			
			frames.add(new Frame(filename+new Integer(i).toString(),folder));
		}
		
		if(!building){
			//load all of the model's firing frames 
			for(int f = 1; f <= noOfFrames; f++){
				
				fireFrames.add(new Frame(filename+new Integer(f).toString()+"f",folder));
			}
			//load all of the death frames 
			for(int f = 1; f <= noOfFrames; f++){
				
				deathFrames.add(new Frame(filename+new Integer(f).toString()+"d",folder));
			}
		}
	}
	
	public void setSize(float sizeX, float sizeY, float sizeZ){
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}
	
	public float sizeX(){
		
		return sizeX;
	}
	
	public float sizeY(){
		
		return sizeY;
	}
	
	public float sizeZ(){
		
		return sizeZ;
	}
	
	public void setAngle(float angle){
		
		this.angle = angle;
	}
	
	public float getAngle(){
		
		return angle;
	}
	
	//for debugging 
	public void restPopFace(){
		
		shapeNo = 0;
		currentIndex = 0;
	}
	
	public Face popFaceBody(int currentFrame,ArrayList<Frame> frameList){
		
		//if the last face was the last face in the shape 
		if(frameList.get(currentFrame).getShapeFaceSize(shapeNo) == currentIndex+1){
			
			shapeNo++;
			currentIndex = 0;

		}else{
			
			currentIndex ++;
		}
		//if this is the last shape then return null 
		if(frameList.get(currentFrame).getNoOfShapes() == shapeNo){
			
			shapeNo = 0;
			currentIndex = -1; //allows first shape to be drawn 
			
			return null;
		}

		return frameList.get(currentFrame).getFace(shapeNo, currentIndex);
		
	}
	
	//pop's a face in a frame 
	public Face popFace(int currentFrame, int state){
		
		if(state == 0){
			
			return popFaceBody(currentFrame,frames);
		
		}else if(state == 1){
			
			return popFaceBody(currentFrame,fireFrames);
		
		}else{
			
			return popFaceBody(currentFrame,deathFrames);
		}
	}
	
	public Vertex getVertex(int index,int currentFrame,int state){
		
		if(state == 0){
			return frames.get(currentFrame).getVertex(shapeNo, index);
		}else if(state == 1){
			return fireFrames.get(currentFrame).getVertex(shapeNo, index);
		}else{
			return deathFrames.get(currentFrame).getVertex(shapeNo, index);
		}
	}
	
	public Colour getColour(int currentFrame, int state){
		if(state == 0){
			System.out.println(currentFrame);
			return frames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else if(state == 1){
			return fireFrames.get(currentFrame).getColour(shapeNo,currentIndex);
		}else{
			return deathFrames.get(currentFrame).getColour(shapeNo,currentIndex);
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
