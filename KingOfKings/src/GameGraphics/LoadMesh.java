package GameGraphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadMesh {
	
	private File obj;
	private File mtl;
	private BufferedReader reader;
	private BufferedReader mtlReader;
	//private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Shape shape;
	private HashMap<String,Colour> materials;
	
	
	public LoadMesh(String objFile, String mtlFile) throws IOException{
		
		shape = new Shape("objfile");
		obj = new File(objFile);
		mtl = new File(mtlFile);
		
		reader = new BufferedReader(new FileReader(obj));
		mtlReader = new BufferedReader(new FileReader(mtl));
		materials = new HashMap<String,Colour>();
		
		getStoreColours();
		createShapes();
		
		shape.CreateAABB();
		
	}
	
	private void getStoreColours() throws IOException{
		
		String currentLine = null;
		
		String lastMaterial = "";
		
		//reads the mtl file
		while((currentLine = mtlReader.readLine()) != null){
			
			if(currentLine.length() == 0){
				
				continue;
			}
			
			//if it's a material
			if(currentLine.charAt(0) == 'n'){
				lastMaterial = currentLine.substring(7,currentLine.length());
				materials.put(lastMaterial,new Colour());
			//if it's a colour array 
			}else if(currentLine.charAt(0) == 'K'
						|| currentLine.charAt(0) == 'd'
							|| currentLine.charAt(0) =='m'){
				
				materials.get(lastMaterial).postLine(
						currentLine);
			}
		}


	}
	
	public void createShapes() throws IOException{

		String currentLine = null;

		//read the obj file
		while((currentLine = reader.readLine()) != null){
			
//			if(currentLine.charAt(0) == 'o' && shapes.size() > 0){
//				
//				//System.out.println(lastSize + " size " + shapes.get(shapes.size()-1).getName());
//				lastSize += shapes.get(shapes.size()-1).getNoOfVertices();
//				lastTexSize += shapes.get(shapes.size()-1).getNoOfTexVertices();
//			}
			
			//if this line is the start of a shape
			//if(currentLine.charAt(0) == 'o'){
				
				//shapes.add(new Shape(currentLine.substring(2, currentLine.length()),lastSize,lastTexSize));
			//else add important information to the shape 
			//}else 
			if(currentLine.charAt(0) == 'v' 
					|| currentLine.charAt(0) == 'f'
						|| currentLine.charAt(0) == 'u'){
				
				shape.postLine(currentLine);
			}
		}

		
	}
	
	public Shape getShape(){
		
		return shape;
	}
	
	public HashMap<String,Colour> getMaterials(){
		
		return materials;
	}
	
	//public static void main(String[] args) {
//		
//		try {
//			new LoadMesh("Models/slave1.obj","Models/slave1.mtl");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	

}
