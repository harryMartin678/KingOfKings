package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;


//holds a model and all of it's animation frames 
public class Model {
	
	protected Frame still;
	protected Frame fire;
	protected Frame death;
	protected Frame walk;
	//protected int shapeNo;
	protected int currentIndex;
	protected float sizeX;
	protected float sizeY;
	protected float sizeZ;
	protected float angle;
	protected float transX;
	protected float transY;
	protected float transZ;
	protected String name;
	
	public Model(String filename, String folder, int noOfFrames, int modelType) 
			throws IOException{
		
		
	//	shapeNo = 0;
		currentIndex = -1;
		name = filename;
		
		sizeX = 0.2f;
		sizeY = 0.2f;
		sizeZ = 0.2f;
		angle = 0.0f;
		
		transX = 0.0f;
		transY = 0.0f;
		
		if(modelType == 0 || modelType == 1){
			//load all of the model's moving frames 
//			for(int i = 1; i <= noOfFrames; i++){
//				
//				frames.add(new Frame(filename+new Integer(i).toString(),folder));
//			}
			still = new Frame(filename, folder, false);
			walk = new Frame(filename + "1w", folder, true);
			
		
		}else if(modelType == 2){
		
			//frames.add(new Frame(filename,folder));
			still = new Frame(filename, folder, false);
			death = new Frame(filename + "1d",folder,true);
		
		}else if(modelType == 3){
			
			still = new Frame(filename, folder, false);
		}
		
		if(modelType == 0){
			//load all of the model's firing frames 
//			for(int f = 1; f <= noOfFrames; f++){
//				
//				fireFrames.add(new Frame(filename+new Integer(f).toString()+"f",folder));
//			}
//			//load all of the death frames 
//			for(int f = 1; f <= noOfFrames; f++){
//				
//				deathFrames.add(new Frame(filename+new Integer(f).toString()+"d",folder));
//			}
			still = new Frame(filename, folder, false);
			death = new Frame(filename + "1d",folder,true);
			fire = new Frame(filename + "1a", folder, true);
		}
	}
	
	public String getName(){
		
		return name;
	}
	
	public void setTrans(float x,float y,float z){
		
		this.transX = x;
		this.transY = y;
		this.transZ = z;
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
	
	public float getTransX(){
		
		return transX;
	}
	
	public float getTransY(){
		
		return transY;
	}
	
	public float getTransZ(){
		
		return transZ;
	}
	
	public void setAngle(float angle){
		
		this.angle = angle;
	}
	
	public float getAngle(){
		
		return angle;
	}
	
	//for debugging 
	public void restPopFace(){
		
		//shapeNo = 0;
		currentIndex = 0;
	}
	
	public float[] getBB(int animNo){
		
		if(animNo == 0){
			
			return still.getBB(sizeX,sizeY,sizeZ,transX,transY,transZ);
		
		}else if(animNo == 1){
			
			return fire.getBB(sizeX,sizeY,sizeZ,transX,transY,transZ);
		
		}else{
			
			return death.getBB(sizeX,sizeY,sizeZ,transX,transY,transZ);
		}
	}
	
//	public float getZOffSet(int animNo){
//		
//		float[] bbox;
//		if(animNo == 0){
//			
//			bbox = still.getBB(sizeX,sizeY,sizeZ,transX,transY);
//		
//		}else if(animNo == 1){
//			
//			bbox = fire.getBB(sizeX,sizeY,sizeZ,transX,transY);
//		
//		}else{
//			
//			bbox = death.getBB(sizeX,sizeY,sizeZ,transX,transY);
//		}
//		
//		return (Math.abs(bbox[4]) +  Math.abs(bbox[5]))/2;
//	}
	
	public Face popFaceBody(int currentFrame,Frame frame){
		
//		//if the last face was the last face in the shape 
//		if(frame.getShapeFaceSize(shapeNo) == currentIndex+1){
//			
//			shapeNo++;
//			currentIndex = 0;
//
//		}else{
//			
//			currentIndex ++;
//		}
//		//if this is the last shape then return null 
//		if(frame.getNoOfShapes() == shapeNo){
//			
//			shapeNo = 0;
//			currentIndex = -1; //allows first shape to be drawn 
//			
//			return null;
//		}
		
		if(currentIndex + 1 == frame.getNoOfFace()){
			
			currentIndex = -1;
			
			return null;
			
		}else{
			
			currentIndex ++;
		}

		return frame.getFace(currentIndex);
		
	}
	
	//pop's a face in a frame 
	public Face popFace(int animNo, int state){
		
		if(state == 0){
			
			return popFaceBody(animNo,still);
		
		}else if(state == 1){
			
			return popFaceBody(animNo,fire);
		
		}else{
			
			return popFaceBody(animNo,death);
		}
	}
	
	public VertexTex getVertexTex(int index, int state) {
		// TODO Auto-generated method stub
		if(state == 0){
			return still.getVertexTex(index);
		}else if(state == 1){
			return fire.getVertexTex(index);
		}else{
			return death.getVertexTex(index);
		}
	}
	
	public Vertex getVertex(int index,int animNo,int state){
		
		if(state == 0){
			if(animNo > 0){
				
				return walk.getVertex(index, animNo);
			}
			return still.getVertex(index, animNo);
		}else if(state == 1){
			return fire.getVertex(index, animNo);
		}else{
			return death.getVertex(index, animNo);
		}
	}
	
	public Colour getColour(int currentFrame, int state){
		
//		Colour color = new Colour();
//		color.makeRed();
//		return color;
		if(state == 0){
			//System.out.println(currentFrame);
			return still.getColour(currentIndex);
		}else if(state == 1){
			return fire.getColour(currentIndex);
		}else{
			return death.getColour(currentIndex);
		}
	}

//	public boolean anyAbove(float z,int currentFrameNo,int state) {
//		// TODO Auto-generated method stub
//		Frame currentFrame;
//		
//		if(state == 0){
//			
//			currentFrame = frames.get(currentFrameNo);
//			
//		}else if(state == 1){
//			
//			currentFrame = fireFrames.get(currentFrameNo);
//			
//		}else{
//			
//			currentFrame = deathFrames.get(currentFrameNo);
//		}
//		
//		for(int s = 0; s < currentFrame.getNoOfShapes(); s++){
//			for(int v = 0; v < currentFrame.getShapeVertexSize(s); v++){
//				
//				if(currentFrame.getVertex(s, v).getZ() > z){
//					
//					return false;
//				}
//			}
//		}
//		
//		return true;
//	}

	
	
	
	
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
