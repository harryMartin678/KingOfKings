package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


//holds a frame of a unit 
public class Frame {
	
	private Shape shape;
	private HashMap<String,Colour> materials;
	private HashMap<Integer,Keyframes> keyFrames;
	//private int[] shapesNo;
	private String filename;
	
	public Frame(String filename,String folder,boolean IsAnim) throws IOException{
		
		//holds shapes in the model
		//shapes = new ArrayList<Shape>();
		//holds the materials in the model
		materials = new HashMap<String,Colour>();
		
		this.filename = filename;
		//loads frame
		LoadMesh mesh = new LoadMesh(folder + "/"+ filename+".obj",folder + "/" + filename+".mtl");
		//shapes = mesh.getShapes();
		shape = mesh.getShape();
		materials = mesh.getMaterials();
		
		if(IsAnim){
			keyFrames = new LoadVector(folder + "/" + 
						filename.replace("1", "") + ".objvec").getKeyFrames();
		}

		//shapesNo = new int[shapes.size()];
		//shapesNo[0] = shapes.get(0).vertexNo();
		
//		for(int s = 1; s < shapes.size(); s++){
//			
//			shapesNo[s] = shapes.get(s).vertexNo() + shapesNo[s-1];
//		}
	}
	
	public Vertex getVertex(int index, int animNo){
		
		if(keyFrames == null || !keyFrames.containsKey(index)){
			
			return shape.getVertex(index);
		}
		
		return shape.getVertex(index).OffsetVertex(
					keyFrames.get(index).GetOffSet(animNo,filename));
		
	}
	
//	public int getVertexNo( int index){
//		
//		return shapesNo[shapeNo] + index;
//	}
	
	public VertexTex getVertexTex(int index) {
		// TODO Auto-generated method stub
		return shape.getVertexTex(index);
	}
	
//	public int getShapeVertexSize(int shape){
//		
//		return shape.getNoOfVertices();
//	}
	
	public int getShapeFaceSize(){
		
		return shape.getNoOfFaces();
	}
	
//	public int getNoOfShape(){
//		
//		return shape.size();
//	}

	public Face getFace(int index) {
		// TODO Auto-generated method stub
		return shape.getFace(index);
	}
	
	public Colour getColour(int index){
		
		String materialName = shape.getColour(index);
		return materials.get(materialName);
	}
	
	public HashMap<String,Colour> getMaterials(){
		
		return materials;
	}

	public int getNoOfFace() {
		// TODO Auto-generated method stub
		return shape.getNoOfFaces();
	}
	
	public float[] getBB(float sizeX,float sizeY,float sizeZ,
			float transX,float transY,float transZ){
		
		float[] bb = shape.getBB();

		return new float[]{(bb[0] * sizeX) + transX,(bb[1] * sizeX) + transX,
				(bb[2] * sizeY)  + transY,(bb[3]  * sizeY) + transY,
				(bb[4] * sizeZ) + transZ, (bb[5]  * sizeZ)+ transZ};
	}


}
