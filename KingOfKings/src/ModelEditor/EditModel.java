package ModelEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ModelEditor.EditModel.Vertex;

public class EditModel {
	
	private ArrayList<Vertex> allVertices;
	
	public EditModel(String filename){
		
		ArrayList<Vertex> Vertices = null;
		ArrayList<String> OtherLines = new ArrayList<String>();
		
		System.out.println(filename);
		
		File file = new File(filename + ".obj");
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Vertices = GetVertices(reader,OtherLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(int v = 0; v < 10; v++){
//			
//			System.out.println(Vertices.get(v));
//		}
//		System.out.println("GAPPP");
//		
		Vertex center = getCenter(Vertices);
//		
//		System.out.println(center);
//		System.out.println("GAPPP");
		CenterModel(Vertices,center);
		GetUnitSize(Vertices,center);
		
		String toWrite = GetObjString(Vertices, OtherLines);
		
		String[] parts = filename.split("/");
		String[] newParts = new String[parts.length+1];
		
		for(int i = 0; i < parts.length-1; i++){
			
			newParts[i] = parts[i];
		}
		
		newParts[newParts.length-2] = "converted";
		newParts[newParts.length-1] = parts[parts.length-1];
		
		allVertices = Vertices;
		
		File newFile = new File(String.join("/", newParts) + ".obj");
		
		System.out.println(newFile.getAbsolutePath());
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(newFile));
			writer.write(toWrite);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		for(int v = 0; v < Vertices.size(); v++){
//			
//			System.out.println(Vertices.get(v));
//		}
		
		
		
	}
	
	private String GetObjString(ArrayList<Vertex> Vertices, ArrayList<String> OtherLines){
		
		String file = "";
		int lineNo = 0;
		int vertexNo = 0;
		int OtherNo = 0;
		
		while(vertexNo < Vertices.size() || OtherNo < OtherLines.size()){
			
			if(vertexNo < Vertices.size() && Vertices.get(vertexNo).lineNo == lineNo){
				
				file += Vertices.get(vertexNo).ToLine();
				vertexNo++;
			}else if(OtherNo < OtherLines.size()){
				
				file += OtherLines.get(OtherNo) + "\n";
				OtherNo++;
			}
			
			lineNo++;
			
		}
		
		return file;
	}
	
	private void GetUnitSize(ArrayList<Vertex> vertices,Vertex center) {
		// TODO Auto-generated method stub
		Vertex furthestPoint = GetFurthestPoint(vertices,center);
		
		float mag = (float)Math.sqrt(Math.pow(furthestPoint.x, 2) +
				Math.pow(furthestPoint.y, 2) + Math.pow(furthestPoint.z, 2));
		
		for(int v = 0; v < vertices.size(); v++){
			
			vertices.get(v).divideBy(mag);
		}
	}

	private Vertex GetFurthestPoint(ArrayList<Vertex> vertices,Vertex center) {
		// TODO Auto-generated method stub
		float furthest = Float.MIN_VALUE;
		Vertex furVertex = null;
		
		for(int f = 0; f < vertices.size(); f++){
			
			float dist;
			if((dist = vertices.get(f).distanceTo(center)) > furthest){
				
				furthest = dist;
				furVertex = vertices.get(f);
			}
		}
		
		return furVertex;
	}

	private void CenterModel(ArrayList<Vertex> vertices, Vertex center) {
		// TODO Auto-generated method stub
		for(int v = 0; v < vertices.size(); v++){
			
			vertices.get(v).MinusVertex(center);
		}
	}

	private Vertex getCenter(ArrayList<Vertex> vertices) {
		// TODO Auto-generated method stub
		Vertex avgVertex = new Vertex();
		
		for(int v = 0; v < vertices.size(); v++){
			
			avgVertex.x += vertices.get(v).x;
			avgVertex.y += vertices.get(v).y;
			avgVertex.z += vertices.get(v).z;
		}
		
		avgVertex.x = avgVertex.x / vertices.size();
		avgVertex.y = avgVertex.y / vertices.size();
		avgVertex.z = avgVertex.z / vertices.size();
		
		return avgVertex;
	}

	public ArrayList<Vertex> GetVertices(BufferedReader reader,
			ArrayList<String> OtherLines) throws IOException{
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		String line;
		int lineNo = 0;
		while((line = reader.readLine()) != null){

			if(line.substring(0, 2).equals("v ")){
				vertices.add(new Vertex(line,lineNo));
			}else{
				OtherLines.add(line);
			}
			lineNo++;
		}
		
		return vertices;
	}
	
	public ArrayList<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return allVertices;
	}
	
	public class Vertex{
		
		public float x;
		public float y;
		public float z;
		public int lineNo;
		
		public Vertex(String line,int lineNo){
			
			ParseLine(line);
			this.lineNo = lineNo;
		}
		
		public void divideBy(float mag) {
			// TODO Auto-generated method stub
			this.x /= mag;
			this.y /= mag;
			this.z /= mag;
		}

		public Vertex() {
			// TODO Auto-generated constructor stub
		}

		public void ParseLine(String line){
			
			String[] lineSplit = line.split(" ");
			
			x = Float.parseFloat(lineSplit[1]);
			y = Float.parseFloat(lineSplit[2]);
			z = Float.parseFloat(lineSplit[3]);
		}
		
		public String ToLine(){
			
			return "v " + x + " " + y + " " + z + "\n";
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "{ "+x + " " + y + " " + z + " }";
		}
		
		public void MinusVertex(Vertex offset){
			
			this.x -= offset.x;
			this.y -= offset.y;
			this.z -= offset.z;
		}
		
		public float distanceTo(Vertex vertex){
			
			return (float)Math.sqrt(Math.pow(vertex.x - this.x, 2)
					+ Math.pow(vertex.y - this.y, 2) 
					+ Math.pow(vertex.z - this.z, 2));
		}
		
	}

	
	
	public static void main(String[] args) {
		
//		String[] objs = new String[]{"worker","worker1a","worker1d","Archer1","Archer1a"
//				,"Archer1d","Archer1w","archerytower","archerytower1d","ballistictower",
//				"ballistictower1d","farm","mine","Giant","Giant1a","Giant1d","Giant1w"
//				,"GiantLiar","GiantLiar1d","Hound","Hound1a","Hound1d","Hound1w",
//				"HoundPit","HoundPit1d","royalPalace","Spearman","Spearman1a","Spearman1d",
//				"Spearman1w","Spearyard","Spearyard1d","stockpile","stockpile1d","SwordsMan",
//				"SwordsMana1","SwordsMand1","SwordsManw1","SwordsSmith","SwordsSmith1d"};
//		
//		for(int f = 0; f < objs.length; f++){
//			
//			assert(new File("bin/ModelEditor/" + objs[f] + ".obj").exists());
//			
//		}
//		
//		for(int b = 0; b < objs.length; b++){
//			
//			new EditModel("bin/ModelEditor/" + objs[b]);
//		}
		new EditModel("bin/ModelEditor/Stockpile");
	}

}
