package GameGraphics;

import java.util.ArrayList;

public class Shape {
	
	private String name;
	private ArrayList<VertexTex> verticesTex;
	private ArrayList<Vertex> vertices;
	private ArrayList<Face> faces;
	private ArrayList<String[]> materials;
	private ArrayList<Colour> colours;
	private int currentColour;
	
	private float maxX;
	private float minX;
	private float maxY;
	private float minY;
	private float maxZ;
	private float minZ;
	
	public Shape(String name){
		
		this.name = name;
		verticesTex = new ArrayList<VertexTex>();
		vertices = new ArrayList<Vertex>();
		faces = new ArrayList<Face>();
		materials = new ArrayList<String[]>();
		colours = new ArrayList<Colour>();
		currentColour = 0;
	}
	
	public void postLine(String line){
		
		
		//vertices 
		if(line.charAt(0) == 'v' && line.charAt(1) != 'n'){
			
			if(line.charAt(1) == 't'){
				
				verticesTex.add(new VertexTex(line));
				
			}else{
				
				vertices.add(new Vertex(line));
			}
		
		//face indices 
		}else if(line.charAt(0) == 'f'){
			
			faces.add(new Face(line));
		
		//material description
		}else if(line.charAt(0) == 'u'){
			
			String[] mat = new String[2];
			
			mat[0] = line.substring(7,line.length());

			mat[1] = Integer.toString(faces.size());
			
			materials.add(mat);
		
		}
		
	}
	
	public String getName(){
		
		return name;
	}
	
	public String[] getMaterial(int index){
		
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
	
	public int getNoOfTexVertices() {
		// TODO Auto-generated method stub
		return verticesTex.size();
	}


	public int getNoOfFaces() {
		
		return faces.size();
	}

	public Vertex getVertex(int index) {
		
		return vertices.get(index);
		
	}
	
	public VertexTex getVertexTex(int index) {
		// TODO Auto-generated method stub
		
		return verticesTex.get(index);
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
	
	public String getColour(int index){

		if(index == 0){
			
			currentColour = 0;
		}
		
		if(currentColour + 1 < materials.size()){
			
			String[] next = this.getMaterial(currentColour + 1);
			
			if(index >= Integer.parseInt(next[1])){
				
				currentColour++;
				return next[0];
				
			}else{

				return this.getMaterial(currentColour)[0];
			}
			
		}else{
			
			currentColour = 0;
			return this.getMaterial(currentColour)[0];
		}
	}

	public int vertexNo() {
		// TODO Auto-generated method stub
		return vertices.size();
	}
	
	public void CreateAABB(){
		
		for(int v = 0; v < vertices.size(); v++){
			
			maxX = Math.max(vertices.get(v).getX(),maxX);
			minX = Math.min(vertices.get(v).getX(), minX);
			maxY = Math.max(vertices.get(v).getY(), maxY);
			minY = Math.min(vertices.get(v).getY(), minY);
			maxZ = Math.max(vertices.get(v).getZ(), maxZ);
			minZ = Math.min(vertices.get(v).getZ(), minZ);
		}
	}
	
	public float[] getBB(){
		
		return new float[]{maxX,minX,maxY,minY,maxZ,minZ};
	}

}
