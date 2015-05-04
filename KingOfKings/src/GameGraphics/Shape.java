package GameGraphics;

import java.util.ArrayList;

public class Shape {
	
	private String name;
	private ArrayList<Vertex> vertices;
	private ArrayList<Face> faces;
	private ArrayList<int[]> materials;
	private ArrayList<Colour> colours;
	private int lastIndex;
	private int currentColour;
	
	public Shape(String name, int lastIndex){
		
		this.name = name;
		vertices = new ArrayList<Vertex>();
		faces = new ArrayList<Face>();
		materials = new ArrayList<int[]>();
		colours = new ArrayList<Colour>();
		this.lastIndex = lastIndex;
		currentColour = 0;
	}
	
	public void postLine(String line){
		
		
		//vertices 
		if(line.charAt(0) == 'v'){
			
			vertices.add(new Vertex(line));
		
		//face indices 
		}else if(line.charAt(0) == 'f'){
			
			faces.add(new Face(line));
		
		//material description
		}else if(line.charAt(0) == 'u'){
			
			int[] mat = new int[2];
			
			mat[0] = new Integer(line.substring(line.length()-3,line.length())).intValue();
			mat[1] = faces.size();
			
			materials.add(mat);
		}
	}
	
	public String getName(){
		
		return name;
	}
	
	public int[] getMaterial(int index){
		
		return materials.get(index);
	}
	
	
	public void printVertices(){
		
		for(int i = 0; i < vertices.size(); i++){
			
			System.out.println("v " + vertices.get(i).getX() + " " +
			vertices.get(i).getY() + " " + vertices.get(i).getZ());
		}
		
	}
	
	public void printFace(){
		
		for(int i = 0; i < faces.size(); i++){
			
			System.out.print("f ");
			for(int j = 0; j < faces.get(i).getSize(); j++){
				
				System.out.print(faces.get(i).getFace(j) + " ");
			}
			System.out.println();
		}
		
	}
	
	public void printMaterials(){
		
		for(int i = 0; i < materials.size(); i++){
			
			System.out.println("m "+ materials.get(i)[0] + " " + materials.get(i)[1]);
		}
	}
	
	public void printColours(){
		
		System.out.println(colours.size() + " colours " + name);
	}

	public int getNoOfVertices() {

		return vertices.size();
	}

	public int getNoOfFaces() {
		
		return faces.size();
	}

	public Vertex getVertex(int index) {
		
		return vertices.get(index-lastIndex);
		
	}

	public Face getFace(int index) {
	
		return faces.get(index);
	}

	public int getMaterialSize() {
		// TODO Auto-generated method stub
		return materials.size();
	}
	
	public void resetColour(){
		
		this.currentColour = 0;
	}
	
	public int getColour(int index){

		if(currentColour < materials.size() - 1){
			
			int[] material = this.getMaterial(currentColour+1);
			
			if(index >= material[1]){
				
				currentColour++;
				return material[0];
				
			}else{
				
				return this.getMaterial(currentColour)[0];
			}
			
		}else{
			
			currentColour = 0;
			return this.getMaterial(currentColour)[0];
		}
	}

}
