package GameGraphics;

import java.io.IOException;
import java.util.ArrayList;

//holds a frame of a unit 
public class Frame {
	
	private ArrayList<Shape> shapes;
	private ArrayList<Colour> materials;
	private int currentColour;
	private int lastShape;
	
	
	public Frame(String filename,String folder) throws IOException{
		
		shapes = new ArrayList<Shape>();
		materials = new ArrayList<Colour>();
		currentColour = 0;
		lastShape = 0;
		
		LoadMesh mesh = new LoadMesh(folder + "/"+ filename+".obj",folder + "/" + filename+".mtl");
		shapes = mesh.getShapes();
		materials = mesh.getMaterials();

		
	}
	
	public Vertex getVertex(int shape, int index){
		
		return shapes.get(shape).getVertex(index);
	}
	
	public int getShapeVertexSize(int shape){
		
		return shapes.get(shape).getNoOfVertices();
	}
	
	public int getShapeFaceSize(int shape){
		
		return shapes.get(shape).getNoOfFaces();
	}
	
	public int getNoOfShapes(){
		
		return shapes.size();
	}

	public Face getFace(int shapeNo, int index) {
		// TODO Auto-generated method stub
		return shapes.get(shapeNo).getFace(index);
	}
	
	public Colour getColour(int shapeNo, int index){
		
		return materials.get(shapes.get(shapeNo).getColour(index));
	}
	
	public ArrayList<Colour> getMaterials(){
		
		return materials;
	}
	
	
	

}
